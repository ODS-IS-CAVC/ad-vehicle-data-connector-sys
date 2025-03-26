package jp.tier4.dataconversion.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jp.tier4.dataconversion.domain.model.fms.ScheduleTaskRegist;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS API 自動運転車両スケジュール登録レスポンスモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterScheduleVehicleServiceParam {

    /** 車両ID */
    private String vehicleId;
    /** 登録用タスクリスト */
    private List<ScheduleTaskRegist> tasks;
}
