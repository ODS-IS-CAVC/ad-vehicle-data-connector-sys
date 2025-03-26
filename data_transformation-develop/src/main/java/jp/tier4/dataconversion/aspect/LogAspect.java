package jp.tier4.dataconversion.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Controller/Service AOP コントローラーとサービスの開始終了を引数も出力する
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 
     * サービスの実行時にログを出力 対象:[Service]をクラス名に含んでいる
     *
     * @param jp
     * @return
     * @throws Throwable
     * 
     * @version 0.0.1
     * @since 0.0.1
     */
    @Around("execution(* jp.tier4.dataconversion.service..*Service.service(..))")
    public Object serviceLog(ProceedingJoinPoint jp) throws Throwable {

        // 開始ログの出力
        log.info("サービス開始：" + jp.getSignature() + ",引数：" + getArgStr(jp));

        try {
            //メソッドの実行
            Object result = jp.proceed();

            // メソッドの実行
            log.info("サービス終了：" + jp.getSignature() + ",戻り値：" + result.toString());

            // 実行結果を呼び出し元に返却
            return result;

        } catch (Exception e) {
            // エラーログ出力
            log.error("サービス異常終了：" + jp.getSignature());

            // エラーの再スロー
            throw e;
        }
    }

    /**
     * 
     * コントローラーの実行時にログを出力 対象:[Controller]をクラス名に含んでいる
     *
     * @param jp
     * @return
     * @throws Throwable
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @Around("execution(* jp.tier4.dataconversion.controllers.*Controller.*(..))")
    public Object controllerLog(ProceedingJoinPoint jp) throws Throwable {

        // 開始ログの出力
        log.info("コントローラー開始：" + jp.getSignature() + ",引数：" + getArgStr(jp));

        try {
            //メソッドの実行
            Object result = jp.proceed();

            // メソッドの実行
            log.info("コントローラー終了：" + jp.getSignature() + ",戻り値：" + result.toString());

            // 実行結果を呼び出し元に返却
            return result;

        } catch (Exception e) {
            // エラーログ出力
            log.error("コントローラー異常終了：" + jp.getSignature());

            // エラーの再スロー
            throw e;
        }
    }

    /**
     * 
     * 指定したメソッドの引数の文字列を取得する
     *
     * @param jp 横断的な処理を挿入する場所
     * @return 指定したメソッドの引数
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    private String getArgStr(ProceedingJoinPoint jp) {
        StringBuilder sb = new StringBuilder();
        Object[] args = jp.getArgs();
        if (args.length > 0) {
            for (Object arg : args) {
                sb.append(arg + ", ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
        } else {
            sb.append("(なし)");
        }
        return sb.toString();
    }
}