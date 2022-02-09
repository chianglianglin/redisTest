//package com.ws.server.impl;
//
//import com.ws.project.model.bo.TaskJobDetailCalculateBo;
//import com.ws.project.model.dto.CountryInfo;
//import com.ws.project.model.dto.DetectInfo;
//import com.ws.project.model.dto.DetectInfo.RequestTypeEnum;
//import com.ws.project.model.po.TaskJobDetail;
//import com.ws.project.model.po.TaskJobStatusCodePo;
//import com.ws.project.model.vo.ClientInfoVo;
//import com.ws.project.model.vo.response.GetResponseVo;
//import com.ws.project.model.vo.response.PostResponseVo;
//import com.ws.project.model.vo.taskjob.TaskJobDetailQueryVo;
//import com.ws.project.model.vo.taskjob.TaskJobDetailVo;
//import com.ws.project.repository.TaskJobDetailRepository;
//import com.ws.project.service.TaskJobDetailService;
//import com.ws.project.tool.JPARepositoryUtils;
//import lombok.SneakyThrows;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Transactional
//public class TaskJobDetailServiceImpl implements TaskJobDetailService {
//
//	static final String CREATE_BY = "DETECT";
//
//	@Autowired
//	private TaskJobDetailRepository repository;
//
//	@Override
//	public void setTaskJobDetail(ClientInfoVo clientInfoVo, DetectInfo detectInfo, Long taskJobId,
//			CountryInfo countryInfo) {
//		TaskJobDetail tjd = new TaskJobDetail();
//		tjd.setTaskJobId(taskJobId);
//		if (countryInfo != null) {
//			tjd.setCountry(countryInfo.getCountry());
//			tjd.setCity(countryInfo.getProvince());
//			tjd.setIsp(countryInfo.getIsp());
//		}
//		if (clientInfoVo != null) {
//			tjd.setDeviceIp(clientInfoVo.getIp());
//			tjd.setDeviceMac(clientInfoVo.getMacAddress());
//			if (clientInfoVo.getCountryInfo() != null) {
//				tjd.setDeviceCountry(clientInfoVo.getCountryInfo().getCountry());
//				tjd.setDeviceIsp(clientInfoVo.getCountryInfo().getIsp());
//				tjd.setDeviceProvince(clientInfoVo.getCountryInfo().getProvince());
//				tjd.setDeviceCity(clientInfoVo.getCountryInfo().getCity());
//			}
//		}
//		tjd.setCreateBy(CREATE_BY);
//		if (detectInfo != null && detectInfo.getServerRequestVo() != null) {
//			tjd.setUrl(detectInfo.getServerRequestVo().getTargetUrl());
//			if (StringUtils.equals(RequestTypeEnum.GET.getValue(), detectInfo.getServerRequestVo().getRequestType())) {
//				GetResponseVo resVo = detectInfo.getGetResponseVo();
//				tjd.setIp(resVo.getTargetIp());
//				tjd.setStatusCode(Integer.valueOf(resVo.getStatusCode()));
//				if (resVo.getContentLength() != null) {
//					tjd.setSize(resVo.getContentLength().longValue());
//				}
//				if (resVo.getDnsDuration() != null) {
//					tjd.setNslookupDuration(resVo.getDnsDuration().toString());
//				}
//				if (resVo.getTotalDuration() != null) {
//					tjd.setTotalDuration(resVo.getTotalDuration().toString());
//				}
//				tjd.setConnectTime(resVo.getRequestTime());
//				tjd.setDoneTime(resVo.getResponseTime());
//			} else if (StringUtils.equals(RequestTypeEnum.POST.getValue(), detectInfo.getServerRequestVo().getRequestType())) {
//				PostResponseVo resVo = detectInfo.getPostResponseVo();
//				tjd.setIp(resVo.getTargetIp());
//				tjd.setStatusCode(Integer.valueOf(resVo.getStatusCode()));
//				if (resVo.getContentLength() != null) {
//					tjd.setSize(resVo.getContentLength().longValue());
//				}
//				if (resVo.getDnsDuration() != null) {
//					tjd.setNslookupDuration(resVo.getDnsDuration().toString());
//				}
//				if (resVo.getTotalDuration() != null) {
//					tjd.setTotalDuration(resVo.getTotalDuration().toString());
//				}
//				tjd.setConnectTime(resVo.getRequestTime());
//				tjd.setDoneTime(resVo.getResponseTime());
//			}
//		}
//		repository.save(tjd);
//	}
//
//	@Override
//	public Map<Long, TaskJobDetailCalculateBo> getDetailCalculate() {
//		Map<Long, TaskJobDetailCalculateBo> result = new HashMap<>();
//		List<TaskJobStatusCodePo> count2List = repository.getTaskJobStatusCout("2%");
//		List<TaskJobStatusCodePo> count3List = repository.getTaskJobStatusCout("3%");
//		List<TaskJobStatusCodePo> count4List = repository.getTaskJobStatusCout("4%");
//		count2List.forEach(c -> {
//			TaskJobDetailCalculateBo bo = result.get(c.getTask_job_id());
//			if (bo == null) {
//				bo = new TaskJobDetailCalculateBo();
//				bo.setTaskJobId(c.getTask_job_id());
//			}
//			bo.setCount2(c.getCount());
//			result.put(bo.getTaskJobId(), bo);
//		});
//		count3List.forEach(c -> {
//			TaskJobDetailCalculateBo bo = result.get(c.getTask_job_id());
//			if (bo == null) {
//				bo = new TaskJobDetailCalculateBo();
//				bo.setTaskJobId(c.getTask_job_id());
//			}
//			bo.setCount3(c.getCount());
//			result.put(bo.getTaskJobId(), bo);
//		});
//		count4List.forEach(c -> {
//			TaskJobDetailCalculateBo bo = result.get(c.getTask_job_id());
//			if (bo == null) {
//				bo = new TaskJobDetailCalculateBo();
//				bo.setTaskJobId(c.getTask_job_id());
//			}
//			bo.setCount4(c.getCount());
//			result.put(bo.getTaskJobId(), bo);
//		});
//
//		return result;
//	}
//
//	@SneakyThrows
//	@Override
//	public Page<TaskJobDetailVo> selectList(TaskJobDetailQueryVo queryVo) {
//		Sort sort = null;
//		if (StringUtils.isNotEmpty(queryVo.getOrderByColumn())) {
//			sort = (queryVo.getIsAsc().equalsIgnoreCase("ASC")) ? Sort.by(queryVo.getOrderByColumn()).ascending()
//					: Sort.by(queryVo.getOrderByColumn()).descending();
//		}
//		Page<TaskJobDetail> taskPage = repository.findAll(
//				JPARepositoryUtils.<TaskJobDetail>buildAllFormatSpecification(queryVo, TaskJobDetailQueryVo.class),
//				JPARepositoryUtils.buildPage(queryVo.getPageNum(), queryVo.getPageSize(), sort));
//		Page<TaskJobDetailVo> taskJobDetailVos = Page.empty();
//		if (CollectionUtils.isNotEmpty(taskPage.getContent())) {
//			taskJobDetailVos = taskPage.map(TaskJobDetailVo::new);
//		}
//		return taskJobDetailVos;
//	}
//}
