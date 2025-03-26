package jp.tier4.stub.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS API 自動運転車両スケジュール情報取得モデル
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveSchedulelsVehicle {

	/** 合計（スケジュール数） */
	private int total;
	/** スケジュール情報リスト */
	private List<Schedule> schedules;
}
