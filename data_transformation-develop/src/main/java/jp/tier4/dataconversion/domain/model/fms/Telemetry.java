package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS テレメトリー
 *
 *
 * @version 0.0.1
 * @since 0.0.1
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
    /** 更新時間 */
    @JsonProperty("updated_at")
    private String updatedAt;
}
