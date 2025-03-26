package jp.tier4.dataconversion.domain.model;

import jakarta.annotation.Nonnull;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * APIレスポンスのベースクラス
 *
 * @param <T> 各APIの返却値の型を設定
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class BaseModel<T> {

    /** データモデルタイプ */
    @Nonnull
    private String dataModelType;
    /** データモデル */
    private T attribute;
}
