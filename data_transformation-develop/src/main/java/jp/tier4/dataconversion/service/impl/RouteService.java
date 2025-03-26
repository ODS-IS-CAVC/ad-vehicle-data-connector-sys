package jp.tier4.dataconversion.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.openssl.PEMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.tier4.dataconversion.domain.model.fms.RetrievePointRoute;

/**
 * 
 * ルート情報取得サービス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class RouteService extends FmsServiceBase<RetrievePointRoute, String> {

    /** FMS API:走行経路状オフ取得URL */
    @Value("${fms.api.url.route}")
    String routeUrl;

    @Override
    public RetrievePointRoute service(String routeId)
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {

        // FMS Authからアクセストークン取得
        String token = getToken();
        // FMS APIを呼び出す前処理
        serviceAdvice();
        // FMS APIを呼び出す
        RetrievePointRoute result = this.fmsClient.get()
                .uri(routeUrl, this.fmsProjectId, this.fmsEnvId, routeId)
                .header("Authorization", token).retrieve()
                .body(RetrievePointRoute.class);
        return result;
    }

}
