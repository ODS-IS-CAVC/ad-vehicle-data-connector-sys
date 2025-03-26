package jp.tier4.stub.domain.model.fms;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS 位置情報オブジェクト
 */
@Getter
@Setter
public class Location {

	/** 緯度 */
	private double lat;
	/** 軽度 */
	private double lng;
	/** 高度 */
	private double height;
}
