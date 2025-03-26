package jp.tier4.stub.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS API スケジュール情報取得モデル
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveSchedule extends Schedule {
	/** タスクリスト */
	private List<ScheduleTask> tasks;
}