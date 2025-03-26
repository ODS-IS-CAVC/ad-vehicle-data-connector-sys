package jp.tier4.dataconversion.controllers.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.tier4.dataconversion.constants.Constants;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.BusStopDataModel;
import jp.tier4.dataconversion.domain.model.Location;
import jp.tier4.dataconversion.domain.model.RegisterScheduleVehicleServiceParam;
import jp.tier4.dataconversion.domain.model.ScheduleGet;
import jp.tier4.dataconversion.domain.model.SchedulePutDataModel;
import jp.tier4.dataconversion.domain.model.VehicleScheduleDataModel;
import jp.tier4.dataconversion.domain.model.VehicleScheduleTaskDataModel;
import jp.tier4.dataconversion.domain.model.fms.Schedule;
import jp.tier4.dataconversion.domain.model.fms.ScheduleTask;
import jp.tier4.dataconversion.domain.model.fms.ScheduleTaskRegist;

/**
 * 
 * スケジュール情報取得コントローラーヘルパー ※serviceとcontrollerの仲立ち
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class ScheduleControllerHelper {

    /**
     * 
     * FMS APIから取得した全場所情報取得モデルをデータ変換システムのレスポンスにマッピングする
     *
     * @param resuponse FMS API 自動運転車両スケジュール情報取得モデル/スケジュール情報取得モデル
     * @return 乗降地（バス停）モデル情報全取得APIレスポンス
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    public static BaseModel<VehicleScheduleDataModel> vehicleScheduleMapper(ScheduleGet resuponse) {

        BaseModel<VehicleScheduleDataModel> result = new BaseModel<VehicleScheduleDataModel>();
        // 車両スケジュール
        VehicleScheduleDataModel vehicleSchedule = new VehicleScheduleDataModel();

        // 車両スケジュール詰め替え
        Schedule schedule = resuponse.getVehicleSchedule().getSchedules().get(0);
        vehicleSchedule.setVehicleScheduleId(schedule.getScheduleId());
        vehicleSchedule.setVehicleId(schedule.getVehicleId());
        vehicleSchedule.setVehicleScheduleStatus(schedule.getStatus());
        vehicleSchedule.setVehicleSchedulePlanStartTime(schedule.getPlanStartTime());
        vehicleSchedule.setVehicleSchedulePlanEndTime(schedule.getPlanEndTime());
        vehicleSchedule.setVehicleScheduleActualStartTime(schedule.getActualStartTime());
        vehicleSchedule.setVehicleScheduleActualEndTime(schedule.getActualEndTime());
        vehicleSchedule.setVehicleScheduleDurationSec(schedule.getDurationSec());
        vehicleSchedule.setVehicleScheduleDeltaSec(schedule.getDeltaSec());

        List<VehicleScheduleTaskDataModel> vehicleScheduleTaskDataModels = new ArrayList<VehicleScheduleTaskDataModel>();
        // スケジュールタスクリスト分繰り返す
        for (ScheduleTask task : resuponse.getScheduleTask().getTasks()) {
            VehicleScheduleTaskDataModel vehicleScheduleTaskDataModel = new VehicleScheduleTaskDataModel();
            vehicleScheduleTaskDataModel.setTaskId(task.getTaskId());
            vehicleScheduleTaskDataModel.setTaskType(task.getTaskType());
            vehicleScheduleTaskDataModel.setStatus(task.getStatus());

            BusStopDataModel origin = new BusStopDataModel();
            origin.setBusStopId(task.getOrigin().getPointId());
            origin.setBusStopName(task.getOrigin().getName());
            Location originLocation = new Location();
            originLocation.setLat(task.getOrigin().getLocation().getLat());
            originLocation.setLng(task.getOrigin().getLocation().getLng());
            origin.setLocation(originLocation);
            vehicleScheduleTaskDataModel.setOrigin(origin);

            BusStopDataModel destination = new BusStopDataModel();
            destination.setBusStopId(task.getDestination().getPointId());
            destination.setBusStopName(task.getDestination().getName());
            Location destinationLocation = new Location();
            destinationLocation.setLat(task.getDestination().getLocation().getLat());
            destinationLocation.setLng(task.getDestination().getLocation().getLng());
            destination.setLocation(destinationLocation);
            vehicleScheduleTaskDataModel.setDestination(destination);

            vehicleScheduleTaskDataModel.setRouteIds(task.getRouteIds());
            vehicleScheduleTaskDataModel.setPlanStartTime(task.getPlanStartTime());
            vehicleScheduleTaskDataModel.setPlanEndTime(task.getPlanEndTime());
            vehicleScheduleTaskDataModel.setActualStartTime(task.getActualStartTime());
            vehicleScheduleTaskDataModel.setActualEndTime(task.getActualEndTime());
            vehicleScheduleTaskDataModel.setDurationSec(task.getDurationSec());
            vehicleScheduleTaskDataModel.setDescription(task.getDescription());

            vehicleScheduleTaskDataModels.add(vehicleScheduleTaskDataModel);
        }
        vehicleSchedule.setVehicleScheduleTasks(vehicleScheduleTaskDataModels);
        vehicleSchedule.setVehicleScheduleCreatedAt(schedule.getCreatedAt());
        vehicleSchedule.setVehicleScheduleUpdatedAt(schedule.getUpdatedAt());
        // データモデルタイプ
        result.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
        result.setAttribute(vehicleSchedule);

        return result;
    }

    /**
     * 
     * 自動運転車両スケジュール登録APIのリクエストパラメータをFMS API用に変換
     *
     * @param param 自動運転車両スケジュール登録データモデル
     * @return FMS API 自動運転車両スケジュール登録レスポンスモデル
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    public static RegisterScheduleVehicleServiceParam vehicleScheduleRegisterForFMSMapper(SchedulePutDataModel param) {

        RegisterScheduleVehicleServiceParam serviceParam = new RegisterScheduleVehicleServiceParam();
        // 車両ID
        serviceParam.setVehicleId(param.getVehicleId());

        List<ScheduleTaskRegist> tasks = new ArrayList<ScheduleTaskRegist>();
        // Nullチェック
        if (Objects.nonNull(param.getDestinationPoints())) {
            // リスト分繰り返す
            for (Integer point : param.getDestinationPoints()) {
                ScheduleTaskRegist task = new ScheduleTaskRegist();
                // タスクタイプはmove固定
                task.setTaskType(Constants.FMS_REGIST_SCHEDULE_TASK_TYPE_MOVE);
                task.setDestinationPoint(point);
                tasks.add(task);
            }
        }
        serviceParam.setTasks(tasks);
        return serviceParam;
    }
}
