package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS API 自動運転車両スケジュール登録レスポンスモデル
 * 
 * RetrieveScheduleと構成要素が同じのため継承する。独自の対応が必要な場合は本クラスを修正する
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterScheduleVehicleResult extends RetrieveSchedule {

}
