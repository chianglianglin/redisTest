package com.ws.controller;

import com.ws.project.model.po.Task;
import com.ws.project.model.po.TaskJobDetail;
import com.ws.repository.TaskJobDetailRepository;
import com.ws.repository.TaskRepository;
import com.ws.server.TaskJobDetailCacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Api(tags = "cacheData")
@RestController
public class testController {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskJobDetailRepository taskJobDetailRepository;

	@Autowired
	private TaskJobDetailCacheService taskJobDetailCacheService;

	@ApiOperation(value = "put task", notes = "")
	@PostMapping("putDBTask")
	public void putTask(@RequestBody Task task) {
		taskRepository.save(task);
	}

	@ApiOperation(value = "put taskDetail 100 thousand")
	@PostMapping("putTaskDetailCache")
	public void putOneHundredTaskDetailCache(int count) {

//		List<TaskJobDetail> taskJobDetails = taskJobDetailRepository.selectHundredTaskJobDetail(1);
		TaskJobDetail taskJobDetail = taskJobDetailRepository.selectOneTaskJobDetail();
		if (taskJobDetail != null){
			taskJobDetail.setId(null);
		}
		List<TaskJobDetail> taskJobDetailList = new ArrayList<>();
		Long time1,time2;
		time1 = System.currentTimeMillis();
		System.out.println("mutlithread start" + time1);

//		for (int i = 0; i < 7; i++) {
//			taskJobDetailList.addAll(taskJobDetails);
//		}
//		int count = 10000;
		for (int i = 0; i < count; i++) {
			taskJobDetailCacheService.putOneTaskJobDetail(taskJobDetail);
//			taskJobDetailCacheService.taskJobDetailPushCache2(taskJobDetails);
//			task(taskJobDetails, i);
//			taskJobDetailList.addAll(taskJobDetails);
		}
//		taskJobDetailCacheService.taskJobDetailPushCache();
		time2 = System.currentTimeMillis();
		System.out.println("doController()花了：" + (time2 - time1));

	}

	@ApiOperation(value = "test one thread redis taskJobDetail")
	@PostMapping("oneThread")
	public void redisOneThread(){
		Long time1,time2;
		time1 = System.currentTimeMillis();
		taskJobDetailCacheService.taskJobDetailGetDetail();
		time2 = System.currentTimeMillis();
		System.out.println("doController()花了：" + (time2 - time1));
	}

	@ApiOperation(value = "test  redisutils taskJobDetail")
	@PostMapping("redisUtils")
	public void redisUtils(){
		List<TaskJobDetail> taskJobDetails = taskJobDetailRepository.selectHundredTaskJobDetail(1);

		for (int i = 0; i < taskJobDetails.size(); i++) {
			taskJobDetails.get(i).setId(null);
		}
		Long time1;
		time1 = System.currentTimeMillis();
		System.out.println("mutlithread start" + time1);

		int count = 10000;
		for (int i = 0; i < count; i++) {

			taskJobDetailCacheService.taskJobDetailPushCache2(taskJobDetails);
//			task(taskJobDetails, i);
//			taskJobDetailList.addAll(taskJobDetails);
		}
	}
}
