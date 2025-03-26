package jp.tier4.dataconversion.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.openssl.PEMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.tier4.dataconversion.domain.model.fms.RetrieveAllPlaces;

/**
 * 
 * 乗降地（バス停）の情報取得サービス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class BusStopService extends FmsServiceBase<RetrieveAllPlaces, String> {

    /** FMS API:全場所の情報取得URL */
    @Value("${fms.api.url.place.all}")
    String placeAllUrl;

    @Override
    public RetrieveAllPlaces service(String noUseParam)
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {
        // FMS Authからアクセストークン取得
        String token = getToken();
        // FMS APIを呼び出す前処理
        serviceAdvice();
        // FMS APIを呼び出す
        RetrieveAllPlaces result = this.fmsClient.get().uri(placeAllUrl, this.fmsProjectId, this.fmsEnvId)
                .header("Authorization", token).retrieve()
                .body(RetrieveAllPlaces.class);
        return result;
    }
}
