package jp.tier4.dataconversion.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * テレメトリーデータモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class TelemetryDataModel {

    /** 自動運転車両ステータス */
    private String status;
    /** 走行モード */
    private String driveMode;
    /** 車両速度 */
    private Double speed;
    /** バッテリー[%] */
    private Double battery;
    /** 位置情報 */
    private LocationForVehicle location;
    /** 進行方向（角度） */
    private Double heading;
    /** 更新時間 */
    private String updatedAt;
}
