package jp.tier4.dataconversion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.tier4.dataconversion.controllers.helper.ScheduleControllerHelper;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.RegisterScheduleVehicleServiceParam;
import jp.tier4.dataconversion.domain.model.ScheduleGet;
import jp.tier4.dataconversion.domain.model.SchedulePutDataModel;
import jp.tier4.dataconversion.domain.model.VehicleScheduleDataModel;
import jp.tier4.dataconversion.service.impl.ScheduleGetService;
import jp.tier4.dataconversion.service.impl.ScheduleRegisterService;

/**
 * 
 * スケジュールコントローラー
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class ScheduleController {

    /** スケジュール取得サービス */
    @Autowired
    private ScheduleGetService scheduleGetService;

    /** スケジュール登録サービス */
    @Autowired
    private ScheduleRegisterService scheduleRegisterService;

    /**
     * 
     * 自動運転車両スケジュール取得API
     *
     * @param vehicleId 車両ID
     * @return 自動運転車両スケジュールデータモデル
     * @throws Exception
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @GetMapping("/vehicle-schedules")
    public BaseModel<VehicleScheduleDataModel> getSchedule(@RequestParam(value = "vehicle_id") String vehicleId)
            throws Exception {
        BaseModel<VehicleScheduleDataModel> resuponse = new BaseModel<VehicleScheduleDataModel>();

        ScheduleGet result;
        try {
            // FMS API呼び出し
            result = scheduleGetService.service(vehicleId);
            // FMS APIの結果を変換
            resuponse = ScheduleControllerHelper.vehicleScheduleMapper(result);
            return resuponse;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 
     * 自動運転車両スケジュール登録API
     *
     * @param scheduleModel 自動運転車両スケジュール登録データモデル
     * @throws Exception
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @PostMapping("/vehicle-schedule")
    public ResponseEntity<Void> putSchedule(@RequestBody @Validated BaseModel<SchedulePutDataModel> scheduleModel)
            throws Exception {

        // リクエストパラメータをFMS API呼び出し用に変換
        RegisterScheduleVehicleServiceParam serviceParam = ScheduleControllerHelper
                .vehicleScheduleRegisterForFMSMapper(scheduleModel.getAttribute());

        // FMS API呼び出し
        scheduleRegisterService.service(serviceParam);
        // HTTPステータス：201（Created）を返却する
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
