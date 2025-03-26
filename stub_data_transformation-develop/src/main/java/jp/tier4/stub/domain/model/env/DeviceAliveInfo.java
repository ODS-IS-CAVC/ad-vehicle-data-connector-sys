package jp.tier4.stub.domain.model.env;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceAliveInfo {

    private Integer deviceID;
    private Integer deviceOperationStatus;
    private Integer deviceAliveStatus;
}
