package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS Auth レスポンスデータモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class FmsAuthResponse {

    /** アクセストークン */
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    private String scope;
}
