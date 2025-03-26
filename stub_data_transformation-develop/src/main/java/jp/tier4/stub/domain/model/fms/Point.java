package jp.tier4.stub.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS API 位置情報モデル
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {

	/** 位置情報 */
	private Location location;
}
