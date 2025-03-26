package jp.tier4.dataconversion.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * APIレスポンスのエラー時のベースクラス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class ErrorBaseModel {

    /** コード */
    private String code;
    /** メッセージ */
    private String message;
}
