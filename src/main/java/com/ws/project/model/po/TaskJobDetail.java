package com.ws.project.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data()
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskJobDetail extends SimpleBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Integer id = null;

	private Long taskJobId;

	private String country;

	private String city;

	private String ip;

	private String url;

	private String isp;

	private Integer statusCode;

	private Long size;

	private String nslookupDuration;

	private String totalDuration;

	private Date connectTime;

	private Date doneTime;
	
	private String deviceIp;
	
	private String deviceCountry;
	
	private String deviceProvince;
	
	private String deviceCity;
	
	private String deviceIsp;
	
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
