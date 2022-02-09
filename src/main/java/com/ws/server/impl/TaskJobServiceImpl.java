//package com.ws.server.impl;
//
//import com.ws.common.utils.DateUtils;
//import com.ws.project.model.bo.TaskJobCalBo;
//import com.ws.project.model.bo.TaskJobDetailCalculateBo;
//import com.ws.project.model.bo.TaskJobSocketDetailCalculateBo;
//import com.ws.project.model.dto.DetectInfo.RequestTypeEnum;
//import com.ws.project.model.po.TaskJob;
//import com.ws.project.model.vo.taskjob.TaskJobInsert;
//import com.ws.project.model.vo.taskjob.TaskJobQueryFromTask;
//import com.ws.project.model.vo.taskjob.TaskJobQueryVo;
//import com.ws.project.model.vo.taskjob.TaskJobVo;
//import com.ws.project.monitor.online.domain.OnlineSession;
//import com.ws.project.repository.TaskJobRepository;
//import com.ws.project.service.TaskJobService;
//import com.ws.project.tool.JPARepositoryUtils;
//import lombok.SneakyThrows;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.util.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class TaskJobServiceImpl implements TaskJobService {
//
//	private static final String CREATE_BY = "DETECT";
//
//	@Autowired
//	private TaskJobRepository repository;
//
//	@Override
//	@CachePut(value="taskjob-info-cache",key="#taskJobVo.uuid")
//	public TaskJob saveTaskJob(TaskJobVo taskJobVo) {
//		List<TaskJob> entityList = repository.findByUuid(taskJobVo.getUuid());
//		if (CollectionUtils.isEmpty(entityList)) {
//			TaskJob taskJob = new TaskJob();
//			taskJob.setActType(taskJobVo.getActType());
//			taskJob.setUrl(taskJobVo.getUrl());
//			taskJob.setTotalNode(taskJobVo.getTotalNode());
//			taskJob.setUuid(taskJobVo.getUuid());
//			taskJob.setCreateBy(CREATE_BY);
//			return repository.save(taskJob);
//		}
//		return null;
//	}
//
//	@Override
//	public void saveTaskJob(TaskJobCalBo bo, String userId) {
//		TaskJob entity = bo.getTaskJob();
//		if ((StringUtils.equals(RequestTypeEnum.GET.getValue(), entity.getActType()))
//				|| (StringUtils.equals(RequestTypeEnum.POST.getValue(), entity.getActType()))) {
//			TaskJobDetailCalculateBo calBo = bo.getDetailMap().get(entity.getId());
//			if (calBo != null) {
//				int count2 = calBo.getCount2() == null ? 0 : calBo.getCount2();
//				int count3 = calBo.getCount3() == null ? 0 : calBo.getCount3();
//				int count4 = calBo.getCount4() == null ? 0 : calBo.getCount4();
//				entity.setNode2xx(count2);
//				entity.setNode3xx(count3);
//				entity.setNode4xx(count4);
//				entity.setNode5xx(entity.getTotalNode() - count2 - count3 - count4);
//				entity.setPassNode(count2 + count3);
//				entity.setLoseNode(entity.getTotalNode() - count2 - count3);
//				BigDecimal pass = BigDecimal.valueOf(Long.valueOf(entity.getPassNode()));
//				BigDecimal total = BigDecimal.valueOf(Long.valueOf(entity.getTotalNode()));
//				BigDecimal passratio = pass.divide(total, 2, RoundingMode.HALF_UP);
//				entity.setPassratio(passratio.floatValue());
//			} else {
//				entity.setNode2xx(0);
//				entity.setNode3xx(0);
//				entity.setNode4xx(0);
//				entity.setNode5xx(entity.getTotalNode());
//				entity.setPassNode(0);
//				entity.setLoseNode(entity.getTotalNode());
//				entity.setPassratio(0f);
//			}
//		} else if (StringUtils.equals(RequestTypeEnum.SOCKET.getValue(), entity.getActType())) {
//			TaskJobSocketDetailCalculateBo calBo = bo.getSocketMap().get(entity.getId());
//			if (calBo != null) {
//				int countPass = (calBo.getCountPass() == null) ? 0 : calBo.getCountPass();
//				entity.setNode5xx(0);
//				entity.setPassNode(countPass);
//				entity.setLoseNode(entity.getTotalNode() - countPass);
//				BigDecimal pass = BigDecimal.valueOf(Long.valueOf(entity.getPassNode()));
//				BigDecimal total = BigDecimal.valueOf(Long.valueOf(entity.getTotalNode()));
//				BigDecimal passratio = pass.divide(total, 2, RoundingMode.HALF_UP);
//				entity.setPassratio(passratio.floatValue());
//			} else {
//				entity.setNode5xx(entity.getTotalNode());
//				entity.setPassNode(0);
//				entity.setLoseNode(entity.getTotalNode());
//				entity.setPassratio(0f);
//			}
//			entity.setNode2xx(0);
//			entity.setNode3xx(0);
//			entity.setNode4xx(0);
//		}
//		entity.setJobRunCheck(DateUtils.getNowDate());
//		entity.setCreateBy(userId);
//		repository.save(entity);
//	}
//
//	@Cacheable(value = "taskjob-info-cache")
//	@Override
//	public TaskJob findByUUID(String uuid) {
//		List<TaskJob> taskJobList = new ArrayList<>();
//		taskJobList.addAll(repository.findByUuid(uuid));
//
//		TaskJob result = taskJobList.stream().sorted(Comparator.comparing(TaskJob::getCreateTime).reversed())
//				.collect(Collectors.toList()).get(0);
//		return result;
//	}
//
//	@Override
//	public List<TaskJob> getTaskJobStatusCount(int minutes) {
//		return repository.getTaskJobStatusCout(minutes);
//	}
//
//	@Override
//	public void saveTaskJob(TaskJob taskJob) {
//		repository.save(taskJob);
//	}
//
//	@Override
//	@SneakyThrows
//	public Page<TaskJob> selectList(TaskJobQueryVo queryVo) {
//		Sort sort = null;
//		if (StringUtils.isNotEmpty(queryVo.getOrderByColumn())) {
//			sort = (queryVo.getIsAsc().equalsIgnoreCase("ASC")) ? Sort.by(queryVo.getOrderByColumn()).ascending()
//					: Sort.by(queryVo.getOrderByColumn()).descending();
//		}
//
//		Page<TaskJob> taskPage = repository.findAll(
//				JPARepositoryUtils.<TaskJob>buildAllFormatSpecification(queryVo, TaskJobQueryVo.class),
//				JPARepositoryUtils.buildPage(queryVo.getPageNum(), queryVo.getPageSize(), sort));
//
//		return taskPage;
//	}
//
//	@Override
//	@SneakyThrows
//	public void deleteTaskJobByIds(String ids) {
//
//		List<Long> taskIds = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
//		List<TaskJob> tasks = repository.findAllById(taskIds);
//		repository.deleteAll(tasks);
//	}
//
//	@Override
//	@SneakyThrows
//	public void insertAll(TaskJobInsert taskJob, OnlineSession onlineSession) {
//		String[] url = taskJob.getUrl().split("\r\n");
//		List<TaskJob> dataList = new ArrayList<>();
//		for (String value : url) {
//			if (value.equals("")) {
//				continue;
//			}
//			TaskJob entity = new TaskJob();
//			String id = onlineSession.getUserId().toString();
//			entity.setCreateBy(id);
//			entity.setUrl(value);
//			entity.setActType(taskJob.getActType());
//			entity.setIps(taskJob.getIps());
//			entity.setPassratio(taskJob.getPassratio());
//			entity.setTotalNode(taskJob.getTotalNode());
//			entity.setUuid(taskJob.getUuid());
//			entity.setPassNode(taskJob.getPassNode());
//			entity.setLoseNode(taskJob.getLoseNode());
//			entity.setNode2xx(taskJob.getNode2xx());
//			entity.setNode3xx(taskJob.getNode3xx());
//			entity.setNode4xx(taskJob.getNode4xx());
//			entity.setNode5xx(taskJob.getNode5xx());
//			entity.setJobRunCheck(taskJob.getJobRunCheck());
//
//			dataList.add(entity);
//		}
//		repository.saveAll(dataList);
//	}
//
//	@Override
//	public TaskJob findTaskJobById(Long id) {
//		return repository.findById(id).get();
//	}
//
//	@Override
//	@SneakyThrows
//	public void editSave(TaskJob taskJob, OnlineSession onlineSession) {
//		if (taskJob.getId() == 0 || taskJob.getId() == null) {
//			throw new Exception("userId 必須輸入");
//		}
//		if (taskJob.getTotalNode() == null || taskJob.getTotalNode().equals("")) {
//			throw new Exception("totalNode 必須輸入");
//		}
//		if (taskJob.getPassNode() == null) {
//			throw new Exception("passNode 必須輸入");
//		}
//		if (taskJob.getLoseNode() == null) {
//			throw new Exception("loseNode 必須輸入");
//		}
//		if (taskJob.getNode2xx() == null) {
//			throw new Exception("node2xx 必須輸入");
//		}
//		if (taskJob.getNode3xx() == null) {
//			throw new Exception("node3xx 必須輸入");
//		}
//		if (taskJob.getNode4xx() == null) {
//			throw new Exception("node4xx 必須輸入");
//		}
//		if (taskJob.getNode5xx() == null) {
//			throw new Exception("node5xx 必須輸入");
//		}
//		RequestTypeEnum actType = RequestTypeEnum.findEnumByEnumValue(taskJob.getActType());
//		if (actType == null) {
//			throw new Exception("ActType 輸入錯誤");
//		}
//
//		TaskJob taskEdit = findTaskJobById(taskJob.getId());
//		taskEdit.setUrl(taskJob.getUrl());
//		taskEdit.setActType(actType.getValue());
//		taskEdit.setIps(taskJob.getIps());
//		taskEdit.setPassratio(taskJob.getPassratio());
//		taskEdit.setTotalNode(taskJob.getTotalNode());
//		taskEdit.setUuid(taskJob.getUuid());
//		taskEdit.setPassNode(taskJob.getPassNode());
//		taskEdit.setLoseNode(taskJob.getLoseNode());
//		taskEdit.setNode2xx(taskJob.getNode2xx());
//		taskEdit.setNode3xx(taskJob.getNode3xx());
//		taskEdit.setNode4xx(taskJob.getNode4xx());
//		taskEdit.setNode5xx(taskJob.getNode5xx());
//		taskEdit.setJobRunCheck(taskJob.getJobRunCheck());
//		repository.save(taskEdit);
//	}
//
//	@Override
//	public List<TaskJob> findByActTypeAndUrl(String actType, String url) {
//		return repository.findByActTypeAndUrl(actType, url);
//	}
//
//	@Override
//	@SneakyThrows
//	public Page<TaskJob> selectListByUrlAndActType(TaskJobQueryFromTask queryVo) {
//		Sort sort = null;
//		if (StringUtils.isNotEmpty(queryVo.getOrderByColumn())) {
//			sort = (queryVo.getIsAsc().equalsIgnoreCase("ASC")) ? Sort.by(queryVo.getOrderByColumn()).ascending()
//					: Sort.by(queryVo.getOrderByColumn()).descending();
//		}
//		Page<TaskJob> taskPage = repository.findAll(
//				JPARepositoryUtils.<TaskJob>buildAllFormatSpecification(queryVo, TaskJobQueryFromTask.class),
//				JPARepositoryUtils.buildPage(queryVo.getPageNum(), queryVo.getPageSize(), sort));
//		return taskPage;
//	}
//}
