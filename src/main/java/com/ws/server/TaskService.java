//package com.ws.server;
//
////import com.ws.project.model.bo.TaskReportBo;
//import com.ws.project.model.po.Task;
//import com.ws.project.model.po.TaskJob;
////import com.ws.project.model.po.TaskTagetUrlAndActType;
////import com.ws.project.model.vo.PageBaseVo;
////import com.ws.project.model.vo.task.TaskInsert;
////import com.ws.project.model.vo.task.TaskQueryVo;
////import com.ws.project.model.vo.task.TaskTestQueryVo;
////import com.ws.project.monitor.online.domain.OnlineSession;
////import org.springframework.data.domain.Page;
//
//import java.util.List;
//
//public interface TaskService {
//
//	List<Task> selectAllList();
//
//	List<Task> selectListByPage(int page, int size, String sortColumn, String sortIsAsc);
//
//	void save(Task entity, Integer userId);
//
//	void insert(Integer customerUserId, String targetUrl, Integer userId);
//
//	PageBaseVo<Task> selectAllList(TaskTestQueryVo queryVo);
//
//	Page<Task> selectList(TaskQueryVo queryVo);
//
//	List<TaskTagetUrlAndActType> getTaksTagetUrlAndActTypes();
//
//	void deleteTaskByIds(String ids);
//
//	Task findTaskById(Integer id);
//
//	void editSave(Task task,OnlineSession onlineSession);
//
//	void insertAll(TaskInsert task,OnlineSession onlineSession);
//
//	Task findLastTaskByTaskJob(TaskJob taskJob);
//
//	void save(List<Task> entityList, String userId);
//
//	List<Task> findTaskByTaskJob(TaskJob taskJob);
//
//	List<Task> findByCustomerUserId(Integer customerUserId);
//
//	Page<TaskReportBo> taskReport(TaskQueryVo queryVo,String userId);
//
//}
