package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS スケジュールタスク登録情報
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class ScheduleTaskRegist {

    /** タスクタイプ */
    @JsonProperty("task_type")
    private String taskType;
    /** バス停ID */
    @JsonProperty("destination_point")
    private Integer destinationPoint;
}
