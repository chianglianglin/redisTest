package com.ws.controller;

import com.ws.config.AsyncConfiguration;
import com.ws.project.model.po.Task;
import com.ws.project.model.po.TaskJobDetail;
import com.ws.project.model.vo.TimeCountVo;
import com.ws.repository.TaskJobDetailRepository;
import com.ws.repository.TaskRepository;
import com.ws.server.TaskJobDetailCacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Api(tags = "cacheData")
@RestController
public class testController {
	private static final String cacheName = "taskjob-save-cache";

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskJobDetailRepository taskJobDetailRepository;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private TaskJobDetailCacheService taskJobDetailCacheService;

//	@ApiOperation(value = "put task", notes = "")
//	@PostMapping("putDBTask")
//	public void putTask(@RequestBody Task task) {
//		taskRepository.save(task);
//	}

	@ApiOperation(value = "put taskDetail")
	@PostMapping("putTaskDetailCache")
	public TimeCountVo putTaskDetailCache(int count) throws InterruptedException {

		ExecutorService eService = Executors.newFixedThreadPool(10);
		TaskJobDetail taskJobDetail = taskJobDetailRepository.selectOneTaskJobDetail();
		if (taskJobDetail != null){
			taskJobDetail.setId(null);
		}
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
		int count = 0;
		if (redisTemplate.hasKey(cacheName)) {
			while (redisTemplate.opsForList().size(cacheName) > 0) {
				redisTemplate.opsForList().rightPop(cacheName);
				count++;
			}
		}
		time2 = System.currentTimeMillis();
		Long totalTime = (time2 - time1)/1000;
		System.out.println("花了：" + totalTime + " 秒");
		return new TimeCountVo("花了：" + totalTime + " 秒",count);
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

//	@ApiOperation(value = "test one thread redis taskJobDetail")
//	@PostMapping("oneThread")
//	public void redisOneThread(){
//		Long time1,time2;
//		time1 = System.currentTimeMillis();
//		taskJobDetailCacheService.taskJobDetailGetDetail();
//		time2 = System.currentTimeMillis();
//		System.out.println("doController()花了：" + (time2 - time1));
//	}
//
//	@ApiOperation(value = "test  redisutils taskJobDetail")
//	@PostMapping("redisUtils")
//	public void redisUtils(){
//		List<TaskJobDetail> taskJobDetails = taskJobDetailRepository.selectHundredTaskJobDetail(1);
//
//		for (int i = 0; i < taskJobDetails.size(); i++) {
//			taskJobDetails.get(i).setId(null);
//		}
//		Long time1;
//		time1 = System.currentTimeMillis();
//		System.out.println("mutlithread start" + time1);
//
//		int count = 10000;
//		for (int i = 0; i < count; i++) {
//
//			taskJobDetailCacheService.taskJobDetailPushCache2(taskJobDetails);
////			task(taskJobDetails, i);
////			taskJobDetailList.addAll(taskJobDetails);
//		}
//	}
//
//	public int findContentChildren(int[] g, int[] s) {
//		boolean[] successContent = new boolean[s.length];
//		for (int i = 0; i < successContent.length; i++) {
//			successContent[i] = false;
//		}
//		int successContentChildren = 0;
//
//		for(int i = 0 ; i < s.length; i++){
//			for (int j = 0; j < g.length; j++) {
//				if (s[i] == g[j] && successContent[i]==false){
//					successContent[i] = true;
//					successContentChildren++;
//				}
//			}
//		}
//		return successContentChildren;
//	}
}
