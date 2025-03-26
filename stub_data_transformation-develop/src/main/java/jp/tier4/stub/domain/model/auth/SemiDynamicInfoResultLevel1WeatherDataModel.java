package jp.tier4.stub.domain.model.auth;

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
public class SemiDynamicInfoResultLevel1WeatherDataModel {

    private String dateTime;
    private String weather;
    private Double temperature;
    private Double windDirection;
    private Double windSpeed;
    private Double humidity;
    private Double rainfall;
    private String updatedAt;
}
