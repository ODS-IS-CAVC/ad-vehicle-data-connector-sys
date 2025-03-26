package jp.tier4.stub.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * APIレスポンスのベースクラス
 * @param <T> 各APIの返却値の型を設定
 */
@Getter
@Setter
public class BaseModel<T> {

	/** データモデルタイプ */
	private String dataModelType;
	/** データモデル */
	private T attribute;
}
