package jp.tier4.dataconversion.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.openssl.PEMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import jp.tier4.dataconversion.domain.model.ScheduleGet;
import jp.tier4.dataconversion.domain.model.fms.RetrieveSchedule;
import jp.tier4.dataconversion.domain.model.fms.RetrieveSchedulelsVehicle;

/**
 * 
 * スケジュール情報取得サービス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class ScheduleGetService extends FmsServiceBase<ScheduleGet, String> {

    /** FMS API:自動運転車両スケジュール情報取得URL */
    @Value("${fms.api.url.schedule.vehicle}")
    String vehicleScheduleUrl;

    /** FMS API:スケジュール情報取得URL */
    @Value("${fms.api.url.schedule}")
    String scheduleUrl;

    @Override
    public ScheduleGet service(String vehicleId)
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {

        ScheduleGet result = new ScheduleGet();
        RetrieveSchedule scheduleTask = null;
        // FMS Authからアクセストークン取得
        String token = getToken();
        // 車両スケジュール情報取得
        RetrieveSchedulelsVehicle vehicleSchedule = getVehicleSchedule(vehicleId, token);
        // 先頭のスケジュールID
        String scheduleId = vehicleSchedule.getSchedules().get(0).getScheduleId();
        try {
            // 最初のスケジュールIDに対してスケジュール詳細情報取得
            scheduleTask = getSchedule(scheduleId, token);
        } catch (Unauthorized e) {
            // 認証エラーの場合トークン発行からリトライ
            scheduleTask = getScheduleRetry(scheduleId);
        }
        result.setVehicleSchedule(vehicleSchedule);
        result.setScheduleTask(scheduleTask);
        return result;
    }

    /**
     * 
     * 自動運転車両スケジュール情報取得
     *
     * @param vehicleId 車両ID
     * @param token FMSークン
     * @return 自動運転車両スケジュール情報取得モデル
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    private RetrieveSchedulelsVehicle getVehicleSchedule(String vehicleId, String token) {

        // FMS APIを呼び出す前処理
        serviceAdvice();
        // FMS APIを呼び出す
        RetrieveSchedulelsVehicle result = this.fmsClient.get()
                .uri(vehicleScheduleUrl, this.fmsProjectId, this.fmsEnvId, vehicleId)
                .header("Authorization", token).retrieve()
                .body(RetrieveSchedulelsVehicle.class);
        return result;
    }

    /**
     * 
     * スケジュール情報取得（リトライ用）
     *
     * @param scheduleId スケジュールID
     * @return スケジュール情報取得モデル
     * @throws NoSuchAlgorithmException
     * @throws IllegalArgumentException
     * @throws InvalidKeySpecException
     * @throws PEMException
     * @throws IOException
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    private RetrieveSchedule getScheduleRetry(String scheduleId)
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {
        // FMS Authからアクセストークン取得
        String token = getToken();
        return getSchedule(scheduleId, token);
    }

    /**
     * 
     * スケジュール情報取得
     *
     * @param scheduleId スケジュールID
     * @param token FMSトークン
     * @return スケジュール情報取得モデル
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    private RetrieveSchedule getSchedule(String scheduleId, String token) {

        // FMS APIを呼び出す前処理
        serviceAdvice();
        // FMS APIを呼び出す
        RetrieveSchedule result = this.fmsClient.get()
                .uri(scheduleUrl, this.fmsProjectId, this.fmsEnvId, scheduleId)
                .header("Authorization", token).retrieve()
                .body(RetrieveSchedule.class);
        return result;
    }
}
