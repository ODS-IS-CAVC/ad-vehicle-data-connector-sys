package jp.tier4.dataconversion.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.openssl.PEMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.tier4.dataconversion.domain.model.fms.Vehicle;

/**
 * 
 * 自動運転車両情報取得サービス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class VehicleTelemetryService extends FmsServiceBase<Vehicle, String> {

    /** FMS API:自動運転車両情報取得URL */
    @Value("${fms.api.url.vehiclel.telemetry}")
    String vehicleTelemetryUrl;

    @Override
    public Vehicle service(String vehicleId)
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {

        // FMS Authからアクセストークン取得
        String token = getToken();
        // FMS APIを呼び出す前処理
        serviceAdvice();
        // FMS APIを呼び出す
        Vehicle result = this.fmsClient.get()
                .uri(vehicleTelemetryUrl, this.fmsProjectId, this.fmsEnvId, vehicleId)
                .header("Authorization", token).retrieve()
                .body(Vehicle.class);
        return result;
    }

}
