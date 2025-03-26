package jp.tier4.dataconversion.domain.model.fms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FMS 車両情報
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {

    /** プロジェクトID */
    @JsonProperty("project_id")
    private String projectId;
    /** 環境ID */
    @JsonProperty("environment_id")
    private String environmentId;
    /** 車両ID */
    @JsonProperty("vehicle_id")
    private String vehicleId;
    /** 車両名 */
    @JsonProperty("vehicle_name")
    private String vehicleName;
    /** 始動開始可能フラグ */
    @JsonProperty("can_start")
    private boolean canStart;
    /** 指示受け入れ可能単位 */
    @JsonProperty("acceptable_order")
    private String acceptableOrder;
    /** 説明 */
    private String description;
    /** 作成日時 */
    @JsonProperty("created_at")
    private String createdAt;
    /** 更新日時 */
    @JsonProperty("updated_at")
    private String updatedAt;
    /** テレメトリー */
    private Telemetry telemetry;

}
