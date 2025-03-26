package jp.tier4.stub.domain.model.fms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * FMS API 自動運転車両スケジュール登録レスポンスモデル
 * 
 * RetrieveScheduleと構成要素が同じのため継承する。独自の対応が必要な場合は本クラスを修正する
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterScheduleVehicleParam {
	
	/** エリアマップバージョンID */
	@JsonProperty("area_map_version_id")
	private String areaMapVersionId;
	/** スケジュールタイプ */
	@JsonProperty("schedule_type")
	private String scheduleType;
	/** 登録用タスクリスト */
	private List<ScheduleTaskRegist> tasks;
	/** タグリスト */
	@JsonProperty("tags")
	private List<Tag> tags;
}
