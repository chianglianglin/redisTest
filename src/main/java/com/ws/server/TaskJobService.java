//package com.ws.server;
//
//import com.ws.project.model.bo.TaskJobCalBo;
//import com.ws.project.model.po.TaskJob;
//import com.ws.project.model.vo.taskjob.TaskJobInsert;
//import com.ws.project.model.vo.taskjob.TaskJobQueryFromTask;
//import com.ws.project.model.vo.taskjob.TaskJobQueryVo;
//import com.ws.project.model.vo.taskjob.TaskJobVo;
//import com.ws.project.monitor.online.domain.OnlineSession;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//
//public interface TaskJobService {
//
//	TaskJob saveTaskJob(TaskJobVo taskJobVo);
//
//	TaskJob findByUUID(String uuid);
//
//	List<TaskJob> getTaskJobStatusCount(int minutes);
//
//	void saveTaskJob(TaskJob taskJob);
//
//	Page<TaskJob> selectList(TaskJobQueryVo queryVo);
//
//	Page<TaskJob> selectListByUrlAndActType(TaskJobQueryFromTask queryVo);
//
//	void deleteTaskJobByIds(String ids);
//
//	void insertAll(TaskJobInsert taskJob, OnlineSession onlineSession);
//
//	TaskJob findTaskJobById(Long id);
//
//	void editSave(TaskJob taskJob, OnlineSession onlineSession);
//
//	List<TaskJob> findByActTypeAndUrl(String actType, String url);
//
//	void saveTaskJob(TaskJobCalBo bo, String userId);
//}
