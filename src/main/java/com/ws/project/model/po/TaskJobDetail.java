package com.ws.project.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data()
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "task_job_detail")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TaskJobDetail extends SimpleBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = null;

	@Column(name = "task_job_id")
	private Long taskJobId;

	@Column(name = "country")
	private String country;

	@Column(name = "city")
	private String city;

	@Column(name = "ip")
	private String ip;

	@Column(name = "url")
	private String url;

	@Column(name = "isp")
	private String isp;

	@Column(name = "status_code")
	private Integer statusCode;

	@Column(name = "size")
	private Long size;

	@Column(name = "nslookup_duration")
	private String nslookupDuration;

	@Column(name = "total_duration")
	private String totalDuration;

	@Column(name = "connect_time")
	private Date connectTime;

	@Column(name = "done_time")
	private Date doneTime;
	
	@Column(name = "device_ip")
	private String deviceIp;
	
	@Column(name = "device_country")
	private String deviceCountry;
	
	@Column(name = "device_province")
	private String deviceProvince;
	
	@Column(name = "device_city")
	private String deviceCity;
	
	@Column(name = "device_isp")
	private String deviceIsp;
	
	@Column(name = "device_mac")
	private String deviceMac;

	public TaskJobDetail(Integer id, Long taskJobId, String country, String city, String ip, String url, String isp, Integer statusCode, Long size, String nslookupDuration, String totalDuration, Date connectTime, Date doneTime, String deviceIp, String deviceCountry, String deviceProvince, String deviceCity, String deviceIsp, String deviceMac) {
		this.id = id;
		this.taskJobId = taskJobId;
		this.country = country;
		this.city = city;
		this.ip = ip;
		this.url = url;
		this.isp = isp;
		this.statusCode = statusCode;
		this.size = size;
		this.nslookupDuration = nslookupDuration;
		this.totalDuration = totalDuration;
		this.connectTime = connectTime;
		this.doneTime = doneTime;
		this.deviceIp = deviceIp;
		this.deviceCountry = deviceCountry;
		this.deviceProvince = deviceProvince;
		this.deviceCity = deviceCity;
		this.deviceIsp = deviceIsp;
		this.deviceMac = deviceMac;
	}
	public TaskJobDetail(TaskJobDetail taskJobDetail,String url){
		this.id = taskJobDetail.getId();
		this.taskJobId = taskJobDetail.getTaskJobId();
		this.country = taskJobDetail.getCountry();
		this.city = taskJobDetail.getCity();
		this.ip = taskJobDetail.getIp();
		this.url = url;
		this.isp = taskJobDetail.getIsp();
		this.statusCode = taskJobDetail.getStatusCode();
		this.size = taskJobDetail.getSize();
		this.nslookupDuration = taskJobDetail.getNslookupDuration();
		this.totalDuration = taskJobDetail.getTotalDuration();
		this.connectTime = taskJobDetail.getConnectTime();
		this.doneTime = taskJobDetail.getDoneTime();
		this.deviceIp = taskJobDetail.getDeviceIp();
		this.deviceCountry = taskJobDetail.getDeviceCountry();
		this.deviceProvince = taskJobDetail.getDeviceProvince();
		this.deviceCity = taskJobDetail.getDeviceCity();
		this.deviceIsp = taskJobDetail.getDeviceIsp();
		this.deviceMac = taskJobDetail.getDeviceMac();
	}
}
