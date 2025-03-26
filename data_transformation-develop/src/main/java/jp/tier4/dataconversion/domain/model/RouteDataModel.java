package jp.tier4.dataconversion.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 走行経路データモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class RouteDataModel {

    /** ルートID */
    private String routeId;
    /** ポイントリスト */
    private List<Point> points;
    /** ルートの走行予想時間[s] */
    private Integer etaSec;
    /** ルートの走行距離[m] */
    private Integer distanceMeters;
}
