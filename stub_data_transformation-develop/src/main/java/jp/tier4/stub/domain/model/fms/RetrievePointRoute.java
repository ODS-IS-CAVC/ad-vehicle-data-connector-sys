package jp.tier4.stub.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS API 走行経路情報取得レスポンスモデル
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrievePointRoute {

	/** ポイント（場所）情報リスト */
	private List<Point> points;
	/** ルートの走行予想時間[s] */
	private Integer eta_sec;
	/** ルートの走行距離[m] */
	private Integer distance_meters;
}
