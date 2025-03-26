package jp.tier4.stub.domain.model.auth;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 準動的情報検索リクエストベースモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SemiDynamicInfoBase {

    /** 要求対象のデータの日時 */
    private String targetTime;
    /** 検索するデータのリスト */
    private List<SemiDynamicInfoParam> requestInfo;
}
