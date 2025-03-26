package jp.tier4.dataconversion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;

import jp.tier4.dataconversion.constants.Constants;
import jp.tier4.dataconversion.domain.model.ErrorBaseModel;

/**
 * 
 * エラーハンドラークラス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@RestControllerAdvice
public class ExceptionHandler {

    /**
     * 
     * 400エラー
     *
     * @param e
     * @return
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler({ ServletRequestBindingException.class, BadRequest.class
    })
    public ErrorBaseModel badRequestHandler(Exception e) {
        // スタックトレースをログ出力
        e.printStackTrace();
        // 返却値を作成
        ErrorBaseModel res = new ErrorBaseModel();
        res.setCode(Constants.CODE_400);
        res.setMessage(Constants.MESSAGE_400);
        return res;
    }

    /**
     * 
     * 401エラー
     *
     * @param e
     * @return
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler({ Unauthorized.class })
    public ErrorBaseModel unAuthorizedHandler(Exception e) {
        // スタックトレースをログ出力
        e.printStackTrace();
        // 返却値を作成
        ErrorBaseModel res = new ErrorBaseModel();
        res.setCode(Constants.CODE_401);
        res.setMessage(Constants.MESSAGE_401);
        return res;
    }

    /**
     * 
     * 503エラー
     *
     * @param e
     * @return
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @org.springframework.web.bind.annotation.ExceptionHandler({ ServiceUnavailable.class })
    public ErrorBaseModel serviceUnavailableHandler(Exception e) {
        // スタックトレースをログ出力
        e.printStackTrace();
        // 返却値を作成
        ErrorBaseModel res = new ErrorBaseModel();
        res.setCode(Constants.CODE_503);
        res.setMessage(Constants.MESSAGE_503);
        return res;
    }

    /**
     * 
     * 500エラー
     *
     * @param e
     * @return
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
    public ErrorBaseModel internalServerErrorHandler(Exception e) {
        // スタックトレースをログ出力
        e.printStackTrace();
        // 返却値を作成
        ErrorBaseModel res = new ErrorBaseModel();
        res.setCode(Constants.CODE_500);
        res.setMessage(Constants.MESSAGE_500);
        return res;
    }
}
