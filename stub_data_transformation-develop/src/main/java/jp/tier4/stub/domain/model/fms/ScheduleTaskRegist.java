package jp.tier4.stub.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS スケジュールタスク登録情報
 */
@Getter
@Setter
public class ScheduleTaskRegist {

	/** タスクタイプ */
	@JsonProperty("task_type")
	private String taskType;
	/** 予約済み */
	@JsonProperty("destination_point")
	private Integer destinationPoint;
	/** 説明 */
	private String description;
}
