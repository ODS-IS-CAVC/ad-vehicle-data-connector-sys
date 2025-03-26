package jp.tier4.dataconversion.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.openssl.PEMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.tier4.dataconversion.domain.model.fms.RetrieveAllVehicles;

/**
 * 
 * 自動運転車両情報全取得サービス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class VehicleAllService extends FmsServiceBase<RetrieveAllVehicles, String> {

    /** FMS API:自動運転車両情報全取得URL */
    @Value("${fms.api.url.vehiclel.all}")
    String vehicleAllUrl;

    @Override
    public RetrieveAllVehicles service(String noUseParam)
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {

        // FMS Authからアクセストークン取得
        String token = getToken();

        // FMS APIを呼び出す前処理
        serviceAdvice();
        RetrieveAllVehicles result = this.fmsClient.get().uri(vehicleAllUrl, this.fmsProjectId, this.fmsEnvId)
                .header("Authorization", token).retrieve()
                .body(RetrieveAllVehicles.class);
        return result;
    }
}
