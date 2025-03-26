package jp.tier4.dataconversion.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.tier4.dataconversion.controllers.helper.VehiclesControllerHelper;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.VehicleDataModel;
import jp.tier4.dataconversion.domain.model.VehicleTelemetryModel;
import jp.tier4.dataconversion.domain.model.fms.RetrieveAllVehicles;
import jp.tier4.dataconversion.domain.model.fms.Vehicle;
import jp.tier4.dataconversion.service.impl.VehicleAllService;
import jp.tier4.dataconversion.service.impl.VehicleTelemetryService;

/**
 * 
 * 自動運転車両コントローラー
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class VehiclesController {

    /** 自動運転車両情報全取得サービス */
    @Autowired
    private VehicleAllService vehicleAllService;

    /** 自動運転車両情報取得サービス */
    @Autowired
    private VehicleTelemetryService vehicleTelemetryService;

    /**
     * 
     * 自動運転車両情報全取得API
     *
     * @return 自動運転車両データモデルリスト
     * @throws Exception
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @GetMapping("/vehicles")
    public BaseModel<List<VehicleDataModel>> vehicles() throws Exception {
        BaseModel<List<VehicleDataModel>> resuponse = new BaseModel<List<VehicleDataModel>>();

        RetrieveAllVehicles result;
        try {
            // FMS API呼び出し
            result = vehicleAllService.service("");
            // FMS APIの結果を変換
            resuponse = VehiclesControllerHelper.vehiclesAllMapper(result);
        } catch (Exception e) {
            throw e;
        }
        return resuponse;
    }

    /**
     * 
     * 自動運転車両情報取得API
     *
     * @param vehicleId 車両ID
     * @return 車両テレメトリーデータモデル
     * @throws Exception
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @GetMapping("/vehicle-telemetry")
    public BaseModel<VehicleTelemetryModel> vehicle(@RequestParam(value = "vehicle_id") String vehicleId)
            throws Exception {
        BaseModel<VehicleTelemetryModel> resuponse = new BaseModel<VehicleTelemetryModel>();
        Vehicle result;
        try {
            // FMS API呼び出し
            result = vehicleTelemetryService.service(vehicleId);
            // FMS APIの結果を変換
            resuponse = VehiclesControllerHelper.vehiclesMapper(result);
            return resuponse;
        } catch (Exception e) {
            throw e;
        }
    }
}
