package jp.tier4.stub.domain.model.fms;

import lombok.Getter;
import lombok.Setter;

/**
 * 車両情報
 */
@Getter
@Setter
public class Vehicle {

	/** プロジェクト識別子 */
	private String project_id;
	/** 環境識別子 */
	private String environment_id;
	/** 車両識別子 */
	private String vehicle_id;
	/** 車両名 */
	private String vehicle_name;
	/** 始動開始可能フラグ */
	private boolean can_start;
	/** 指示受け入れ可能単位 */
	private String acceptable_order;
	/** 説明 */
	private String description;
	/** 作成日時 */
	private String created_at;
	/** 更新日時 */
	private String updated_at;
	/** テレメトリー */
	private Telemetry telemetry;

}
