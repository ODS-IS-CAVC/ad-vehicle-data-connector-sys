package jp.tier4.stub.controllers;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.tier4.stub.domain.model.BaseModel;
import jp.tier4.stub.domain.model.auth.AuthClientParam;
import jp.tier4.stub.domain.model.auth.AuthPasswordParam;
import jp.tier4.stub.domain.model.auth.SemiDynamicInfoBase;
import jp.tier4.stub.domain.model.auth.SemiDynamicInfoResult;
import jp.tier4.stub.domain.model.auth.SemiDynamicInfoResultDataModel;
import jp.tier4.stub.domain.model.auth.SemiDynamicInfoResultLevel1DataModel;
import jp.tier4.stub.domain.model.auth.SemiDynamicInfoResultLevel1WeatherDataModel;
import jp.tier4.stub.domain.model.env.AliveMonitoringInfo;
import jp.tier4.stub.domain.model.env.DeviceIndividualInfo;
import jp.tier4.stub.domain.model.env.RoadSideUnitInfo;
import jp.tier4.stub.domain.model.env.RoadsideUnit;
import jp.tier4.stub.domain.model.env.ServiceLocationInfo;
import jp.tier4.stub.domain.model.env.TargetIndividualInfo;
import jp.tier4.stub.domain.model.env.TargetInfo;
import jp.tier4.stub.domain.model.fms.FmsAuthResponse;
import jp.tier4.stub.domain.model.fms.Location;
import jp.tier4.stub.domain.model.fms.Place;
import jp.tier4.stub.domain.model.fms.Point;
import jp.tier4.stub.domain.model.fms.RegisterScheduleVehicleParam;
import jp.tier4.stub.domain.model.fms.RegisterScheduleVehicleResult;
import jp.tier4.stub.domain.model.fms.RetrieveAllPlaces;
import jp.tier4.stub.domain.model.fms.RetrieveAllVehicles;
import jp.tier4.stub.domain.model.fms.RetrievePointRoute;
import jp.tier4.stub.domain.model.fms.RetrieveSchedule;
import jp.tier4.stub.domain.model.fms.RetrieveSchedulelsVehicle;
import jp.tier4.stub.domain.model.fms.Schedule;
import jp.tier4.stub.domain.model.fms.ScheduleTask;
import jp.tier4.stub.domain.model.fms.Tag;
import jp.tier4.stub.domain.model.fms.Telemetry;
import jp.tier4.stub.domain.model.fms.Vehicle;

/**
 * スタブコントローラー
 * 
 */
@RestController
public class StubController {

    private int scheduleCnt = 0;

    private int wetherCnFirstt = 0;

    private int wetherCntSecond = 0;

    private int serviceLocationCntSecond = 0;

    private int serviceCnt = 0;

    private int vehiclesCnt = 0;

    /** FMS Auth:クライアントID */
    @Value("${fms.auth.client.id}")
    String authClientId;
    /** FMS Auth:サブジェクトID */
    @Value("${fms.auth.subject.id}")
    String authSubjectId;
    /** FMS Auth:プライベートキー */
    @Value("${fms.auth.private.key}")
    String authPrivateKey;
    /** FMS Auth:JWT有効期限 */
    @Value("${fms.auth.jwt.exp}")
    int authJwtExp;

    /**
     * トークン発行
     * 
     * @return
     */
    @PostMapping("/v2/token")
    public FmsAuthResponse tokenPost() {
        FmsAuthResponse resuponse = new FmsAuthResponse();
        resuponse.setAccess_token("accessToken");
        resuponse.setRefresh_token("refreshToken");
        resuponse.setToken_type(null);
        resuponse.setExpires_in(0);
        resuponse.setScope(null);
        return resuponse;
    }

    /**
     * JWTトークン発行
     * 
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @PostMapping("/v2/jwtToken")
    public String jwtTokenPost() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 秘密鍵をBase64でデコード
        byte[] decoded = Base64.getDecoder().decode(this.authPrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        Algorithm algorithm = Algorithm.RSA256(privKey);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        // JWT有効期限は60秒
        cal.add(Calendar.SECOND, this.authJwtExp);
        // JWTトークン取得

        String jwtToken = JWT.create().withIssuer(this.authClientId)
                .withSubject(this.authSubjectId)
                .withExpiresAt(cal.getTime())
                .sign(algorithm);

        return jwtToken;
    }

    /**
     * 自動運転車両情報全取得API
     * 
     * @return
     */
    @GetMapping("/v1/projects/{project_id}/environments/{environment_id}/vehicles")
    public RetrieveAllVehicles vehicles() {
        RetrieveAllVehicles resuponse = new RetrieveAllVehicles();
        resuponse.setTotal(0);

        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        Vehicle vehicle = new Vehicle();
        vehicle.setProject_id("tier4");
        vehicle.setEnvironment_id("5b078865-ab8a-422d-aa11-1437907dae32");
        vehicle.setVehicle_id("5b078865-ab8a-422d-aa11-1437907dae32");
        vehicle.setVehicle_name("vehicle001");
        vehicle.setCan_start(false);
        vehicle.setAcceptable_order("all");
        vehicle.setDescription("test");
        vehicle.setCreated_at("2014-10-10T04:50:40.000001+00:00");
        vehicle.setUpdated_at("2014-10-10T04:50:40.000001+00:00");

        Telemetry tele = new Telemetry();
        tele.setStatus("driving");
        tele.setDriveMode("auto");
        tele.setSpeed(10.5);
        tele.setBattery(75.5);
        Location location = new Location();
        location.setLat(35.6242681254445);
        location.setLng(139.74258640981);
        location.setHeight(0.01258640981);
        tele.setLocation(location);
        tele.setAccel(null);
        tele.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");
        vehicle.setTelemetry(tele);
        vehicles.add(vehicle);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setProject_id("tier4_2");
        vehicle2.setEnvironment_id("5b078865-ab8a-422d-aa11-1437907dae32_2");
        vehicle2.setVehicle_id("5b078865-ab8a-422d-aa11-1437907dae32_2");
        vehicle2.setVehicle_name("vehicle002");
        vehicle2.setCan_start(true);
        vehicle2.setAcceptable_order("all");
        vehicle2.setDescription("test");
        vehicle2.setCreated_at("2014-10-10T04:50:40.000001+00:00");
        vehicle2.setUpdated_at("2014-10-10T04:50:40.000001+00:00");

        Telemetry tele2 = new Telemetry();
        tele2.setStatus("driving");
        tele2.setDriveMode("auto");
        tele2.setSpeed(10.5);
        tele2.setBattery(75.5);
        Location location2 = new Location();
        location2.setLat(35.6242681254445);
        location2.setLng(139.74258640981);
        location2.setHeight(0.01258640981);
        tele2.setLocation(location2);
        tele2.setAccel(null);
        tele2.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");
        vehicle2.setTelemetry(tele2);
        vehicles.add(vehicle2);

        resuponse.setVehicles(vehicles);

        return resuponse;
    }

    /**
     * 自動運転車両情報全取得API v2
     * 
     * @return
     */
    @GetMapping("/v2/projects/{project_id}/environments/{environment_id}/vehicles")
    public RetrieveAllVehicles vehiclesv2() {
        RetrieveAllVehicles resuponse = new RetrieveAllVehicles();
        resuponse.setTotal(2);

        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        Vehicle vehicle = new Vehicle();
        vehicle.setProject_id("tier4");
        vehicle.setEnvironment_id("5b078865-ab8a-422d-aa11-1437907dae32");
        vehicle.setVehicle_id("5b078865-ab8a-422d-aa11-1437907dae32");
        vehicle.setVehicle_name("vehicle001");
        vehicle.setCan_start(false);
        vehicle.setAcceptable_order("all");
        vehicle.setDescription("test");
        vehicle.setCreated_at("2014-10-10T04:50:40.000001+00:00");
        vehicle.setUpdated_at("2014-10-10T04:50:40.000001+00:00");

        Telemetry tele = new Telemetry();
        tele.setStatus("driving");
        tele.setDriveMode("auto");
        tele.setSpeed(10.5);
        tele.setBattery(75.5);
        Location location = new Location();

        if (this.vehiclesCnt % 3 == 0) {
            location.setLat(36.512336);
            location.setLng(140.6221331);
            location.setHeight(0.01258640981);
        } else if (this.vehiclesCnt % 3 == 1) {
            location.setLat(36.50901301);
            location.setLng(140.629916);
            location.setHeight(0.01258640982);
        } else if (this.vehiclesCnt % 3 == 2) {
            location.setLat(36.508418023);
            location.setLng(140.621789001);
            location.setHeight(0.01258640983);
        }

        tele.setLocation(location);
        tele.setAccel(null);
        tele.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");
        vehicle.setTelemetry(tele);
        vehicles.add(vehicle);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setProject_id("tier4_2");
        vehicle2.setEnvironment_id("5b078865-ab8a-422d-aa11-1437907dae32_2");
        vehicle2.setVehicle_id("5b078865-ab8a-422d-aa11-1437907dae32_2");
        vehicle2.setVehicle_name("vehicle002");
        vehicle2.setCan_start(true);
        vehicle2.setAcceptable_order("all");
        vehicle2.setDescription("test");
        vehicle2.setCreated_at("2014-10-10T04:50:40.000001+00:00");
        vehicle2.setUpdated_at("2014-10-10T04:50:40.000001+00:00");

        Telemetry tele2 = new Telemetry();
        tele2.setStatus("driving");
        tele2.setDriveMode("auto");
        tele2.setSpeed(10.5);
        tele2.setBattery(75.5);
        Location location2 = new Location();

        if (vehiclesCnt % 3 == 0) {
            location2.setLat(36.5068601);
            location2.setLng(140.62591021);
            location2.setHeight(0.01258640984);
        } else if (vehiclesCnt % 3 == 1) {
            location2.setLat(36.511556309);
            location2.setLng(140.62011711);
            location2.setHeight(0.01258640985);
        } else if (vehiclesCnt % 3 == 2) {
            location2.setLat(36.5118677215);
            location2.setLng(140.626576032);
            location2.setHeight(0.01258640986);
        }

        tele2.setLocation(location2);
        tele2.setAccel(null);
        tele2.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");
        vehicle2.setTelemetry(tele2);
        vehicles.add(vehicle2);

        this.vehiclesCnt++;

        resuponse.setVehicles(vehicles);

        return resuponse;
    }

    /**
     * 自動運転車両情報取得API
     * 
     * @return
     */
    @GetMapping("/v1/projects/{project_id}/environments/{environment_id}/vehicles/{vehicle_id}")
    public Vehicle vehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setProject_id("tier4");
        vehicle.setEnvironment_id("5b078865-ab8a-422d-aa11-1437907dae32");
        vehicle.setVehicle_id("5b078865-ab8a-422d-aa11-1437907dae32");
        vehicle.setVehicle_name("vehicle001");
        vehicle.setCan_start(false);
        vehicle.setAcceptable_order("all");
        vehicle.setDescription("test");
        vehicle.setCreated_at("2014-10-10T04:50:40.000001+00:00");
        vehicle.setUpdated_at("2014-10-10T04:50:40.000001+00:00");

        Telemetry tele = new Telemetry();
        tele.setStatus("driving");
        tele.setDriveMode("auto");
        tele.setSpeed(10.5);
        tele.setBattery(75.5);
        Location location = new Location();
        location.setLat(35.6242681254445);
        location.setLng(139.74258640981);
        location.setHeight(0.01258640981);
        tele.setLocation(location);
        tele.setAccel(null);
        tele.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");
        vehicle.setTelemetry(tele);

        return vehicle;
    }

    /**
     * 場所情報取得API
     * 
     * @return
     */
    @GetMapping("/v1/projects/{project_id}/environments/{environment_id}/places")
    public RetrieveAllPlaces places() {
        RetrieveAllPlaces retrieveAllPlaces = new RetrieveAllPlaces();
        retrieveAllPlaces.setAreaMapVersionId("123-20141010045040356729");
        List<Place> places = new ArrayList<Place>();
        Place place = new Place();
        place.setPointId(1);
        place.setName("FirstBusStop");
        Location location = new Location();
        location.setLat(35.6242681254445);
        location.setLng(139.74258640981);
        place.setLocation(location);
        places.add(place);

        Place place2 = new Place();
        place2.setPointId(2);
        place2.setName("SecondBusStop");
        Location location2 = new Location();
        location2.setLat(55.6242681254445);
        location2.setLng(150.74258640981);
        place2.setLocation(location2);
        places.add(place2);

        retrieveAllPlaces.setPlaces(places);

        return retrieveAllPlaces;
    }

    /**
     * 自動運転車両スケジュール情報取得API
     * 
     * @return
     */
    @GetMapping("/v1/projects/{project_id}/environments/{environment_id}/vehicles/{vehicle_id}/schedules")
    public RetrieveSchedulelsVehicle vehicleSchedule() {
        RetrieveSchedulelsVehicle retrieveSchedulelsVehicle = new RetrieveSchedulelsVehicle();
        List<Schedule> schedules = new ArrayList<Schedule>();
        retrieveSchedulelsVehicle.setTotal(5);
        for (int i = 0; i < 5; i++) {
            Schedule schedule = new Schedule();
            schedule.setScheduleId("03486544-ab8b-4225-cc34-3457608vby56" + i);
            schedule.setProjectId("tier4");
            schedule.setEnvironmentId("5b078865-ab8a-422d-aa11-1437907dae32");
            schedule.setScheduleType("one-way");
            schedule.setReserved(true);
            schedule.setVehicleId("5b078865-ab8a-422d-aa11-1437907dae32");
            schedule.setStatus("todo");
            schedule.setPlanStartTime("2014-10-01T04:50:40.000001+00:0" + i);
            schedule.setPlanEndTime("2014-10-02T04:50:40.000001+00:0" + i);
            schedule.setActualStartTime("2014-10-03T04:50:40.000001+00:0" + i);
            schedule.setActualEndTime("2014-10-04T04:50:40.000001+00:0" + i);
            schedule.setDurationSec(300);
            schedule.setDeltaSec(200);
            List<Tag> tags = new ArrayList<Tag>();
            for (int j = 0; j < 5; j++) {
                Tag tag = new Tag();
                tag.setKey("user_id" + j);
                tag.setValue("U13" + j);
                tags.add(tag);
            }
            schedule.setTags(tags);
            schedule.setCreatedAt("2014-10-05T04:50:40.000001+00:00");
            schedule.setUpdatedAt("2014-10-06T04:50:40.000001+00:0");
            schedules.add(schedule);
        }

        retrieveSchedulelsVehicle.setSchedules(schedules);
        return retrieveSchedulelsVehicle;
    }

    /**
     * スケジュール情報取得API
     * 
     * @return
     */
    @GetMapping("/v1/projects/{project_id}/environments/{environment_id}/schedules/{schedule_id}")
    public RetrieveSchedule schedule() {
        RetrieveSchedule retrieveSchedule = new RetrieveSchedule();
        List<ScheduleTask> scheduleTasks = new ArrayList<ScheduleTask>();

        retrieveSchedule.setScheduleId("03486544-ab8b-4225-cc34-3457608vby56");
        retrieveSchedule.setProjectId("tier4");
        retrieveSchedule.setEnvironmentId("5b078865-ab8a-422d-aa11-1437907dae32");
        retrieveSchedule.setScheduleType("one-way");
        retrieveSchedule.setReserved(true);
        retrieveSchedule.setVehicleId("5b078865-ab8a-422d-aa11-1437907dae32");
        retrieveSchedule.setStatus("todo");
        retrieveSchedule.setPlanStartTime("2014-10-01T04:50:40.000001+00:00");
        retrieveSchedule.setPlanEndTime("2014-10-02T04:50:40.000001+00:00");
        retrieveSchedule.setActualStartTime("2014-10-03T04:50:40.000001+00:00");
        retrieveSchedule.setActualEndTime("2014-10-04T04:50:40.000001+00:00");
        retrieveSchedule.setDurationSec(300);
        retrieveSchedule.setDeltaSec(200);
        List<Tag> tags = new ArrayList<Tag>();
        for (int i = 0; i < 5; i++) {
            Tag tag = new Tag();
            tag.setKey("user_id" + i);
            tag.setValue("U13" + i);
            tags.add(tag);
        }
        retrieveSchedule.setTags(tags);
        retrieveSchedule.setCreatedAt("2014-10-05T04:50:40.000001+00:00");
        retrieveSchedule.setUpdatedAt("2014-10-06T04:50:40.000001+00:0");

        for (int i = 0; i < 5; i++) {
            ScheduleTask tasks = new ScheduleTask();
            tasks.setTaskId("03486544-ab8b-4225-cc34-3457608vby5" + i);
            tasks.setTaskType("move");
            tasks.setReserved(false);
            tasks.setStatus("done");

            Place origin = new Place();
            origin.setPointId(1);
            origin.setName("FirstBusStop" + i);
            Location originLocation = new Location();
            originLocation.setLat(35.6242681254440 + i);
            originLocation.setLng(139.74258640980 + i);
            origin.setLocation(originLocation);
            tasks.setOrigin(origin);

            Place destination = new Place();
            destination.setPointId(2);
            destination.setName("SecondBusStop" + i);
            Location destinationLocation = new Location();
            destinationLocation.setLat(55.6242681254440 + i);
            destinationLocation.setLng(150.74258640980 + i);
            destination.setLocation(destinationLocation);
            tasks.setDestination(destination);

            List<String> routeIds = new ArrayList<String>();
            routeIds.add("id1" + i);
            routeIds.add("id2" + i);
            tasks.setRouteIds(routeIds);

            tasks.setPlanStartTime("2014-10-01T04:50:40.000001+00:0" + i);
            tasks.setPlanEndTime("2014-10-02T04:50:40.000001+00:0" + i);
            tasks.setActualStartTime("2014-10-03T04:50:40.000001+00:0" + i);
            tasks.setActualEndTime("2014-10-04T04:50:40.000001+00:0" + i);
            tasks.setDurationSec(300 + i);
            tasks.setDescription("説明" + i);
            scheduleTasks.add(tasks);
        }
        retrieveSchedule.setTasks(scheduleTasks);
        return retrieveSchedule;
    }

    /**
     * スケジュール情報取得API 初回エラー
     * 
     * @return
     */
    @GetMapping("/v1/schedules/firstError")
    public Object scheduleFirstError() {

        if (this.scheduleCnt == 0) {
            this.scheduleCnt += 1;
            Map<String, Object> map = new HashMap<>();
            map.put("code", "Unauthenticated");
            map.put("message", "Unauthenticated.");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.UNAUTHORIZED);
        } else {

            RetrieveSchedule retrieveSchedule = new RetrieveSchedule();
            List<ScheduleTask> scheduleTasks = new ArrayList<ScheduleTask>();

            retrieveSchedule.setScheduleId("03486544-ab8b-4225-cc34-3457608vby56");
            retrieveSchedule.setProjectId("tier4");
            retrieveSchedule.setEnvironmentId("5b078865-ab8a-422d-aa11-1437907dae32");
            retrieveSchedule.setScheduleType("one-way");
            retrieveSchedule.setReserved(true);
            retrieveSchedule.setVehicleId("5b078865-ab8a-422d-aa11-1437907dae32");
            retrieveSchedule.setStatus("todo");
            retrieveSchedule.setPlanStartTime("2014-10-01T04:50:40.000001+00:00");
            retrieveSchedule.setPlanEndTime("2014-10-02T04:50:40.000001+00:00");
            retrieveSchedule.setActualStartTime("2014-10-03T04:50:40.000001+00:00");
            retrieveSchedule.setActualEndTime("2014-10-04T04:50:40.000001+00:00");
            retrieveSchedule.setDurationSec(300);
            retrieveSchedule.setDeltaSec(200);
            List<Tag> tags = new ArrayList<Tag>();
            for (int i = 0; i < 5; i++) {
                Tag tag = new Tag();
                tag.setKey("user_id" + i);
                tag.setValue("U13" + i);
                tags.add(tag);
            }
            retrieveSchedule.setTags(tags);
            retrieveSchedule.setCreatedAt("2014-10-05T04:50:40.000001+00:00");
            retrieveSchedule.setUpdatedAt("2014-10-06T04:50:40.000001+00:0");

            for (int i = 0; i < 5; i++) {
                ScheduleTask tasks = new ScheduleTask();
                tasks.setTaskId("03486544-ab8b-4225-cc34-3457608vby5" + i);
                tasks.setTaskType("move");
                tasks.setReserved(false);
                tasks.setStatus("done");

                Place origin = new Place();
                origin.setPointId(1);
                origin.setName("FirstBusStop" + i);
                Location originLocation = new Location();
                originLocation.setLat(35.6242681254440 + i);
                originLocation.setLng(139.74258640980 + i);
                origin.setLocation(originLocation);
                tasks.setOrigin(origin);

                Place destination = new Place();
                destination.setPointId(2);
                destination.setName("SecondBusStop" + i);
                Location destinationLocation = new Location();
                destinationLocation.setLat(55.6242681254440 + i);
                destinationLocation.setLng(150.74258640980 + i);
                destination.setLocation(destinationLocation);
                tasks.setDestination(destination);

                List<String> routeIds = new ArrayList<String>();
                routeIds.add("id1" + i);
                routeIds.add("id2" + i);
                tasks.setRouteIds(routeIds);

                tasks.setPlanStartTime("2014-10-01T04:50:40.000001+00:0" + i);
                tasks.setPlanEndTime("2014-10-02T04:50:40.000001+00:0" + i);
                tasks.setActualStartTime("2014-10-03T04:50:40.000001+00:0" + i);
                tasks.setActualEndTime("2014-10-04T04:50:40.000001+00:0" + i);
                tasks.setDurationSec(300 + i);
                tasks.setDescription("説明" + i);
                scheduleTasks.add(tasks);
            }
            retrieveSchedule.setTasks(scheduleTasks);
            this.scheduleCnt = 0;
            return retrieveSchedule;
        }
    }

    /**
     * 経路情報取得API
     * 
     * @return
     */
    @GetMapping("/v1/projects/{project_id}/environments/{environment_id}/routes/{route_id}")
    public RetrievePointRoute route(String routeId) {
        RetrievePointRoute expected = new RetrievePointRoute();

        List<Point> points = new ArrayList<Point>();
        Point point = new Point();
        Location location = new Location();
        location.setLat(10);
        location.setLng(20);
        point.setLocation(location);
        points.add(point);

        point = new Point();
        location = new Location();
        location.setLat(30);
        location.setLng(40);
        point.setLocation(location);
        points.add(point);

        expected.setPoints(points);
        expected.setEta_sec(3600);
        expected.setDistance_meters(30000);

        return expected;
    }

    /**
     * スケジュール情報登録API
     * 
     * @return
     */
    @PostMapping("/v1/projects/{project_id}/environments/{environment_id}/vehicles/{vehicle_id}/schedules")
    public RegisterScheduleVehicleResult scheduleRegist(@RequestBody RegisterScheduleVehicleParam param) {
        RegisterScheduleVehicleResult expected = new RegisterScheduleVehicleResult();

        List<ScheduleTask> scheduleTasks = new ArrayList<ScheduleTask>();

        expected.setScheduleId("03486544-ab8b-4225-cc34-3457608vby56");
        expected.setProjectId("tier4");
        expected.setEnvironmentId("5b078865-ab8a-422d-aa11-1437907dae32");
        expected.setScheduleType("one-way");
        expected.setReserved(true);
        expected.setVehicleId("5b078865-ab8a-422d-aa11-1437907dae32");
        expected.setStatus("todo");
        expected.setPlanStartTime("2014-10-01T04:50:40.000001+00:00");
        expected.setPlanEndTime("2014-10-02T04:50:40.000001+00:00");
        expected.setActualStartTime("2014-10-03T04:50:40.000001+00:00");
        expected.setActualEndTime("2014-10-04T04:50:40.000001+00:00");
        expected.setDurationSec(300);
        expected.setDeltaSec(200);
        List<Tag> tags = new ArrayList<Tag>();
        for (int i = 0; i < 5; i++) {
            Tag tag = new Tag();
            tag.setKey("user_id" + i);
            tag.setValue("U13" + i);
            tags.add(tag);
        }
        expected.setTags(tags);
        expected.setCreatedAt("2014-10-05T04:50:40.000001+00:00");
        expected.setUpdatedAt("2014-10-06T04:50:40.000001+00:0");

        for (int i = 0; i < 5; i++) {
            ScheduleTask tasks = new ScheduleTask();
            tasks.setTaskId("03486544-ab8b-4225-cc34-3457608vby5" + i);
            tasks.setTaskType("move");
            tasks.setReserved(false);
            tasks.setStatus("done");

            Place origin = new Place();
            origin.setPointId(1);
            origin.setName("FirstBusStop" + i);
            Location originLocation = new Location();
            originLocation.setLat(35.6242681254440 + i);
            originLocation.setLng(139.74258640980 + i);
            origin.setLocation(originLocation);
            tasks.setOrigin(origin);

            Place destination = new Place();
            destination.setPointId(2);
            destination.setName("SecondBusStop" + i);
            Location destinationLocation = new Location();
            destinationLocation.setLat(55.6242681254440 + i);
            destinationLocation.setLng(150.74258640980 + i);
            destination.setLocation(destinationLocation);
            tasks.setDestination(destination);

            List<String> routeIds = new ArrayList<String>();
            routeIds.add("id1" + i);
            routeIds.add("id2" + i);
            tasks.setRouteIds(routeIds);

            tasks.setPlanStartTime("2014-10-01T04:50:40.000001+00:0" + i);
            tasks.setPlanEndTime("2014-10-02T04:50:40.000001+00:0" + i);
            tasks.setActualStartTime("2014-10-03T04:50:40.000001+00:0" + i);
            tasks.setActualEndTime("2014-10-04T04:50:40.000001+00:0" + i);
            tasks.setDurationSec(300 + i);
            tasks.setDescription("説明" + i);
            scheduleTasks.add(tasks);
        }
        expected.setTasks(scheduleTasks);
        return expected;
    }

    /**
     * ユーザ当人認証API
     * 
     * @return
     */
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthPasswordParam param) {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", "accessToken");
        map.put("refreshToken", "refreshToken");
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
    }

    /**
     * クライアント認証API
     * 
     * @return
     */
    @PostMapping("/auth/client")
    public ResponseEntity<Map<String, Object>> client(@RequestBody AuthClientParam param) {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", "accessToken");
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
    }

    /**
     * サービス地点情報取得
     * 
     * @return
     */
    @GetMapping("/serviceLocationInfo")
    public BaseModel<ServiceLocationInfo> serviceLocationInfo(Integer serviceLocationID) {
        BaseModel<ServiceLocationInfo> result = new BaseModel<ServiceLocationInfo>();
        ServiceLocationInfo info = new ServiceLocationInfo();
        info.setServiceLocationID(serviceLocationID);

        // 通常-稼働停止-路駐車-稼働停止かつ路駐車
        List<RoadsideUnit> roadsideUnitList = new ArrayList<RoadsideUnit>();
        if (this.serviceCnt == 0) {
            RoadsideUnit item0_1 = new RoadsideUnit();
            item0_1.setRoadsideUnitID(1);
            item0_1.setRoadsideUnitClassification(0);
            RoadsideUnit item0_2 = new RoadsideUnit();
            item0_2.setRoadsideUnitID(2);
            item0_2.setRoadsideUnitClassification(0);
            RoadsideUnit item0_3 = new RoadsideUnit();
            item0_3.setRoadsideUnitID(3);
            item0_3.setRoadsideUnitClassification(0);
            RoadsideUnit item0_4 = new RoadsideUnit();
            item0_4.setRoadsideUnitID(4);
            item0_4.setRoadsideUnitClassification(0);
            roadsideUnitList.add(item0_1);
            roadsideUnitList.add(item0_2);
            roadsideUnitList.add(item0_3);
            roadsideUnitList.add(item0_4);
            this.serviceCnt++;
        } else if (this.serviceCnt == 1) {
            RoadsideUnit item1_1 = new RoadsideUnit();
            item1_1.setRoadsideUnitID(11);
            item1_1.setRoadsideUnitClassification(1);
            RoadsideUnit item1_2 = new RoadsideUnit();
            item1_2.setRoadsideUnitID(12);
            item1_2.setRoadsideUnitClassification(1);
            RoadsideUnit item1_3 = new RoadsideUnit();
            item1_3.setRoadsideUnitID(13);
            item1_3.setRoadsideUnitClassification(1);
            RoadsideUnit item1_4 = new RoadsideUnit();
            item1_4.setRoadsideUnitID(14);
            item1_4.setRoadsideUnitClassification(1);
            roadsideUnitList.add(item1_1);
            roadsideUnitList.add(item1_2);
            roadsideUnitList.add(item1_3);
            roadsideUnitList.add(item1_4);
            this.serviceCnt++;
        } else if (this.serviceCnt == 2) {
            RoadsideUnit item7_1 = new RoadsideUnit();
            item7_1.setRoadsideUnitID(71);
            item7_1.setRoadsideUnitClassification(7);
            RoadsideUnit item7_2 = new RoadsideUnit();
            item7_2.setRoadsideUnitID(72);
            item7_2.setRoadsideUnitClassification(7);
            RoadsideUnit item7_3 = new RoadsideUnit();
            item7_3.setRoadsideUnitID(73);
            item7_3.setRoadsideUnitClassification(7);
            RoadsideUnit item7_4 = new RoadsideUnit();
            item7_4.setRoadsideUnitID(74);
            item7_4.setRoadsideUnitClassification(7);
            roadsideUnitList.add(item7_1);
            roadsideUnitList.add(item7_2);
            roadsideUnitList.add(item7_3);
            roadsideUnitList.add(item7_4);
            this.serviceCnt = 0;
        }

        info.setRoadsideUnitList(roadsideUnitList);
        result.setAttribute(info);
        return result;
    }

    /**
     * サービス地点情報取得 成功３回、失敗１回を繰り返すスタブ
     * 
     * @return
     */
    @GetMapping("/serviceLocationInfoSecondError")
    public Object serviceLocationInfoSecondError(Integer serviceLocationID) {

        // サービス地点ID分(3つ)呼び出されるため、初回呼び出し分のみ成功させる
        if (this.serviceLocationCntSecond < 3) {
            // 3回分成功するときの処理
            BaseModel<ServiceLocationInfo> result = new BaseModel<ServiceLocationInfo>();
            ServiceLocationInfo info = new ServiceLocationInfo();
            info.setServiceLocationID(serviceLocationID);

            // 通常-稼働停止-路駐車-稼働停止かつ路駐車
            List<RoadsideUnit> roadsideUnitList = new ArrayList<RoadsideUnit>();
            RoadsideUnit item0_1 = new RoadsideUnit();
            item0_1.setRoadsideUnitID(1);
            item0_1.setRoadsideUnitClassification(0);
            RoadsideUnit item0_2 = new RoadsideUnit();
            item0_2.setRoadsideUnitID(2);
            item0_2.setRoadsideUnitClassification(0);
            RoadsideUnit item0_3 = new RoadsideUnit();
            item0_3.setRoadsideUnitID(3);
            item0_3.setRoadsideUnitClassification(0);
            RoadsideUnit item0_4 = new RoadsideUnit();
            item0_4.setRoadsideUnitID(4);
            item0_4.setRoadsideUnitClassification(0);

            RoadsideUnit item1_1 = new RoadsideUnit();
            item1_1.setRoadsideUnitID(11);
            item1_1.setRoadsideUnitClassification(1);
            RoadsideUnit item1_2 = new RoadsideUnit();
            item1_2.setRoadsideUnitID(12);
            item1_2.setRoadsideUnitClassification(1);
            RoadsideUnit item1_3 = new RoadsideUnit();
            item1_3.setRoadsideUnitID(13);
            item1_3.setRoadsideUnitClassification(1);
            RoadsideUnit item1_4 = new RoadsideUnit();
            item1_4.setRoadsideUnitID(14);
            item1_4.setRoadsideUnitClassification(1);

            RoadsideUnit item7_1 = new RoadsideUnit();
            item7_1.setRoadsideUnitID(71);
            item7_1.setRoadsideUnitClassification(7);
            RoadsideUnit item7_2 = new RoadsideUnit();
            item7_2.setRoadsideUnitID(72);
            item7_2.setRoadsideUnitClassification(7);
            RoadsideUnit item7_3 = new RoadsideUnit();
            item7_3.setRoadsideUnitID(73);
            item7_3.setRoadsideUnitClassification(7);
            RoadsideUnit item7_4 = new RoadsideUnit();
            item7_4.setRoadsideUnitID(74);
            item7_4.setRoadsideUnitClassification(7);

            roadsideUnitList.add(item0_1);
            roadsideUnitList.add(item0_2);
            roadsideUnitList.add(item0_3);
            roadsideUnitList.add(item0_4);
            roadsideUnitList.add(item1_1);
            roadsideUnitList.add(item1_2);
            roadsideUnitList.add(item1_3);
            roadsideUnitList.add(item1_4);
            roadsideUnitList.add(item7_1);
            roadsideUnitList.add(item7_2);
            roadsideUnitList.add(item7_3);
            roadsideUnitList.add(item7_4);
            info.setRoadsideUnitList(roadsideUnitList);
            result.setAttribute(info);
            // 呼び出された回数をカウントするため、インクリメントする
            this.serviceLocationCntSecond += 1;
            return result;
        } else {
            // サービス地点分（3つ）成功後に呼び出された際に、エラーを返す
            // 再度呼び出された回数をカウントするため、0に戻す
            this.serviceLocationCntSecond = 0;
            Map<String, Object> map = new HashMap<>();
            map.put("code", "InternalServerError");
            map.put("message", "Internal server error.");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 路側気情報取得
     * 
     * @return
     */
    @GetMapping("/roadsideUnitInfo")
    public BaseModel<RoadSideUnitInfo> roadSideUnitInfo(Integer serviceLocationID, Integer roadsideUnitID) {

        BaseModel<RoadSideUnitInfo> result = new BaseModel<RoadSideUnitInfo>();
        RoadSideUnitInfo info = new RoadSideUnitInfo();
        info = getRoadSideUnitInfo(roadsideUnitID);
        info.setServiceLocationID(serviceLocationID);
        result.setAttribute(info);
        return result;
    }

    /**
     * 死活情報取得
     * 
     * @return
     */
    @GetMapping("/aliveMonitoringInfo")
    public BaseModel<AliveMonitoringInfo> aliveMonitoringInfo(Integer serviceLocationID, Integer roadsideUnitID) {

        BaseModel<AliveMonitoringInfo> result = new BaseModel<AliveMonitoringInfo>();
        AliveMonitoringInfo info = new AliveMonitoringInfo();
        info = getAliveMonitoringInfo(roadsideUnitID);
        info.setServiceLocationID(serviceLocationID);
        info.setRoadsideUnitID(roadsideUnitID);
        result.setAttribute(info);
        return result;
    }

    /**
     * 物標情報取得
     * 
     * @return
     */
    @GetMapping("/targetInfo")
    public BaseModel<TargetInfo> targetInfo(Integer serviceLocationID, Integer roadsideUnitID) {

        BaseModel<TargetInfo> result = new BaseModel<TargetInfo>();
        TargetInfo info = new TargetInfo();
        List<DeviceIndividualInfo> deviceIndividualInfo = new ArrayList<DeviceIndividualInfo>();
        DeviceIndividualInfo deviceIndividual = new DeviceIndividualInfo();
        List<TargetIndividualInfo> targetIndividualInfo = new ArrayList<TargetIndividualInfo>();
        TargetIndividualInfo targetIndividual = new TargetIndividualInfo();
        // デフォルトで静止状態がnullの要素を追加
        targetIndividualInfo.add(targetIndividual);

        Integer restingState = null;
        switch (roadsideUnitID % 10) {
            case 3:
            case 4:
                restingState = 140;
                break;
            default:
                restingState = 139;
        }
        // 追加で静止状態が境界値となる要素を追加（路側機IDの末尾が3 or 4のものは路駐車有と判断されるような要素追加）
        targetIndividual.setRestingState(restingState);
        targetIndividualInfo.add(targetIndividual);

        deviceIndividual.setTargetIndividualInfo(targetIndividualInfo);
        deviceIndividualInfo.add(deviceIndividual);
        deviceIndividualInfo.add(deviceIndividual);
        info.setDeviceIndividualInfo(deviceIndividualInfo);
        info.setServiceLocationID(serviceLocationID);
        info.setRoadsideUnitID(roadsideUnitID);
        result.setAttribute(info);
        return result;
    }

    /**
     * 物標情報取得 静止状態を表すrestingStateの境界値（上限）を返却するスタブ
     * 
     * @return
     */
    @GetMapping("/targetInfoUpperLimitCase")
    public BaseModel<TargetInfo> targetInfoUpperLimitCase(Integer serviceLocationID, Integer roadsideUnitID) {

        BaseModel<TargetInfo> result = new BaseModel<TargetInfo>();
        TargetInfo info = new TargetInfo();
        List<DeviceIndividualInfo> deviceIndividualInfo = new ArrayList<DeviceIndividualInfo>();
        DeviceIndividualInfo deviceIndividual = new DeviceIndividualInfo();
        List<TargetIndividualInfo> targetIndividualInfo = new ArrayList<TargetIndividualInfo>();
        TargetIndividualInfo targetIndividual = new TargetIndividualInfo();
        // デフォルトで静止状態がnullの要素を追加
        targetIndividualInfo.add(targetIndividual);

        Integer restingState = null;
        switch (roadsideUnitID % 10) {
            case 3:
            case 4:
                restingState = 3600;
                break;
            default:
                restingState = 3601;
        }
        // 追加で静止状態が境界値となる要素を追加（路側機IDの末尾が3 or 4のものは路駐車有と判断されるような要素追加）
        targetIndividual.setRestingState(restingState);
        targetIndividualInfo.add(targetIndividual);

        deviceIndividual.setTargetIndividualInfo(targetIndividualInfo);
        deviceIndividualInfo.add(deviceIndividual);
        deviceIndividualInfo.add(deviceIndividual);
        info.setDeviceIndividualInfo(deviceIndividualInfo);
        info.setServiceLocationID(serviceLocationID);
        info.setRoadsideUnitID(roadsideUnitID);
        result.setAttribute(info);
        return result;
    }

    /**
     * 準動的情報検索API
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/semiDynamicInfo")
    public SemiDynamicInfoResult semiDynamicInfo(String encodedData) throws UnsupportedEncodingException {
        SemiDynamicInfoResult result = new SemiDynamicInfoResult();
        result.setDataModelType("test1");
        SemiDynamicInfoResultDataModel att = new SemiDynamicInfoResultDataModel();
        List<SemiDynamicInfoResultLevel1DataModel> level1 = new ArrayList<SemiDynamicInfoResultLevel1DataModel>();
        SemiDynamicInfoResultLevel1DataModel l1 = new SemiDynamicInfoResultLevel1DataModel();
        SemiDynamicInfoResultLevel1WeatherDataModel weatherInfo = new SemiDynamicInfoResultLevel1WeatherDataModel();

        l1.setWeatherInfo(weatherInfo);

        ObjectMapper mapper = new ObjectMapper();
        SemiDynamicInfoBase param = new SemiDynamicInfoBase();
        try {

            param = mapper.readValue(encodedData, SemiDynamicInfoBase.class);
            // 時間部分を取得して処理分岐

            // 文字列→時間
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf.parse(param.getTargetTime());
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
            // 時間取得
            String hour = sdf2.format(date);
            int h = Integer.parseInt(hour);
            BigDecimal hd = new BigDecimal(hour);
            // 気象
            String weather = getWeather(h);
            // 風向
            Double windDirection = getWindDirection(h);
            weatherInfo.setDateTime(param.getTargetTime());
            weatherInfo.setWeather(weather);
            weatherInfo.setTemperature(hd.multiply(BigDecimal.valueOf(0.1)).doubleValue());
            weatherInfo.setWindDirection(windDirection);
            weatherInfo.setWindSpeed(hd.multiply(BigDecimal.valueOf(0.2)).doubleValue());
            weatherInfo.setRainfall(hd.multiply(BigDecimal.valueOf(0.3)).doubleValue());

        } catch (JsonProcessingException | ParseException e) {
            throw new RuntimeException(e);
        }

        l1.setWeatherInfo(weatherInfo);
        level1.add(l1);
        att.setLevel1(level1);
        att.setResult(null);
        att.setTargetTime(null);
        result.setAttribute(att);
        return result;
    }

    /**
     * 準動的情報検索API
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/semiDynamicInfoFirstError")
    public Object semiDynamicInfoFirstError(String encodedData) throws UnsupportedEncodingException {
        if (this.wetherCnFirstt < 1) {
            this.wetherCnFirstt += 1;
            Map<String, Object> map = new HashMap<>();
            map.put("code", "InternalServerError");
            map.put("message", "Internal server error.");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            SemiDynamicInfoResult result = new SemiDynamicInfoResult();
            result.setDataModelType("test1");
            SemiDynamicInfoResultDataModel att = new SemiDynamicInfoResultDataModel();
            List<SemiDynamicInfoResultLevel1DataModel> level1 = new ArrayList<SemiDynamicInfoResultLevel1DataModel>();
            SemiDynamicInfoResultLevel1DataModel l1 = new SemiDynamicInfoResultLevel1DataModel();
            SemiDynamicInfoResultLevel1WeatherDataModel weatherInfo = new SemiDynamicInfoResultLevel1WeatherDataModel();

            l1.setWeatherInfo(weatherInfo);

            ObjectMapper mapper = new ObjectMapper();
            SemiDynamicInfoBase param = new SemiDynamicInfoBase();
            try {

                param = mapper.readValue(encodedData, SemiDynamicInfoBase.class);
                // 時間部分を取得して処理分岐

                // 文字列→時間
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = sdf.parse(param.getTargetTime());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
                // 時間取得
                String hour = sdf2.format(date);
                int h = Integer.parseInt(hour);
                BigDecimal hd = new BigDecimal(hour);
                // 気象
                String weather = getWeather(h);
                // 風向
                Double windDirection = getWindDirection(h);
                weatherInfo.setDateTime(param.getTargetTime());
                weatherInfo.setWeather(weather);
                weatherInfo.setTemperature(hd.multiply(BigDecimal.valueOf(0.1)).doubleValue());
                weatherInfo.setWindDirection(windDirection);
                weatherInfo.setWindSpeed(hd.multiply(BigDecimal.valueOf(0.2)).doubleValue());
                weatherInfo.setRainfall(hd.multiply(BigDecimal.valueOf(0.3)).doubleValue());

            } catch (JsonProcessingException | ParseException e) {
                throw new RuntimeException(e);
            }

            l1.setWeatherInfo(weatherInfo);
            level1.add(l1);
            att.setLevel1(level1);
            att.setResult(null);
            att.setTargetTime(null);
            result.setAttribute(att);

            // リセット
            if (this.wetherCnFirstt == 72) {
                this.wetherCnFirstt = 0;
            } else {
                this.wetherCnFirstt += 1;
            }

            return result;
        }
    }

    /**
     * 準動的情報検索API
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/semiDynamicInfoSecondError")
    public Object semiDynamicInfoSecondError(String encodedData) throws UnsupportedEncodingException {
        if (this.wetherCntSecond < 72) {
            SemiDynamicInfoResult result = new SemiDynamicInfoResult();
            result.setDataModelType("test1");
            SemiDynamicInfoResultDataModel att = new SemiDynamicInfoResultDataModel();
            List<SemiDynamicInfoResultLevel1DataModel> level1 = new ArrayList<SemiDynamicInfoResultLevel1DataModel>();
            SemiDynamicInfoResultLevel1DataModel l1 = new SemiDynamicInfoResultLevel1DataModel();
            SemiDynamicInfoResultLevel1WeatherDataModel weatherInfo = new SemiDynamicInfoResultLevel1WeatherDataModel();

            l1.setWeatherInfo(weatherInfo);

            ObjectMapper mapper = new ObjectMapper();
            SemiDynamicInfoBase param = new SemiDynamicInfoBase();
            try {

                param = mapper.readValue(encodedData, SemiDynamicInfoBase.class);
                // 時間部分を取得して処理分岐

                // 文字列→時間
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = sdf.parse(param.getTargetTime());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
                // 時間取得
                String hour = sdf2.format(date);
                int h = Integer.parseInt(hour);
                BigDecimal hd = new BigDecimal(hour);
                // 気象
                String weather = getWeather(h);
                // 風向
                Double windDirection = getWindDirection(h);
                weatherInfo.setDateTime(param.getTargetTime());
                weatherInfo.setWeather(weather);
                weatherInfo.setTemperature(hd.multiply(BigDecimal.valueOf(0.1)).doubleValue());
                weatherInfo.setWindDirection(windDirection);
                weatherInfo.setWindSpeed(hd.multiply(BigDecimal.valueOf(0.2)).doubleValue());
                weatherInfo.setRainfall(hd.multiply(BigDecimal.valueOf(0.3)).doubleValue());

            } catch (JsonProcessingException | ParseException e) {
                throw new RuntimeException(e);
            }

            l1.setWeatherInfo(weatherInfo);
            level1.add(l1);
            att.setLevel1(level1);
            att.setResult(null);
            att.setTargetTime(null);
            result.setAttribute(att);

            this.wetherCntSecond += 1;
            System.out.println(wetherCntSecond);
            return result;
        } else {
            this.wetherCntSecond = 0;
            Map<String, Object> map = new HashMap<>();
            map.put("code", "InternalServerError");
            map.put("message", "Internal server error.");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 準動的情報検索API
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/semiDynamicInfoNullCase")
    public Object semiDynamicInfoNullCase(String encodedData) throws UnsupportedEncodingException {
        SemiDynamicInfoResult result = new SemiDynamicInfoResult();
        result.setDataModelType("test1");
        SemiDynamicInfoResultDataModel att = new SemiDynamicInfoResultDataModel();
        List<SemiDynamicInfoResultLevel1DataModel> level1 = new ArrayList<SemiDynamicInfoResultLevel1DataModel>();
        SemiDynamicInfoResultLevel1DataModel l1 = new SemiDynamicInfoResultLevel1DataModel();
        SemiDynamicInfoResultLevel1WeatherDataModel weatherInfo = new SemiDynamicInfoResultLevel1WeatherDataModel();

        l1.setWeatherInfo(weatherInfo);

        ObjectMapper mapper = new ObjectMapper();
        SemiDynamicInfoBase param = new SemiDynamicInfoBase();
        try {

            param = mapper.readValue(encodedData, SemiDynamicInfoBase.class);
            // 時間部分を取得して処理分岐

            // 文字列→時間
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf.parse(param.getTargetTime());
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
            // 時間取得
            String hour = sdf2.format(date);
            int h = Integer.parseInt(hour);
            BigDecimal hd = new BigDecimal(hour);
            // 気象
            String weather = getWeather(h);
            // 風向
            Double windDirection = getWindDirection(h);
            weatherInfo.setDateTime(param.getTargetTime());
            weatherInfo.setWeather(h % 10 == 1 ? null : weather);
            weatherInfo.setTemperature(h % 10 == 2 ? null : hd.multiply(BigDecimal.valueOf(0.1)).doubleValue());
            weatherInfo.setWindDirection(h % 10 == 3 ? null : windDirection);
            weatherInfo.setWindSpeed(h % 10 == 4 ? null : hd.multiply(BigDecimal.valueOf(0.2)).doubleValue());
            weatherInfo.setRainfall(h % 10 == 5 ? null : hd.multiply(BigDecimal.valueOf(0.3)).doubleValue());

        } catch (JsonProcessingException | ParseException e) {
            throw new RuntimeException(e);
        }

        l1.setWeatherInfo(weatherInfo);
        level1.add(l1);
        att.setLevel1(level1);
        att.setResult(null);
        att.setTargetTime(null);
        result.setAttribute(att);

        return result;
    }

    /**
     * 準動的情報検索API 時刻を10で除算した余りに応じて、不明を表す65535.0を返却するスタブ
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/semiDynamicInfoUnknownCase")
    public Object semiDynamicInfoUnknownCase(String encodedData) throws UnsupportedEncodingException {
        SemiDynamicInfoResult result = new SemiDynamicInfoResult();
        result.setDataModelType("test1");
        SemiDynamicInfoResultDataModel att = new SemiDynamicInfoResultDataModel();
        List<SemiDynamicInfoResultLevel1DataModel> level1 = new ArrayList<SemiDynamicInfoResultLevel1DataModel>();
        SemiDynamicInfoResultLevel1DataModel l1 = new SemiDynamicInfoResultLevel1DataModel();
        SemiDynamicInfoResultLevel1WeatherDataModel weatherInfo = new SemiDynamicInfoResultLevel1WeatherDataModel();

        l1.setWeatherInfo(weatherInfo);

        ObjectMapper mapper = new ObjectMapper();
        SemiDynamicInfoBase param = new SemiDynamicInfoBase();
        try {

            param = mapper.readValue(encodedData, SemiDynamicInfoBase.class);
            // 時間部分を取得して処理分岐

            // 文字列→時間
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf.parse(param.getTargetTime());
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
            // 時間取得
            String hour = sdf2.format(date);
            int h = Integer.parseInt(hour);
            BigDecimal hd = new BigDecimal(hour);
            // 気象
            String weather = getWeather(h);
            // 風向
            Double windDirection = getWindDirection(h);
            weatherInfo.setDateTime(param.getTargetTime());
            weatherInfo.setWeather(h % 10 == 1 ? "65535.0" : weather);
            weatherInfo.setTemperature(h % 10 == 2 ? 65535.0 : hd.multiply(BigDecimal.valueOf(0.1)).doubleValue());
            weatherInfo.setWindDirection(h % 10 == 3 ? 65535.0 : windDirection);
            weatherInfo.setWindSpeed(h % 10 == 4 ? 65535.0 : hd.multiply(BigDecimal.valueOf(0.2)).doubleValue());
            weatherInfo.setRainfall(h % 10 == 5 ? 65535.0 : hd.multiply(BigDecimal.valueOf(0.3)).doubleValue());

        } catch (JsonProcessingException | ParseException e) {
            throw new RuntimeException(e);
        }

        l1.setWeatherInfo(weatherInfo);
        level1.add(l1);
        att.setLevel1(level1);
        att.setResult(null);
        att.setTargetTime(null);
        result.setAttribute(att);

        return result;
    }

    /**
     * 
     * 路側機情報の設定
     *
     * @param roadsideUnitID
     * @return
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    private RoadSideUnitInfo getRoadSideUnitInfo(Integer roadsideUnitID) {
        RoadSideUnitInfo info = new RoadSideUnitInfo();
        info.setRoadsideUnitName("路側機名称" + roadsideUnitID);
        Integer lat = 0;
        Integer lon = 0;
        switch (roadsideUnitID) {
            case 1:
                lat = 365127530;
                lon = 1406207250;
                break;
            case 2:
                lat = 365121000;
                lon = 1406231940;
                break;
            case 3:
                lat = 365118700;
                lon = 1406258090;
                break;
            case 4:
                lat = 365116830;
                lon = 1406288900;
                break;
            case 11:
                lat = 365115620;
                lon = 1406307290;
                break;
            case 12:
                lat = 365098680;
                lon = 1406301870;
                break;
            case 13:
                lat = 365069280;
                lon = 1406292390;
                break;
            case 14:
                lat = 365057430;
                lon = 1406288930;
                break;
            case 71:
                lat = 365074280;
                lon = 1406243650;
                break;
            case 72:
                lat = 365087990;
                lon = 1406207350;
                break;
            case 73:
                lat = 365094310;
                lon = 1406190460;
                break;
            case 74:
                lat = 365108960;
                lon = 1406197660;
                break;
            default:
                lat = 365108960;
                lon = 1406197660;
        }
        info.setLatitude(lat);
        info.setLongitude(lon);
        return info;
    }

    /**
     * 
     * 気象設定
     *
     * @param h 時刻
     * @return 気象
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    private String getWeather(int h) {
        // 気象
        String weather = "";
        switch (h % 5) {
            case 0:
                // 晴
                weather = "100";
                break;
            case 1:
                // 曇
                weather = "200";
                break;
            case 2:
                // 雨
                weather = "300";
                break;
            case 3:
                // 雪
                weather = "400";
                break;
            case 4:
                // みぞれ
                weather = "500";
                break;
            default:
                // 晴
                weather = "100";
        }
        return weather;
    }

    /**
     * 
     * 風向情報設定
     *
     * @param h
     * @return
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    private Double getWindDirection(int h) {
        // 風向
        Double windDirection;
        // 風向
        switch (h % 18) {
            case 0:
                // 清穂
                windDirection = 0.0;
                break;
            case 1:
                //北北東
                windDirection = 11.3;
                break;
            case 2:
                //北東
                windDirection = 33.8;
                break;
            case 3:
                //東北東
                windDirection = 56.3;
                break;
            case 4:
                //東
                windDirection = 78.8;
                break;
            case 5:
                //東南東
                windDirection = 101.3;
                break;
            case 6:
                //南東
                windDirection = 123.8;
                break;
            case 7:
                //南南東
                windDirection = 146.3;
                break;
            case 8:
                //南
                windDirection = 168.8;
                break;
            case 9:
                //南南西
                windDirection = 191.3;
                break;
            case 10:
                //南西
                windDirection = 213.8;
                break;
            case 11:
                //西南西
                windDirection = 236.3;
                break;
            case 12:
                //西
                windDirection = 258.8;
                break;
            case 13:
                //西北西
                windDirection = 281.3;
                break;
            case 14:
                //北西
                windDirection = 303.8;
                break;
            case 15:
                //北北西
                windDirection = 326.3;
                break;
            case 16:
                //北
                windDirection = 348.8;
                break;
            case 17:
                //北
                windDirection = 0.1;
                break;
            default:
                // 清穂
                windDirection = 0.0;
        }
        return windDirection;
    }

    private AliveMonitoringInfo getAliveMonitoringInfo(Integer roadsideUnitID) {
        AliveMonitoringInfo info = new AliveMonitoringInfo();
        Integer serviceAvailability = 0;
        switch (roadsideUnitID % 10) {
            case 2:
                serviceAvailability = 2;
                break;
            case 4:
                serviceAvailability = 2;
                break;
            default:
                serviceAvailability = 0;
        }
        info.setServiceAvailability(serviceAvailability);
        return info;
    }
}
