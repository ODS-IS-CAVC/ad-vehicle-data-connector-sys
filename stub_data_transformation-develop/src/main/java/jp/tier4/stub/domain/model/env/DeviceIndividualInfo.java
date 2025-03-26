package jp.tier4.stub.domain.model.env;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceIndividualInfo {

    private List<TargetIndividualInfo> targetIndividualInfo;
}
