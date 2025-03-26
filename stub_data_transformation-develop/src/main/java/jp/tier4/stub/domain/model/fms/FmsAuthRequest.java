package jp.tier4.stub.domain.model.fms;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS Auth リクエスト
 */
@Getter
@Setter
public class FmsAuthRequest {

	private String grant_type;
	private String scope;
	private String assertion;
	private String refresh_token;
	private String client_id;
	private String client_secret;
	private String client_assertion;
	private String client_assertion_type;
	private String subject_token;
	private String subject_token_type;
}
