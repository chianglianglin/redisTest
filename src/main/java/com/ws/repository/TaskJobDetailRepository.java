package com.ws.repository;

import com.ws.project.model.po.TaskJobDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskJobDetailRepository extends JpaRepository<TaskJobDetail, Integer>, JpaSpecificationExecutor<TaskJobDetail> {

	@Query(value = "SELECT  id ,task_job_id ,country, city ,ip, url,isp,status_code,size,nslookup_duration,total_duration,connect_time,done_time,create_time,create_by,device_ip,device_country,device_province,device_city,device_isp,device_mac FROM task_job_detail LIMIT :count",nativeQuery = true)
	List<TaskJobDetail> selectHundredTaskJobDetail(int count);

	@Query(value = "SELECT  task_job_id  FROM task_job_detail LIMIT :count" , nativeQuery = true)
	List<Long> selectTaskJobId(int count);

}
