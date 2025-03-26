package jp.tier4.dataconversion.service;

/**
 * 
 * サービスベース
 *
 * @param <R> 結果
 * @param <P> パラメータ
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public interface ServiceBase<R, P> {

    /** 検証用 */
    R service(P p) throws Exception;
}
