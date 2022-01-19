package com.eval.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eval.pojo.LogRecord;

@Repository
public interface LogRecordRepository extends CrudRepository<LogRecord, Long>  {

	LogRecord findByEventId(String string);
	
}
