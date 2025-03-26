package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS API 位置情報モデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {

    /** 位置情報 */
    private Location location;
}
