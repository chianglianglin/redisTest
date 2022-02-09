package com.ws.project.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data()
@EqualsAndHashCode(callSuper = true)
@Table(name = "task_job")
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TaskJob extends SimpleBaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid") // 'Server customer 溝通用UUID'
	private String uuid;

	@Column(name = "act_type") // '任務行為：post、get、socket'
	private String actType;

	@Column(name = "url") // 'Table: task;Column: target_url'
	private String url;

	@Column(name = "passratio") // '成功率'
	private Float passratio;

	@Column(name = "ips") // 'IP 紀錄'
	private String ips;

	@Column(name = "total_node") // '發送節點數'
	private Integer totalNode = 0;

	@Column(name = "pass_node") // '正常響應節點數'
	private Integer passNode = 0;

	@Column(name = "lose_node") // '失敗響應節點數'
	private Integer loseNode = 0;

	@Column(name = "node2xx") // '響應2XX節點數'
	private Integer node2xx = 0;

	@Column(name = "node3xx") // '響應3XX節點數'
	private Integer node3xx = 0;

	@Column(name = "node4xx") // '響應4XX節點數'
	private Integer node4xx = 0;

	@Column(name = "node5xx") // '響應5XX節點數'
	private Integer node5xx = 0;

	@Column(name = "job_run_check") // 'task_job 統計確認'
	private Date jobRunCheck;

}
