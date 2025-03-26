package jp.tier4.dataconversion.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 走行経路用位置情報データモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class Point {
    /** 位置情報 */
    private Location location;
}
