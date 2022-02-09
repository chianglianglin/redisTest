//package com.ws.server;
//
//import com.ws.project.model.bo.TaskJobDetailCalculateBo;
//import com.ws.project.model.dto.CountryInfo;
//import com.ws.project.model.dto.DetectInfo;
//import com.ws.project.model.vo.ClientInfoVo;
//import com.ws.project.model.vo.taskjob.TaskJobDetailQueryVo;
//import com.ws.project.model.vo.taskjob.TaskJobDetailVo;
//import org.springframework.data.domain.Page;
//
//import java.util.Map;
//
//public interface TaskJobDetailService {
//	void setTaskJobDetail(ClientInfoVo clientInfoVo, DetectInfo detectInfo, Long taskJobId, CountryInfo countryInfo);
//
//	Page<TaskJobDetailVo> selectList(TaskJobDetailQueryVo queryVo);
//
//	Map<Long, TaskJobDetailCalculateBo> getDetailCalculate();
//
//}
