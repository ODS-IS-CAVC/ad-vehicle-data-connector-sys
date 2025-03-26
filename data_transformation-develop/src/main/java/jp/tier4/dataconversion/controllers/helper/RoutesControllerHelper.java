package jp.tier4.dataconversion.controllers.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.tier4.dataconversion.constants.Constants;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.Location;
import jp.tier4.dataconversion.domain.model.Point;
import jp.tier4.dataconversion.domain.model.RouteDataModel;
import jp.tier4.dataconversion.domain.model.fms.RetrievePointRoute;

/**
 * 
 * 走行経路報取得コントローラーヘルパー ※serviceとcontrollerの仲立ち
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class RoutesControllerHelper {

    /**
     * 
     * FMS APIから取得した走行経路情報取得モデルをデータ変換システムのレスポンスにマッピングする
     *
     * @param routeId ルートID
     * @param resuponse FMS API 走行経路情報取得モデル
     * @return 走行経路データ取得APIレスポンス
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    public static BaseModel<RouteDataModel> routeMapper(String routeId, RetrievePointRoute resuponse) {
        BaseModel<RouteDataModel> result = new BaseModel<RouteDataModel>();
        RouteDataModel route = new RouteDataModel();
        List<Point> points = new ArrayList<Point>();

        // Placesは必須だがNullチェック
        if (Objects.nonNull(resuponse.getPoints())) {
            // 場所リスト分繰り返す
            for (jp.tier4.dataconversion.domain.model.fms.Point fmsPoint : resuponse.getPoints()) {
                Point point = new Point();
                Location location = new Location();
                // 緯度
                location.setLat(fmsPoint.getLocation().getLat());
                // 経度
                location.setLng(fmsPoint.getLocation().getLng());
                point.setLocation(location);
                points.add(point);
            }
        }
        // ルートID
        route.setRouteId(routeId);
        // 場所リスト
        route.setPoints(points);
        // 走行予想時間
        route.setEtaSec(resuponse.getEtaSec());
        // 走行距離
        route.setDistanceMeters(resuponse.getDistanceMeters());
        // データモデルタイプ
        result.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
        result.setAttribute(route);

        return result;
    }
}
