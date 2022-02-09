package com.ws.repository;

import com.ws.project.model.po.TaskJob;
import com.ws.project.model.po.TaskJobDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskJobRepository extends JpaRepository<TaskJob, Long>, JpaSpecificationExecutor<TaskJob> {

	@Query(value = "SELECT  id  FROM task_job LIMIT :count",nativeQuery = true)
	List<Integer> selectTaskJobId(int count);
}
