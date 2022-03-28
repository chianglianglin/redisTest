package com.ws.controller;


import com.ws.project.model.po.TaskJobDetail;
import com.ws.project.model.vo.TimeCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Api(tags = "cacheData")
@RestController
public class testController {
	private static final String cacheName = "taskjob-save-cache";

	@Autowired
	private RedisTemplate redisTemplate;

	@ApiOperation(value = "put taskDetail")
	@PostMapping("putTaskDetailCache")
	public TimeCountVo putTaskDetailCache(int count) throws InterruptedException {

		ExecutorService eService = Executors.newFixedThreadPool(10);
		TaskJobDetail taskJobDetail = new TaskJobDetail(11, 1l, "sdfwe", "sfwe", "2.2.2.2", "232..323.", "dwefw", 111, 23l, "wefww", "dfwefwf", new Date(),
				new Date(), "fwefwe", "sfwefw", "wefwf", "dwfef", "fwefw", "wfewf");
		List<TaskJobDetail> taskJobDetailList = new ArrayList<>();
		Long time1,time2;
		time1 = System.currentTimeMillis();
		System.out.println("mutlithread start" + time1);
		for (int i = 0; i < count; i++) {

			eService.execute(() ->{
				redisPutMethod(taskJobDetail);
			});
		}
		eService.shutdown();
		eService.awaitTermination(1, TimeUnit.MINUTES);

		time2 = System.currentTimeMillis();
		Long totalTime = (time2 - time1)/1000;
		System.out.println("花了：" + totalTime + " 秒");

		return new TimeCountVo("花了：" + totalTime + " 秒",count);



	}

	@ApiOperation(value = "pop taskDetail")
	@PostMapping("popTaskDetailCache")
	public TimeCountVo popTaskDetailCache() throws InterruptedException {


		Long time1,time2;
		time1 = System.currentTimeMillis();
		int totalCount = 0;

		if (redisTemplate.hasKey(cacheName)) {
			while (redisTemplate.opsForList().size(cacheName) > 0) {
				redisTemplate.opsForList().rightPop(cacheName);
				totalCount++;
			}
		}

		time2 = System.currentTimeMillis();
		Long totalTime = (time2 - time1)/1000;
		System.out.println("花了：" + totalTime + " 秒");
		return new TimeCountVo("花了：" + totalTime + " 秒",totalCount);
	}

	private void redisPutMethod(TaskJobDetail taskJobDetail){
		redisTemplate.opsForList().leftPush(cacheName,taskJobDetail);
	}
	private void redisPopMethod(){
		if (redisTemplate.hasKey(cacheName)) {
			while (redisTemplate.opsForList().size(cacheName) > 0) {
				redisTemplate.opsForList().rightPop(cacheName);
			}
		}
	}

}
