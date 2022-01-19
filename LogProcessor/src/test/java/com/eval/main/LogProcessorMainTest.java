package com.eval.main;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.eval.parser.LogProcessor;
import com.eval.repo.LogRecordRepository;

public class LogProcessorMainTest {
	
	@MockBean
    private LogRecordRepository logRecordRepository;
	
	LogProcessor logProcessor;
	
	@Before
    public void setUp() throws Exception {
		logProcessor = new LogProcessor();
    }
	
	@Test
    public void verify_process_is_called_once() {
		verify(logProcessor, times(1)).process(any());
	}
	
	@Test
    public void verify_repo_saved_is_called_atleast_once() {
		verify(logRecordRepository, atLeastOnce()).save(any());
	}
	
	@Test
    public void verify_repo_saved_is_called_once() {
		verify(logRecordRepository, times(1)).count();
	}
	
	
}
