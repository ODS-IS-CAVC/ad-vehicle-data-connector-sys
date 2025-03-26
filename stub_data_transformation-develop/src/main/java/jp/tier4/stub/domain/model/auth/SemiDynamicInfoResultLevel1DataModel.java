package jp.tier4.stub.domain.model.auth;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 準動的情報検索リクエストモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SemiDynamicInfoResultLevel1DataModel {

    private String spatialID;
    private List<Double> bbox;
    private SemiDynamicInfoResultLevel1WeatherDataModel weatherInfo;
    /** 未使用 */
    private Object level2;
}
