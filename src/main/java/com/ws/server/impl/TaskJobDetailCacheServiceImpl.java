package com.ws.server.impl;

import com.alibaba.fastjson.JSON;
import com.ws.project.model.bo.TaskJobDetailBo;
import com.ws.project.model.po.TaskJobDetail;
//import com.ws.project.model.po.UrlJobId;
import com.ws.repository.TaskJobDetailRepository;
import com.ws.repository.TaskJobRepository;
import com.ws.repository.TaskRepository;
import com.ws.server.TaskJobDetailCacheService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskJobDetailCacheServiceImpl extends Thread implements TaskJobDetailCacheService {


	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskJobRepository taskJobRepository;

	@Autowired
	private TaskJobDetailRepository taskJobDetailRepository;

	private static final String cacheName = "taskjob-save-cache";
	private static final String myCacheName = "taskjob";
	private static final String myCacheName2 = "taskjobbackup";


	@Override
	public void taskJobDetailPushCache() {
		List<String> urls = taskRepository.selectUrl(100);
		List<Object> range = redisTemplate.opsForList().range(myCacheName, 0, 999);
		List<TaskJobDetail> taskJobDetails = range.stream().map(o -> JSON.parseObject(JSON.toJSONString(o), TaskJobDetail.class)).collect(Collectors.toList());
//		taskJobDetailRepository.saveAll(taskJobDetails);
//		System.out.println(taskJobDetails);
		List<Integer> taskJobIds = taskJobRepository.selectTaskJobId(1000);
		for (int i = 0; i < 1000; i++) {
			taskJobDetails.get(i).setTaskJobId(Long.valueOf(taskJobIds.get(i)));
			taskJobDetails.get(i).setId(null);
		}
		for (int i = 0; i < 100; i++) {
			for (int j = 0 + i * 10; j < 10 + i * 10; j++) {
				taskJobDetails.get(j).setUrl(urls.get(i));
			}
		}
		List<TaskJobDetail> taskJobDetailList = new ArrayList<>();
		int count = 100;
		for (int i = 0; i < count; i++) {
			taskJobDetailList.addAll(taskJobDetails);

		}
		redisTemplate.opsForList().leftPushAll(cacheName, taskJobDetailList);
		//占存在遠端資料KEY為myCacheName
//		List<TaskJobDetail> taskJobDetails= taskJobDetailRepository.selectHundredTaskJobDetail(1000);
//		for (TaskJobDetail taskjob: taskJobDetails){
//			taskjob.setId(null);
//			taskjob.setTaskJobId(null);
//		}
//		redisTemplate.opsForList().leftPushAll(myCacheName,taskJobDetails);
	}

	@Override
	public void taskJobDetailPushCache2(){

		List<TaskJobDetail> taskJobDetails = taskJobDetailRepository.findAll();
		for (int i = 0; i < 2000; i++) {
			taskJobDetails.get(i).setId(null);
		}
		List<TaskJobDetail> taskJobDetailList = new ArrayList<>();
		int count = 100;
		for (int i = 0; i < count; i++) {
			taskJobDetailList.addAll(taskJobDetails);

		}
		redisTemplate.opsForList().leftPushAll(cacheName, taskJobDetailList);
	}

	@Override
	public void run() {
		super.run();
	}

	//	private int[] sum(int[] nums,int target){
//		HashMap<Integer, Integer> map = new HashMap<>();
//		for (int i = 0; i < nums.length; i++) {
//			int x = target - nums[i];
//			if (map.containsKey(x)){
//				return new int[]{map.get(x),i};
//			}
//			map.put(nums[i],i);
//		}
//		return new int[0];
//	}

//	private class ListNode{
//		int val;
//		ListNode next;
//		public ListNode(){}
//		public ListNode(int val){
//			this.val = val;
//		}
//		public ListNode(int val,ListNode next){
//			this.val = val;
//			this. next = next;
//		}
//
//	}

//	private ListNode add(ListNode l1,ListNode l2){
//		ListNode dumpHead = new ListNode(0);
//		ListNode tail = dumpHead;
//		int overdit = 0;
//		while (l1 != null || l2 != null){
//
//
//			int result = l1.val + l2.val + overdit;
//			tail = tail.next =new ListNode(result % 10);
//
//		}
//
//		return dumpHead.next;
//	}

}
