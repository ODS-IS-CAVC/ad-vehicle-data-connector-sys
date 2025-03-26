package jp.tier4.dataconversion.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 自動運転車両スケジュール登録データモデル
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
public class SchedulePutDataModel {

    /** 車両ID */
    private String vehicleId;
    /** 目的地のバス停IDリスト */
    private List<Integer> destinationPoints;
}
