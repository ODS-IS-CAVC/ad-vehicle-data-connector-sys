package jp.tier4.stub.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS 場所
 */
@Getter
@Setter
public class Place {

	/** 場所ID */
	@JsonProperty("point_id")
	private int pointId;
	/** 場所名 */
	private String name;
	/** 位置情報 */
	private Location location;
}
