//package com.ws.server.impl;
//
//import com.ws.common.utils.DateUtils;
//import com.ws.project.model.bo.TaskReportBo;
//import com.ws.project.model.dto.DetectInfo.RequestTypeEnum;
//import com.ws.project.model.po.Task;
//import com.ws.project.model.po.TaskJob;
//import com.ws.project.model.po.TaskTagetUrlAndActType;
//import com.ws.project.model.vo.PageBaseVo;
//import com.ws.project.model.vo.task.TaskInsert;
//import com.ws.project.model.vo.task.TaskQueryVo;
//import com.ws.project.model.vo.task.TaskQueryVo.OrderColumn;
//import com.ws.project.model.vo.task.TaskTestQueryVo;
//import com.ws.project.monitor.online.domain.OnlineSession;
//import com.ws.project.repository.TaskRepository;
//import com.ws.project.service.TaskService;
//import com.ws.project.tool.JPARepositoryUtils;
//import lombok.SneakyThrows;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class TaskServiceImpl implements TaskService {
//	final String tableName = "temp_view";
//
//	@Autowired
//	private TaskRepository repository;
//
//	@Override
//	public Page<TaskReportBo> taskReport(TaskQueryVo queryVo, String userId) {
//		Sort sort = null;
//		if (StringUtils.isNotEmpty(queryVo.getOrderByColumn())) {
//			sort = (queryVo.getIsAsc().equalsIgnoreCase("ASC"))
//					? Sort.by(tableName + "." + OrderColumn.mapColumnName(queryVo.getOrderByColumn())).ascending()
//					: Sort.by(tableName + "." + OrderColumn.mapColumnName(queryVo.getOrderByColumn())).descending();
//		} else {
//			sort = Sort.by(tableName + ".task_finish_date").descending();
//		}
//		Pageable pageable = JPARepositoryUtils.buildPage(queryVo.getPageNum(), queryVo.getPageSize(), sort);
//		String startTime = DateUtils.dateTime(queryVo.getStartDate());
//		String endTime = DateUtils.dateTime(queryVo.getEndDate());
//		BigDecimal passRationG = null;
//		BigDecimal passRationL = null;
//		List<Long> tagList = new ArrayList<>(queryVo.getTagId());
//		if (CollectionUtils.isEmpty(tagList)) {
//			tagList.add(0l);
//		}
//
//		if (queryVo.getPassRatio() != null) {
//			if (queryVo.getIsBigger()) {
//				passRationG = queryVo.getPassRatio().divide(BigDecimal.valueOf(100l));
//			} else {
//				passRationL = queryVo.getPassRatio().divide(BigDecimal.valueOf(100l));
//			}
//		}
//
//		return repository.taskJobReport(startTime, endTime, userId, queryVo.getTargetUrl(), passRationG, passRationL,
//				tagList, pageable);
//	}
//
//	@Override
//	public List<Task> findByCustomerUserId(Integer customerUserId) {
//		return repository.findByCustomerUserId(customerUserId);
//
//	}
//
//	@Override
//	public List<TaskTagetUrlAndActType> getTaksTagetUrlAndActTypes() {
//		List<TaskTagetUrlAndActType> data = repository.findTaskJob();
//		return data;
//	}
//
//	@Override
//	public List<Task> findTaskByTaskJob(TaskJob taskJob) {
//		return repository.findByActTypeAndTargetUrlAndTaskFinishDateGreaterThanEqualAndCreateTimeLessThanEqual(
//				taskJob.getActType(), taskJob.getUrl(), taskJob.getCreateTime(), taskJob.getCreateTime());
//	}
//
//	@Override
//	public Task findLastTaskByTaskJob(TaskJob taskJob) {
//		List<Task> entityList = repository.findByActTypeAndTargetUrlAndTaskFinishDateGreaterThanEqual(
//				taskJob.getActType(), taskJob.getUrl(), new Date());
//		if (CollectionUtils.isNotEmpty(entityList)) {
//			return entityList.stream().sorted(Comparator.comparing(Task::getTaskFinishDate).reversed())
//					.collect(Collectors.toList()).get(0);
//		}
//		return null;
//	}
//
//	@Override
//	public List<Task> selectAllList() {
//		return null;
//	}
//
//	@Override
//	@SneakyThrows
//	public Page<Task> selectList(TaskQueryVo queryVo) {
//		Sort sort = null;
//		if (StringUtils.isNotEmpty(queryVo.getOrderByColumn())) {
//			sort = (queryVo.getIsAsc().equalsIgnoreCase("ASC")) ? Sort.by(queryVo.getOrderByColumn()).ascending()
//					: Sort.by(queryVo.getOrderByColumn()).descending();
//		}
//
//		Page<Task> taskPage = repository.findAll(
//				JPARepositoryUtils.<Task>buildAllFormatSpecification(queryVo, TaskQueryVo.class),
//				JPARepositoryUtils.buildPage(queryVo.getPageNum(), queryVo.getPageSize(), sort));
//
//		return taskPage;
//	}
//
//	@Override
//	@SneakyThrows
//	public PageBaseVo<Task> selectAllList(TaskTestQueryVo queryVo) {
//		Sort sort = null;
//		if (StringUtils.isNotEmpty(queryVo.getOrderColumn())) {
//			sort = Sort.by(queryVo.getOrderColumn());
//		}
//
//		PageBaseVo<Task> result = new PageBaseVo<>();
//		Page<Task> taskPage = repository.findAll(
//				JPARepositoryUtils.<Task>buildAllFormatSpecification(queryVo, TaskTestQueryVo.class),
//				JPARepositoryUtils.buildPageAble(queryVo.getPage(), queryVo.getPageSize(), sort));
//
//		result.setTotalElements(taskPage.getTotalElements());
//		result.setTotalPages(taskPage.getTotalPages());
//		if (!CollectionUtils.isEmpty(taskPage.getContent())) {
//			taskPage.getContent().forEach(task -> {
//				result.getList().add(task);
//			});
//		}
//		return result;
//	}
//
//	@Override
//	public List<Task> selectListByPage(int page, int size, String sortColumn, String sortIsAsc) {
//		return null;
//	}
//
//	@Override
//	@SneakyThrows
//	public void insertAll(TaskInsert task, OnlineSession onlineSession) {
//		String[] url = task.getTargetUrl().split("\r\n");
//		List<Task> dataList = new ArrayList<>();
//		for (String value : url) {
//			if (value.equals("")) {
//				continue;
//			}
//			Task entity = new Task();
//			String id = onlineSession.getUserId().toString();
//			entity.setUpdateBy(id);
//			entity.setCreateBy(id);
//			entity.setCustomerUserId(Integer.parseInt(id));
//			entity.setTargetUrl(value);
//			entity.setActType(task.getActType());
//			entity.setTaskFinishDate(
//					DateUtils.addHours(DateUtils.getNowDate(), Integer.parseInt(task.getTaskFinishDate())));
//			dataList.add(entity);
//		}
//		repository.saveAll(dataList);
//	}
//
//	@Override
//	public void insert(Integer customerUserId, String targetUrl, Integer userId) {
//		Task entity = new Task();
//		entity.setCustomerUserId(customerUserId);
//		entity.setTargetUrl(targetUrl);
//		this.save(entity, userId);
//	}
//
//	@Override
//	@SneakyThrows
//	public void save(Task entity, Integer userId) {
//		if (userId == 0 || userId == null) {
//			throw new Exception("userId 必須輸入");
//		}
//		if (entity.getCustomerUserId() == 0 || entity.getCustomerUserId() == null) {
//			throw new Exception("customer user id 必須輸入");
//		}
//		if (StringUtils.isBlank(entity.getCreateBy())) {
//			entity.setCreateBy(userId.toString());
//		}
//		entity.setUpdateBy(userId.toString());
//		repository.save(entity);
//	}
//
//	@Override
//	@SneakyThrows
//	public void save(List<Task> entityList, String userId) {
//		if (StringUtils.isBlank(userId)) {
//			throw new Exception("userId 必須輸入");
//		}
//		entityList.forEach(e -> {
//			if (StringUtils.isBlank(e.getCreateBy())) {
//				e.setCreateBy(userId);
//			}
//			e.setUpdateBy(userId.toString());
//		});
//		repository.saveAll(entityList);
//	}
//
//	@Override
//	@SneakyThrows
//	public void editSave(Task task, OnlineSession onlineSession) {
//
//		if (task.getId() == 0 || task.getId() == null) {
//			throw new Exception("userId 必須輸入");
//		}
//
//		if (task.getTaskFinishDate() == null) {
//			throw new Exception("TaskFinishDate 必須輸入");
//		}
//
//		if (task.getTargetUrl() == null || task.getTargetUrl().equals("")) {
//			throw new Exception("TargetUrl 必須輸入");
//		}
//
//		RequestTypeEnum actType = RequestTypeEnum.findEnumByEnumValue(task.getActType());
//		if (actType == null) {
//			throw new Exception("ActType 輸入錯誤");
//		}
//
//		Task taskEdit = findTaskById(task.getId());
//		taskEdit.setTargetUrl(task.getTargetUrl());
//		taskEdit.setTaskFinishDate(task.getTaskFinishDate());
//		taskEdit.setActType(actType.getValue());
//		taskEdit.setUpdateBy(onlineSession.getUserId().toString());
//		taskEdit.setUpdateTime(new Date());
//		repository.save(taskEdit);
//	}
//
//	@Override
//	@SneakyThrows
//	public void deleteTaskByIds(String ids) {
//
//		List<Integer> taskIds = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
//		List<Task> tasks = repository.findAllById(taskIds);
//		repository.deleteAll(tasks);
//	}
//
//	@Override
//	public Task findTaskById(Integer id) {
//		return repository.findById(id).get();
//	}
//
//}
