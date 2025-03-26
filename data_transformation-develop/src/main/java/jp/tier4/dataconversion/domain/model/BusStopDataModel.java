package jp.tier4.dataconversion.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 乗降地（バス停）データモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class BusStopDataModel {

    /** バス停ID */
    private int busStopId;
    /** バス停名 */
    private String busStopName;
    /** 車両位置情報 */
    private Location location;
}
