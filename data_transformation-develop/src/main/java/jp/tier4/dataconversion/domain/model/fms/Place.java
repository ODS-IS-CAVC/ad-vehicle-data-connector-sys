package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS 場所情報
 *
 *
 * @version 0.0.1
 * @since 0.0.1
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
