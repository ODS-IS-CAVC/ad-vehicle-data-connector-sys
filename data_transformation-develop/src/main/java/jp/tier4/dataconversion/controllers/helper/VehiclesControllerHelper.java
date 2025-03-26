package jp.tier4.dataconversion.controllers.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.tier4.dataconversion.constants.Constants;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.LocationForVehicle;
import jp.tier4.dataconversion.domain.model.TelemetryDataModel;
import jp.tier4.dataconversion.domain.model.VehicleDataModel;
import jp.tier4.dataconversion.domain.model.VehicleTelemetryModel;
import jp.tier4.dataconversion.domain.model.fms.RetrieveAllVehicles;
import jp.tier4.dataconversion.domain.model.fms.Vehicle;

/**
 * 
 * 車両情報取得コントローラーヘルパー ※serviceとcontrollerの仲立ち
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class VehiclesControllerHelper {

    /**
     * 
     * FMS APIから取得した全自動運転車両情報取得モデルをデータ変換システムのレスポンスにマッピングする
     *
     * @param resuponse FMS API 全自動運転車両情報取得モデル
     * @return 自動運転車両情報全取得APIレスポンス
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    public static BaseModel<List<VehicleDataModel>> vehiclesAllMapper(RetrieveAllVehicles resuponse) {
        BaseModel<List<VehicleDataModel>> result = new BaseModel<List<VehicleDataModel>>();
        List<VehicleDataModel> vehicles = new ArrayList<VehicleDataModel>();

        // 車両情報リストが存在する場合
        if (Objects.nonNull(resuponse.getVehicles())) {
            // 車両情報のマッピングを行う
            for (Vehicle item : resuponse.getVehicles()) {
                VehicleDataModel vehicle = new VehicleDataModel();
                vehicle.setVehicleId(item.getVehicleId());
                vehicle.setVehicleName(item.getVehicleName());
                vehicle.setStatus(item.getTelemetry().getStatus());
                // 車両位置情報
                LocationForVehicle location = new LocationForVehicle();
                location.setLat(item.getTelemetry().getLocation().getLat());
                location.setLng(item.getTelemetry().getLocation().getLng());
                location.setHeight(item.getTelemetry().getLocation().getHeight());
                vehicle.setLocation(location);

                vehicle.setUpdatedAt(item.getTelemetry().getUpdatedAt());
                vehicles.add(vehicle);
            }
        }
        result.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
        result.setAttribute(vehicles);

        return result;
    }

    /**
     * 
     * FMS APIから取得した自動運転車両情報取得モデルをデータ変換システムのレスポンスにマッピングする
     *
     * @param resuponse FMS API 自動運転車両情報取得モデル
     * @return 自動運転車両情報取得APIレスポンス
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    public static BaseModel<VehicleTelemetryModel> vehiclesMapper(Vehicle resuponse) {

        BaseModel<VehicleTelemetryModel> result = new BaseModel<VehicleTelemetryModel>();
        VehicleTelemetryModel vehicle = new VehicleTelemetryModel();

        vehicle.setVehicleId(resuponse.getVehicleId());
        vehicle.setVehicleName(resuponse.getVehicleName());
        TelemetryDataModel telemetry = new TelemetryDataModel();
        telemetry.setStatus(resuponse.getTelemetry().getStatus());
        telemetry.setDriveMode(resuponse.getTelemetry().getDriveMode());
        telemetry.setSpeed(resuponse.getTelemetry().getSpeed());
        telemetry.setBattery(resuponse.getTelemetry().getBattery());
        // 車両位置情報
        LocationForVehicle location = new LocationForVehicle();
        location.setLat(resuponse.getTelemetry().getLocation().getLat());
        location.setLng(resuponse.getTelemetry().getLocation().getLng());
        location.setHeight(resuponse.getTelemetry().getLocation().getHeight());
        telemetry.setLocation(location);

        telemetry.setHeading(resuponse.getTelemetry().getHeading());
        telemetry.setUpdatedAt(resuponse.getTelemetry().getUpdatedAt());
        vehicle.setTelemetry(telemetry);
        result.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
        result.setAttribute(vehicle);

        return result;
    }

}
