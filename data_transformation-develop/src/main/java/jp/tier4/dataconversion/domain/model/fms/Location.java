package jp.tier4.dataconversion.domain.model.fms;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS 位置情報オブジェクト
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class Location {

	/** 緯度 */
	private double lat;
	/** 経度 */
	private double lng;
	/** 高度 */
	private double height;
}
