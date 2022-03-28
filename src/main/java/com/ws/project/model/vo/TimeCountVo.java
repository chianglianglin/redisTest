package com.ws.project.model.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeCountVo {

	String totalTime;

	int count;


	public TimeCountVo(String totalTime, int count) {
		this.totalTime = totalTime;
		this.count = count;
	}
}
