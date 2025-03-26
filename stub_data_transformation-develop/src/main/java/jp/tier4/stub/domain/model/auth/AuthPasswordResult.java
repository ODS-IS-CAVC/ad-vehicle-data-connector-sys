package jp.tier4.stub.domain.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ユーザ当人認証レスポンスモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthPasswordResult {

    /** アクセストークン */
    private String accessToken;
    /** リフレッシュトークン */
    private String refreshToken;
}
