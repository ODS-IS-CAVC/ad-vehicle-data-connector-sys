package jp.tier4.dataconversion.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.tier4.dataconversion.controllers.helper.BusStopControllerHelper;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.BusStopDataModel;
import jp.tier4.dataconversion.domain.model.fms.RetrieveAllPlaces;
import jp.tier4.dataconversion.service.impl.BusStopService;

/**
 * 
 * 乗降地（バス停）モデル情報取得コントローラー
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class BusStopController {

    /** 乗降地（バス停）の情報取得サービス */
    @Autowired
    private BusStopService busStopService;

    /**
     * 
     * 乗降地（バス停）モデル情報全取得API
     *
     * @return 乗降地（バス停）データモデル
     * @throws Exception
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @GetMapping("/bus-stops")
    public BaseModel<List<BusStopDataModel>> busStop() throws Exception {
        BaseModel<List<BusStopDataModel>> resuponse = new BaseModel<List<BusStopDataModel>>();

        RetrieveAllPlaces result;
        try {
            // FMS API呼び出し
            result = busStopService.service("");
            // FMS APIの結果を変換
            resuponse = BusStopControllerHelper.busStopMapper(result);
            return resuponse;
        } catch (Exception e) {
            throw e;
        }
    }

}
