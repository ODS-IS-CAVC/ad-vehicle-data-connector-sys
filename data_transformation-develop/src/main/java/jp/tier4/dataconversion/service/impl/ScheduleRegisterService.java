package jp.tier4.dataconversion.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.openssl.PEMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.tier4.dataconversion.domain.model.RegisterScheduleVehicleServiceParam;
import jp.tier4.dataconversion.domain.model.fms.RegisterScheduleVehicleParam;
import jp.tier4.dataconversion.domain.model.fms.RegisterScheduleVehicleResult;

/**
 * 
 * 自動運転車両スケジュール登録サービス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class ScheduleRegisterService
        extends FmsServiceBase<RegisterScheduleVehicleResult, RegisterScheduleVehicleServiceParam> {

    /** FMS API:自動運転車両スケジュール登録URL */
    @Value("${fms.api.url.schedule.register}")
    String scheduleRegisterAllUrl;

    /** FMS API:自動運転車両スケジュール登録 エリアマップバージョンID */
    @Value("${fms.api.url.schedule.register.area-map-version-id}")
    String scheduleRegisterAreaMapVersionId;

    @Override
    public RegisterScheduleVehicleResult service(RegisterScheduleVehicleServiceParam param)
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {

        // FMS Authからアクセストークン取得
        String token = getToken();
        // FMS APIを呼び出す前処理
        serviceAdvice();

        RegisterScheduleVehicleParam requestParam = new RegisterScheduleVehicleParam();
        // エリアマップIDの設定
        requestParam.setAreaMapVersionId(scheduleRegisterAreaMapVersionId);
        requestParam.setTasks(param.getTasks());
        String vehicleId = param.getVehicleId();

        RegisterScheduleVehicleResult result = this.fmsClient.post()
                .uri(scheduleRegisterAllUrl, this.fmsProjectId, this.fmsEnvId, vehicleId)
                .body(requestParam)
                .header("Authorization", token).retrieve()
                .body(RegisterScheduleVehicleResult.class);
        return result;
    }
}
