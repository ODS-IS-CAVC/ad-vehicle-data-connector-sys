package jp.tier4.dataconversion.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS API 走行経路情報取得レスポンスモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrievePointRoute {

    /** ポイント（場所）情報リスト */
    private List<Point> points;
    /** ルートの走行予想時間[s] */
    @JsonProperty("eta_sec")
    private Integer etaSec;
    /** ルートの走行距離[m] */
    @JsonProperty("distance_meters")
    private Integer distanceMeters;
}
