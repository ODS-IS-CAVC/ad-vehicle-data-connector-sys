package jp.tier4.dataconversion.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 車両テレメトリーデータモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class VehicleTelemetryModel {

    /** 車両ID */
    private String vehicleId;
    /** 車両名 */
    private String vehicleName;
    /** テレメトリーデータ */
    private TelemetryDataModel telemetry;
}
