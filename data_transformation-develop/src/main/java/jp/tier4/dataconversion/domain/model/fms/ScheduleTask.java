package jp.tier4.dataconversion.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS スケジュールタスク情報
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class ScheduleTask {

    /** タスクID */
    @JsonProperty("task_id")
    private String taskId;
    /** タスクタイプ */
    @JsonProperty("task_type")
    private String taskType;
    /** 予約済み */
    @JsonProperty("is_reserved")
    private boolean isReserved;
    /** ステータス */
    @JsonProperty("status")
    private String status;
    /** 出発点 */
    @JsonProperty("origin")
    private Place origin;
    /** 終着点 */
    @JsonProperty("destination")
    private Place destination;
    /** ルートIDリスト */
    @JsonProperty("route_ids")
    private List<String> routeIds;
    /** スケジュール開始予定時刻 */
    @JsonProperty("plan_start_time")
    private String planStartTime;
    /** スケジュール終了予定時刻 */
    @JsonProperty("plan_end_time")
    private String planEndTime;
    /** スケジュールの実際の開始時刻 */
    @JsonProperty("actual_start_time")
    private String actualStartTime;
    /** スケジュールの実際の終了時刻。 */
    @JsonProperty("actual_end_time")
    private String actualEndTime;
    /** 予定期間（秒） */
    @JsonProperty("duration_sec")
    private Integer durationSec;
    /** 説明 */
    private String description;
}
