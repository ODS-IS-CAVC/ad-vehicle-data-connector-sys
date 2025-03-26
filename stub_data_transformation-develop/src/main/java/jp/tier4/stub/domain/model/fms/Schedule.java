package jp.tier4.stub.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * スケジュール情報
 */
@Getter
@Setter
public class Schedule {

	/** スケジュール識別子 */
	@JsonProperty("schedule_id")
	private String scheduleId;
	/** プロジェクト識別子 */
	@JsonProperty("project_id")
	private String projectId;
	/** 環境識別子 */
	@JsonProperty("environment_id")
	private String environmentId;
	/** スケジュールタイプ */
	@JsonProperty("schedule_type")
	private String scheduleType;
	/** 予約済み */
	@JsonProperty("is_reserved")
	private boolean isReserved;
	/** 車両識別子 */
	@JsonProperty("vehicle_id")
	private String vehicleId;
	/** ステータス */
	@JsonProperty("status")
	private String status;
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
	/** 予定開始時間からの遅れ・進捗状況（正：開始遅れ、負：早期開始） */
	@JsonProperty("delta_sec")
	private Integer deltaSec;

	/** タグリスト */
	@JsonProperty("tags")
	private List<Tag> tags;
	/** 作成日時 */
	@JsonProperty("created_at")
	private String createdAt;
	/** 更新日時 */
	@JsonProperty("updated_at")
	private String updatedAt;

}
