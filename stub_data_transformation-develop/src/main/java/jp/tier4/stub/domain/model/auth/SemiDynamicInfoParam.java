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
public class SemiDynamicInfoParam {

    /** ターゲットデータ */
    private Integer targetData;
    /** 要求形式 */
    private Integer requestFormat;
    /** 要求対象範囲 */
    private SemiDynamicInfoRequestArea requestArea;
}
