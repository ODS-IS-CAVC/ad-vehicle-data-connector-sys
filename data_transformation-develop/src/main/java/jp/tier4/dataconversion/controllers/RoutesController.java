package jp.tier4.dataconversion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.tier4.dataconversion.controllers.helper.RoutesControllerHelper;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.RouteDataModel;
import jp.tier4.dataconversion.domain.model.fms.RetrievePointRoute;
import jp.tier4.dataconversion.service.impl.RouteService;

/**
 * 
 * 走行経路コントローラー
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class RoutesController {

    /** ルート情報取得サービス */
    @Autowired
    private RouteService routeService;

    /**
     * 
     * 走行経路データ取得API
     *
     * @param routeId ルートID
     * @return 走行経路データモデル
     * @throws Exception
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @GetMapping("/routes")
    public BaseModel<RouteDataModel> getRoutes(@RequestParam(value = "route_id") String routeId)
            throws Exception {
        BaseModel<RouteDataModel> resuponse = new BaseModel<RouteDataModel>();

        RetrievePointRoute result;
        try {
            // FMS API呼び出し
            result = routeService.service(routeId);
            // FMS APIの結果を変換
            resuponse = RoutesControllerHelper.routeMapper(routeId, result);
            return resuponse;
        } catch (Exception e) {
            throw e;
        }
    }
}
