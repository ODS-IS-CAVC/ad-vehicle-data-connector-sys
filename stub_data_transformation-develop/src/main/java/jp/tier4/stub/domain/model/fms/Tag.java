package jp.tier4.stub.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * タグ情報
 */
@Getter
@Setter
public class Tag {

	/**  */
	@JsonProperty("key")
	private String key;
	/**  */
	@JsonProperty("value")
	private String value;
}
