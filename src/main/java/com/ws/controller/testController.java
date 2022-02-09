package com.ws.controller;

import com.ws.project.model.po.Task;
import com.ws.repository.TaskRepository;
import com.ws.server.TaskJobDetailCacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "cacheData")
@RestController
public class testController {
		@Autowired
		private TaskRepository taskRepository;

		@Autowired
		private TaskJobDetailCacheService taskJobDetailCacheService;

	@ApiOperation(value = "put task", notes = "")
	@PostMapping("putDBTask")
	public void putTask(@RequestBody Task task){
		taskRepository.save(task);
	}

	@ApiOperation(value = "put taskDetail 100 thousand")
	@PostMapping("putTaskDetailCache")
	public void putOneHundredTaskDetailCache(){
		Long time1,time2;
		time1 = System.currentTimeMillis();
//		taskJobDetailCacheService.taskJobDetailPushCache();
		taskJobDetailCacheService.taskJobDetailPushCache2();
		time2 =System.currentTimeMillis();
		System.out.println("doSomething()花了：" +(time2-time1));

	}


}
