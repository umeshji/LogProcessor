package com.eval.parser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.eval.pojo.LogRecord;

@RunWith(MockitoJUnitRunner.class)
class LogProcessorTests {
	
	@MockBean
    private LogProcessor logProcessor;
	
	@Test
    public void process_parse_json_and_return_list_on_success() {
		List<LogRecord> records = new ArrayList<>();
		when(logProcessor.process(anyString())).thenReturn(records);
		
	}
	
	@Test
    public void process_should_parse_json_and_return_null_on_error() {
		when(logProcessor.process(anyString())).thenReturn(null);
		verify(logProcessor, times(1)).process(any());
	}
	
}
