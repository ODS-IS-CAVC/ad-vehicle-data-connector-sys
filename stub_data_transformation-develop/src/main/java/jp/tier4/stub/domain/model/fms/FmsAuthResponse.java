package jp.tier4.stub.domain.model.fms;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS Auth レスポンス
 */
@Getter
@Setter
public class FmsAuthResponse {

	private String access_token;
	private String refresh_token;
	private String token_type;
	private int expires_in;
	private String scope;
}
