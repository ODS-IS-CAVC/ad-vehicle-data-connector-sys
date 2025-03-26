package jp.tier4.dataconversion.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 自動運転車両スケジュールデータモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class VehicleScheduleDataModel {

    /** 車両スケジュールID */
    private String vehicleScheduleId;
    /** 車両ID */
    private String vehicleId;
    /** 車両スケジュールステータス */
    private String vehicleScheduleStatus;
    /** 予定開始時間 */
    private String vehicleSchedulePlanStartTime;
    /** 予定終了時間 */
    private String vehicleSchedulePlanEndTime;
    /** 実績開始時間 */
    private String vehicleScheduleActualStartTime;
    /** 実績終了時間 */
    private String vehicleScheduleActualEndTime;
    /** 予定開始時間から予定終了時間の秒数 */
    private Integer vehicleScheduleDurationSec;
    /** 予定開始時間からの遅れ・進捗状況（正：開始遅れ、負：早期開始） */
    private Integer vehicleScheduleDeltaSec;
    /** 自動運転車両スケジュールタスクリスト */
    private List<VehicleScheduleTaskDataModel> vehicleScheduleTasks;
    /** スケジュールの作成時刻 */
    private String vehicleScheduleCreatedAt;
    /** スケジュールの更新時刻 */
    private String vehicleScheduleUpdatedAt;
}
