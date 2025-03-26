package jp.tier4.dataconversion.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 位置情報オブジェクト
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
}
