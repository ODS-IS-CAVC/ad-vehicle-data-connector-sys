package jp.tier4.dataconversion.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS API 全場所情報取得モデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveAllPlaces {

    /** エリアマップバージョンID */
    @JsonProperty("area_map_version_id")
    private String areaMapVersionId;
    /** 場所リスト */
    private List<Place> places;
}
