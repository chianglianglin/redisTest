package com.ws.project.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建者
	 */
	@Column(name = "create_by") // '資料創建人員'
	private String createBy;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time") // '資料創建時間'
	@CreatedDate
	private Date createTime;

	/**
	 * 更新者
	 */
	@Column(name = "update_by") // '資料創建時間'
	private String updateBy; // '資料修改人員'

	/**
	 * 更新时间
	 */
	@Column(name = "update_time") // '資料修改時間'
	@LastModifiedDate
	private Date updateTime;

}
