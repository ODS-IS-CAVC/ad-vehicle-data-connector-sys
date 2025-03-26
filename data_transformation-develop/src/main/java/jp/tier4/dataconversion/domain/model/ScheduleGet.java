package jp.tier4.dataconversion.domain.model;

import jp.tier4.dataconversion.domain.model.fms.RetrieveSchedule;
import jp.tier4.dataconversion.domain.model.fms.RetrieveSchedulelsVehicle;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS API 自動運転車両スケジュール情報取得モデル/スケジュール情報取得モデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class ScheduleGet {

    /** FMS API 自動運転車両スケジュール情報取得モデル */
    private RetrieveSchedulelsVehicle vehicleSchedule;
    /** FMS API スケジュール情報取得モデル */
    private RetrieveSchedule scheduleTask;
}