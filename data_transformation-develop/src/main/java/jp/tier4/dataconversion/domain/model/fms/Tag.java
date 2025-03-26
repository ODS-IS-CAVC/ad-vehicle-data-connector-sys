package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS タグ情報
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class Tag {

    /** キー */
    @JsonProperty("key")
    private String key;
    /** 値 */
    @JsonProperty("value")
    private String value;
}
