package com.ws.controller;

import com.alibaba.fastjson.JSON;
import com.ws.project.model.po.Task;
import com.ws.project.model.po.TaskJobDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "REDIS API")
//@RestController
//@RequestMapping("/api/redis")
@RestController
public class RedisController {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@ApiOperation(value = "put string", notes = "")
	@PostMapping("put")
	public void putRedis(@RequestParam String key,@RequestParam String text) {
		redisTemplate.opsForValue().set(key, text);
	}
	
	@ApiOperation(value = "put task", notes = "")
	@PostMapping("putTask")
	public void putRedis(@RequestParam String key,@RequestBody Task task) {
		redisTemplate.opsForValue().set(key, task);
	}
	
	@ApiOperation(value = "pop task", notes = "")
	@PostMapping("get")
	public String  popRedis(@RequestParam String key) {

//		return JSON.toJSONString(redisTemplate.opsForValue().get(key));
//		List<Object> range = redisTemplate.opsForList().range(key, 0, 10);
//		range.stream().forEach(o -> System.out.println(JSON.toJSONString(o)));
		return JSON.toJSONString(redisTemplate.opsForList().range(key,0,5));
	}
	
	@ApiOperation(value = "clean redis by key", notes = "")
	@PostMapping("delete")
	public void cleanRedis(@RequestParam String key) {
		redisTemplate.delete(key);
	}
}
