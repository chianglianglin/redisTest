package com.ws.server;


import com.ws.project.model.po.TaskJobDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskJobDetailCacheService {

		void taskJobDetailPushCache();

		void taskJobDetailPushCache2(List<TaskJobDetail> taskJobDetails);

		void piperLineRedis();

		void taskJobDetailGetDetail();

		void putOneTaskJobDetail(TaskJobDetail taskJobDetail);
}
