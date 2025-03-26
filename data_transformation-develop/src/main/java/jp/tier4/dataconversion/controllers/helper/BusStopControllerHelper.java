package jp.tier4.dataconversion.controllers.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.tier4.dataconversion.constants.Constants;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.BusStopDataModel;
import jp.tier4.dataconversion.domain.model.Location;
import jp.tier4.dataconversion.domain.model.fms.Place;
import jp.tier4.dataconversion.domain.model.fms.RetrieveAllPlaces;

/**
 * 
 * 乗降地（バス停）情報取得コントローラーヘルパー ※serviceとcontrollerの仲立ち
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class BusStopControllerHelper {

    /**
     * 
     * FMS APIから取得した全場所情報取得モデルをデータ変換システムのレスポンスにマッピングする
     *
     * @param resuponse FMS API 全場所情報取得モデル
     * @return 乗降地（バス停）モデル情報全取得APIレスポンス
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    public static BaseModel<List<BusStopDataModel>> busStopMapper(RetrieveAllPlaces resuponse) {

        BaseModel<List<BusStopDataModel>> result = new BaseModel<List<BusStopDataModel>>();
        List<BusStopDataModel> busStops = new ArrayList<BusStopDataModel>();

        // Placesは必須だがNullチェック
        if (Objects.nonNull(resuponse.getPlaces())) {
            // 場所リスト分繰り返す
            for (Place place : resuponse.getPlaces()) {
                BusStopDataModel busStop = new BusStopDataModel();
                // 場所ID
                busStop.setBusStopId(place.getPointId());
                // 場所名
                busStop.setBusStopName(place.getName());
                // 位置情報
                Location location = new Location();
                // 緯度
                location.setLat(place.getLocation().getLat());
                // 経度
                location.setLng(place.getLocation().getLng());
                busStop.setLocation(location);
                busStops.add(busStop);
            }
        }
        // データモデルタイプ
        result.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
        result.setAttribute(busStops);

        return result;
    }

}
