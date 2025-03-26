package jp.tier4.dataconversion.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 車両データモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class VehicleDataModel {

    /** 車両ID */
    private String vehicleId;
    /** 車両名 */
    private String vehicleName;
    /** ステータス */
    private String status;
    /** 車両位置情報 */
    private LocationForVehicle location;
    /** 更新時間 */
    private String updatedAt;
}
