package jp.tier4.stub.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS テレメトリー
 */
@Getter
@Setter
public class Telemetry {

	/** 自動運転車両ステータス */
	private String status;
	/** 走行モード */
	@JsonProperty("drive_mode")
	private String driveMode;
	/** 車両速度 */
	private Double speed;
	/** バッテリー[%] */
	private Double battery;
	/** 位置情報 */
	private Location location;
	/** 進行方向（角度） */
	private Double heading;
	/** 加速度：未使用  */
	private Accelerations accel;
	/** 更新時間 */
	@JsonProperty("updated_at")
	private String updatedAt;
}
