package com.eval.repo;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import com.eval.pojo.LogRecord;
import com.eval.repo.LogRecordRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LogRecordRepositoryTest {
	
	@Autowired
	private LogRecordRepository logRecordRepository;
	
	   @After
	    public void tearDown() throws Exception {
		  logRecordRepository.deleteAll();
	    }
	   
	   @Test
	    public void findByEventId() {
		   LogRecord logRecordNew = new LogRecord();
		   logRecordNew.setEventId("scsmbstgra");
		   logRecordNew.setEventDuration(4L);
		   logRecordNew.setAppType("APPLICATION_LOG");
		   logRecordNew.setHost("12345");
		   logRecordRepository.save(logRecordNew);
	        LogRecord logRecord = logRecordRepository.findByEventId("scsmbstgra");
	        assertThat(logRecord).isNotNull();
	        assertThat(logRecord.getEventId()).isEqualTo("scsmbstgra");
	    }
	
}
