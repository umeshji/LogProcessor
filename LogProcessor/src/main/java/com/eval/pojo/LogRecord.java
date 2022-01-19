package com.eval.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode()
@Data
@Entity
@Table(name = "logrecord")
public class LogRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String eventId;
	private String appType;
	private String host;
	private Long eventDuration;
	@Type(type="yes_no")
	private boolean alert;
}
