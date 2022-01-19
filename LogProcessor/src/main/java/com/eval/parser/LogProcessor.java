package com.eval.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eval.pojo.LogRecord;
import com.eval.pojo.Record;
import com.eval.util.AppConstant;

public class LogProcessor {
	Logger log = LoggerFactory.getLogger(LogProcessor.class);
	
	Map<String, Record> logMap = new HashMap<>();
	Predicate<Record> p1 = (r) -> "STARTED".equals(r.getState());
	Predicate<Record> p2 = (r) -> "FINISHED".equals(r.getState());
    
	public List<LogRecord> process(String jsonFilePath) {
		File jsonFile = new File(jsonFilePath);
		JsonFactory jsonfactory = new JsonFactory(); // init factory
		List<LogRecord> list = new ArrayList<>();
		try {
			int numberOfRecords = 0;
			int processed = 0;
			JsonParser jsonParser = jsonfactory.createJsonParser(jsonFile);  // create JSON parser
			JsonToken jsonToken = jsonParser.nextToken();
			String id = null; String type = null;  String state = null; String host = null;
			Long timestamp = null;
			while (jsonToken!=null && jsonToken != JsonToken.END_ARRAY) {
				String fieldname = jsonParser.getCurrentName(); 			// get current name of token
				if (AppConstant.ID.equalsIgnoreCase(fieldname)) {
					jsonToken = jsonParser.nextToken(); 					// read next token
					 id = jsonParser.getText();
				} else if (AppConstant.STATE.equalsIgnoreCase(fieldname)) {
					jsonToken = jsonParser.nextToken();
					state =jsonParser.getText();
				} else if (AppConstant.TYPE.equalsIgnoreCase(fieldname)) {
					jsonToken = jsonParser.nextToken();
					type = jsonParser.getText();
				} else if (AppConstant.HOST.equalsIgnoreCase(fieldname)) {
					jsonToken = jsonParser.nextToken();
					host = jsonParser.getText();
				} else if (AppConstant.TIMESTAMP.equalsIgnoreCase(fieldname)) {
					jsonToken = jsonParser.nextToken();
					timestamp = jsonParser.getLongValue();
				} else if (jsonToken==JsonToken.END_OBJECT) {
					// do some processing, Indexing, saving in DB etc..
					Record recordNew = new Record(id,state,type,host,timestamp);
					String logId = recordNew.getId();
					if(logMap.containsKey(logId)) {
						Record recordOld = logMap.get(logId);
						// handles both case of log arrival (START <-> FINISH or FINISH	<-> START) || (p1.test(logRecordNew) && p2.test(logRecordOld))
						if( (p1.test(recordOld) && p2.test(recordNew)) || (p1.test(recordNew) && p2.test(recordOld)) ) { 
							processed++;
							//add the orm object if above predicate return true (business condition is met)
							LogRecord record = new LogRecord();
							record.setAlert(Math.abs(recordOld.getTimestamp() - recordNew.getTimestamp()) > 4 ? true :false); //if time more then 4 millisecond
							record.setHost(recordOld.getHost()!=null ? recordOld.getHost() : null);
							record.setAppType(recordOld.getType() !=null ? recordOld.getType() : null);
							record.setEventId(recordOld.getId());
							record.setEventDuration(Math.abs(recordOld.getTimestamp()- recordNew.getTimestamp()));
							list.add(record);
							logMap.remove(recordOld.getId()); // both the entries start & finished received hence remove the initial entry
						}
					} else {	
						logMap.putIfAbsent(logId, recordNew); // if not present in map add (first entry)
					}
					numberOfRecords++;
				}
				jsonToken = jsonParser.nextToken();
			}
			log.info("Total Records Found : " + numberOfRecords + " Total Records processed " +  processed); 
		} catch (IOException e) {
			log.error("Error with file path "+ e.getMessage());
		}
		return list;
	}
}
