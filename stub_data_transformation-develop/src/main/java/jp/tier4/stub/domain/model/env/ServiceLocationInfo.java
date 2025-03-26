package jp.tier4.stub.domain.model.env;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceLocationInfo {

	private String updateTimeInfo;
	private Integer formatVersion;
	private Integer serviceLocationID;
	private Integer latitude;
	private Integer longitude;
	private Integer elevation;
	private Integer approachAttributeSize;
	private List<ApproachAttributeInfo> approachAttributeInfo;
    private List<RoadsideUnit> roadsideUnitList;
}
