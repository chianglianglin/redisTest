package com.ws.project.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
public class SimpleBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time") // '資料創建時間'
	@CreatedDate
	private Date createTime;

	/**
	 * 创建者
	 */
	@Column(name = "create_by") // '資料創建人員'
	private String createBy;

}
