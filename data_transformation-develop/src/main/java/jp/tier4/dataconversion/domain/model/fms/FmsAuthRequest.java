package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS Auth リクエスト用データモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class FmsAuthRequest {

    @JsonProperty("grant_type")
    private String grantType;

    private String scope;

    private String assertion;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("client_assertion")
    private String clientAssertion;

    @JsonProperty("client_assertion_type")
    private String clientAssertionType;

    @JsonProperty("subject_token")
    private String subjectToken;

    @JsonProperty("subject_token_type")
    private String subjectTokenType;
}
