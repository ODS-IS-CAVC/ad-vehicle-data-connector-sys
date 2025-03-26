package jp.tier4.stub.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS API 全自動運転車両情報取得モデル
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveAllVehicles {

	/** 合計（車両数） */
	private int total;
	/** 車両情報リスト */
	private List<Vehicle> vehicles;
}
