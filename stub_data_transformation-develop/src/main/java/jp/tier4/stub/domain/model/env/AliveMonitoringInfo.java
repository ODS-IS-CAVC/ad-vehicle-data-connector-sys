package jp.tier4.stub.domain.model.env;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliveMonitoringInfo {

    private Integer serviceLocationID;
    private Integer roadsideUnitID;
    private String updateTimeInfo;
    private Integer formatVersion;
    private Integer operationClassificationCode;
    private Integer serviceAvailability;
    private Integer deviceClassificationNum;
    private List<DeviceClassificationAliveInfo> deviceClassificationAliveInfo;
    private List<DeviceAliveInfo> deviceAliveInfo;
}
