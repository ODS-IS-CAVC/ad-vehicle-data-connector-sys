package jp.tier4.dataconversion.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 位置情報オブジェクト（車両用）
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class LocationForVehicle extends Location {

    /** 高度 */
    private double height;
}
