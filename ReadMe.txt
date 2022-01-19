Parsing algorith/logic
Take input from user for the file location Eg (D:\Practise\person.json)
Read the file using Json parser.
Store every record in Record object.
Use Map to save the object , keey eventId (eg.scsmbstgra) as the key
One receiving the second log in pair ( order does not matter start/finish or finish/start)
Check if the time diff is more then 4 and set the flag to true;
Push the new model object (LogRecord) in the List
Remove entry from the Map
Return the list of model object to main class (LogProcesserMain.java)
Partition the list into list of list using guava library
Call parallel stream on the above list to updatate the database in parallel (each thread update 10k record in batch)
Paralle stream implements itself using fork/join framework.
Below line shows the code and config used to achieve above purpose
	Code   : listOfList.parallelStream().forEach(x -> logRecordRepository.saveAll(x));
	Config : spring.jpa.properties.hibernate.jdbc.batch_size=10000

I have tested the code by passing 5.2 million records in the person.json file and it took 11 seconds to process and push them in DB


