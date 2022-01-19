package com.eval.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"timestamp","type","host"})
@Value
public class Record {
	private String id;
	private String state;
	private String type;
	private String host;
	private Long timestamp;
}
