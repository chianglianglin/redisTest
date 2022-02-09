package com.ws.repository;

import com.ws.project.model.po.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 第一個參數為訪問的實體，第二參數是這個Entity ID的資料型態
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {


	@Query(value = "SELECT distinct url FROM task_job LIMIT :count" ,nativeQuery = true)
	List<String> selectUrl(@Param("count") int  count);

}