package com.eval.main;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.eval.parser.LogProcessor;
import com.eval.pojo.LogRecord;
import com.eval.repo.LogRecordRepository;
import com.google.common.collect.Lists;

@SpringBootApplication
@ComponentScan(basePackages={"com.eval.main"})
@EnableJpaRepositories(basePackages="com.eval.repo")
@EntityScan(basePackages="com.eval.pojo")
public class LogProcesserMain {

	Logger log = LoggerFactory.getLogger(LogProcesserMain.class);
	
	private LogProcessor logProcessor;

	public static void main(String[] args) {
		SpringApplication.run(LogProcesserMain.class, args);
		
	}
	
	public LogProcesserMain(){
		logProcessor = new LogProcessor();
	}
	
	@Autowired
	private LogRecordRepository logRecordRepository;


	@Bean
	public CommandLineRunner processLogFile() {
		return (args) -> {
			try {
				System.out.println("Please enter the file path");
				Scanner scanner = new Scanner(System.in);
		        String jsonFilePath = scanner.nextLine();
				long start = System.currentTimeMillis();
				List<LogRecord> list = logProcessor.process(jsonFilePath);
				int cores = Runtime.getRuntime().availableProcessors();
				List<List<LogRecord>> listOfList = Lists.partition(list, (list.size() / cores));
				listOfList.parallelStream().forEach(x -> logRecordRepository.saveAll(x));
				long end = System.currentTimeMillis();
				long seconds = ((end - start) / 1000) % 60;
				long toalRecords = logRecordRepository.count();
				log.info("Method execution lasted : " + seconds + " seconds "  + " totalRecords : " + toalRecords);
			} catch (Exception e) {
				log.error("Error while fetching details for log file" + e.getMessage());
			} 
		};

	}

}
