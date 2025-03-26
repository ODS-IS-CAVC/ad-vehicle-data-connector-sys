package jp.tier4.stub.domain.model.env;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetInfo {

    private Integer serviceLocationID;
    private Integer roadsideUnitID;
    private String updateTimeInfo;
    private Integer formatVersion;
    private String deviceNum;
    private List<DeviceIndividualInfo> deviceIndividualInfo;
}
