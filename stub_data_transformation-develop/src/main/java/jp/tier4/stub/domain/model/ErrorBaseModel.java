package jp.tier4.stub.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * APIレスポンスのエラー時のベースクラス
 */
@Getter
@Setter
public class ErrorBaseModel {

	/** コード */
	private String code;
	/** メッセージ */
	private String message;
}
