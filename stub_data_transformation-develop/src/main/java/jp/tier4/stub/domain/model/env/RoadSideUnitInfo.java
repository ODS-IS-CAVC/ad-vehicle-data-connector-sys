package jp.tier4.stub.domain.model.env;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoadSideUnitInfo {

    private Integer serviceLocationID;
    private Integer roadsideUnitID;
    private String updateTimeInfo;
    private Integer formatVersion;
    private String roadsideUnitName;
    private String productNumber;
    private String manufacturer;
    private String customer;
    private String licensingInfo;
    private String initialRegistrationDate;
    private Integer powerConsumption;
    private Integer grossWeight;
    private Integer materialType;
    private String dateOfInstallation;
    private Integer latitude;
    private Integer longitude;
    private String roadsideUnitManager;
    private String installationSiteManager;
    private String lastInspectionDate;
    private String nextInspectionDate;

}
