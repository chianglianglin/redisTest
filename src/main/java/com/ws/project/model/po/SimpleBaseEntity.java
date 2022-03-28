package com.ws.project.model.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	@CreatedDate
	private Date createTime;

	/**
	 * 创建者
	 */
	private String createBy;

}
