package jp.tier4.dataconversion.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 自動運転車両スケジュールタスクデータモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class VehicleScheduleTaskDataModel {

    /** 実行タスクID */
    private String taskId;
    /** スケジュールタイプ */
    private String taskType;
    /** タスクステータス */
    private String status;
    /** 走行開始地点の乗降地(バス停)のデータ */
    private BusStopDataModel origin;
    /** 目的地の乗降地(バス停)のデータ */
    private BusStopDataModel destination;
    /** ルートIDリスト */
    private List<String> routeIds;
    /** タスク予定開始時間 */
    private String planStartTime;
    /** タスク予定終了時間 */
    private String planEndTime;
    /** タスク実績開始時間 */
    private String actualStartTime;
    /** タスク実績終了時間 */
    private String actualEndTime;
    /** タスクの予定開始時間からタスクの予定終了時間の秒数 */
    private Integer durationSec;
    /** 説明 */
    private String description;
}
