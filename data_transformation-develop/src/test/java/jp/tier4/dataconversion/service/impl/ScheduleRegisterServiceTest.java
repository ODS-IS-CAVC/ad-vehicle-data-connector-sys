package jp.tier4.dataconversion.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

import jp.tier4.dataconversion.Application;
import jp.tier4.dataconversion.constants.Constants;
import jp.tier4.dataconversion.domain.model.RegisterScheduleVehicleServiceParam;
import jp.tier4.dataconversion.domain.model.fms.Location;
import jp.tier4.dataconversion.domain.model.fms.Place;
import jp.tier4.dataconversion.domain.model.fms.RegisterScheduleVehicleResult;
import jp.tier4.dataconversion.domain.model.fms.Schedule;
import jp.tier4.dataconversion.domain.model.fms.ScheduleTask;
import jp.tier4.dataconversion.domain.model.fms.ScheduleTaskRegist;
import jp.tier4.dataconversion.domain.model.fms.Tag;

/**
 * 
 * 自動運転車両スケジュール登録サービステスト
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringJUnitConfig
@SpringBootTest(classes = Application.class)
public class ScheduleRegisterServiceTest {

    @Autowired
    private ScheduleRegisterService service;

    @Test
    void test_service() throws Exception {
        ReflectionTestUtils.setField(service, "authPrivateKey",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDq99X07HizstlmIaPn+G3iCOar+OjioQLHItpPPmV78aZT2zB838JCOsrJpcHB9lVu0zzgSZfXVjEcQ/8vb6RsYEFE/01bKhiV2s2CUYdWkeGPuvvMLlDZDyi/LdWOXwj4l1FOW9O8y7ZW2HhA+UHrxK9w+QADdMimn8l2R2S5j0OoLYFm2r/kTGltf6fENt3Tw1gYW567xWVrSmeHvNDZTf0XO01dDEYyK7y9DLk6nWB985tHi6v+WqsKAIUr6pDmR5ZeSa6vq3StUtKm9gi1bBlgIFBnyOUrf8QTbABc3PohC5hlW0sM5h4UGBkcWydVgfvhBC9+MsByiQFf7wp5AgMBAAECggEAA1dq74S9YrDUbqNj1VSvG1vNC/0KMs7fXz2bpSzZ/J0/JEolsmDsPkzfvWZiDwQVyvGuF/Jz+lf7cs90vZP03qWvx+9phjZfCyKFcaj/J7gDYSvjkM/Hzo5soetg8hlf40kjmt2rcS/kc++4JhRlX5j4db7UxTfP8q6rCseRIyJjdhbSV25TGRZs1E84EAhltwUABus/rNmT79k3f/kKKWA+NVLG/vXUKHio6T8OAcI84oA4JwTgt7zcl+RE8bpBbnVS0uPYY9C0IcG6sODwHvqpoRVqd/BzYTX6pmGPOwXY7yt8kUQnSQWLRjH+a0hrpl4Ph3wnhtRbOiSNaT5wEQKBgQD3BgHKDAZjOqbwnEV0+XmaNa6eNLtSGgAEy0eoMufsUdqxAOPm0Skj4EBjdd0t7yI4F1S/kps9YVfvU3gpQa2aOKuGz3ESbmO5WDT4+bd15Y5dzBbnDQhECWo5BpiU79NlaYXouv8aAh1clujTlV058FEnC7U5UpguNpggQ/3/+wKBgQDzga5YPy3DASAATnefxR6RyUigpBmLOz8eT3ziKdVntgjWGESif5Mrk54PJAlLWJkyFhx7OrL5+xDs8rmSTVeQTUPPxiW30OlBzF1z4PxgS0GMZ0B4XT8rHVDpcMBMgISmBFEFKIOX/5G9Km7UAl3O9tmDGp093aCZPRmne5IxGwKBgQDlRTsF53FKShbFvZUtw5rcyTRf/DtcMWuRB4djkpP1n+dlX5knEzym4q2AYtW9m3TcMLNk91KEfsPaClO9nwrc1/pDrmeZHsIKNdc8urFPiJdBcXpUSqFAXKitcvTNT2aaen1iC6VX41yTIjHIp5oWe3kmz1QNdx5tzhgV4mVuXwKBgAC5ew5SKc2xvCmkEiEMkhm28ptfwZ+zbm2lqOZ3X2YumXohmlqtbH+tscOzxL9PpS0L+cu4Ef5Ca/t4gWrZBhCs2i3NFiGk4nzKoeCNY9LZhnlneT6icGStcqcvKbNQkpR34NHWHAYgwYtBjBDfbO4Ht//23U7o0OiD0Qa5jGbPAoGBAMEh8Uyolh5fdhdFS/MlzmJv7gnCrobhTKLyFspWxAA/tCgiIGjArhpj1bTemJbyUX435PJ+dA4+4SHnZYhC6DAVX6RhadiR7eKP13cAcMLSNONYvy/Kk9cxWjiM3UDo5gGxbDMgflKRCkPzpkwlczyjv5lFBsSa4OjwmPq3uQa3",
                String.class);

        // パラメータ
        RegisterScheduleVehicleServiceParam param = new RegisterScheduleVehicleServiceParam();
        param.setVehicleId("vehicleId");
        List<ScheduleTaskRegist> paramTasks = new ArrayList<ScheduleTaskRegist>();
        ScheduleTaskRegist paramTask = new ScheduleTaskRegist();
        paramTask.setTaskType(Constants.FMS_REGIST_SCHEDULE_TASK_TYPE_MOVE);
        paramTask.setDestinationPoint(100);
        paramTasks.add(paramTask);
        paramTask = new ScheduleTaskRegist();
        paramTask.setTaskType(Constants.FMS_REGIST_SCHEDULE_TASK_TYPE_MOVE);
        paramTask.setDestinationPoint(200);
        paramTasks.add(paramTask);

        param.setTasks(paramTasks);

        // 検証用変数
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

        // テスト対象実行
        RegisterScheduleVehicleResult result = service.service(param);
        // 検証

        vehicleScheduleAssert(result, expected);

        for (int i = 0; i < expected.getTasks().size(); i++) {
            taskAssert(result.getTasks().get(i), expected.getTasks().get(i));
        }
    }

    @Test
    void test_service_exception() {
        ReflectionTestUtils.setField(service, "authPrivateKey", "aaa", String.class);

        // テスト対象実行
        try {
            service.service(null);
            // Exception発生予定のためこちらの分岐に入った場合エラーとする
            assertEquals(false, true);
        } catch (NoSuchAlgorithmException | IllegalArgumentException | InvalidKeySpecException | IOException e) {
            // 例外発生していたら正とする
            assertEquals(true, true);
        }
    }

    /**
     * 車両情報を検証する
     * 
     * @param act 実績値
     * @param exp 検証値
     */
    private void vehicleScheduleAssert(Schedule act, Schedule exp) {

        assertThat(act.getScheduleId()).isEqualTo(exp.getScheduleId());
        assertThat(act.getProjectId()).isEqualTo(exp.getProjectId());
        assertThat(act.getEnvironmentId()).isEqualTo(exp.getEnvironmentId());
        assertThat(act.getScheduleType()).isEqualTo(exp.getScheduleType());
        assertThat(act.isReserved()).isEqualTo(exp.isReserved());
        assertThat(act.getVehicleId()).isEqualTo(exp.getVehicleId());
        assertThat(act.getStatus()).isEqualTo(exp.getStatus());
        assertThat(act.getPlanStartTime()).isEqualTo(exp.getPlanStartTime());
        assertThat(act.getPlanEndTime()).isEqualTo(exp.getPlanEndTime());
        assertThat(act.getActualStartTime()).isEqualTo(exp.getActualStartTime());
        assertThat(act.getActualEndTime()).isEqualTo(exp.getActualEndTime());
        assertThat(act.getDurationSec()).isEqualTo(exp.getDurationSec());
        assertThat(act.getDeltaSec()).isEqualTo(exp.getDeltaSec());

        for (int i = 0; i < act.getTags().size(); i++) {
            tagAssert(act.getTags().get(i), exp.getTags().get(i));
        }

        assertThat(act.getCreatedAt()).isEqualTo(exp.getCreatedAt());
        assertThat(act.getUpdatedAt()).isEqualTo(exp.getUpdatedAt());
    }

    /**
     * 車両情報を検証する
     * 
     * @param act 実績値
     * @param exp 検証値
     */
    private void tagAssert(Tag act, Tag exp) {
        assertThat(act.getKey()).isEqualTo(exp.getKey());
        assertThat(act.getValue()).isEqualTo(exp.getValue());
    }

    /**
     * 車両情報を検証する
     * 
     * @param act 実績値
     * @param exp 検証値
     */
    private void taskAssert(ScheduleTask act, ScheduleTask exp) {
        assertThat(act.getTaskId()).isEqualTo(exp.getTaskId());
        assertThat(act.getTaskType()).isEqualTo(exp.getTaskType());
        assertThat(act.isReserved()).isEqualTo(exp.isReserved());
        assertThat(act.getStatus()).isEqualTo(exp.getStatus());

        assertThat(act.getOrigin().getPointId()).isEqualTo(exp.getOrigin().getPointId());
        assertThat(act.getOrigin().getName()).isEqualTo(exp.getOrigin().getName());
        assertThat(act.getOrigin().getLocation().getLat()).isEqualTo(exp.getOrigin().getLocation().getLat());
        assertThat(act.getOrigin().getLocation().getLng()).isEqualTo(exp.getOrigin().getLocation().getLng());
        assertThat(act.getOrigin().getPointId()).isEqualTo(exp.getOrigin().getPointId());

        assertThat(act.getDestination().getPointId()).isEqualTo(exp.getDestination().getPointId());
        assertThat(act.getDestination().getName()).isEqualTo(exp.getDestination().getName());
        assertThat(act.getDestination().getLocation().getLat()).isEqualTo(exp.getDestination().getLocation().getLat());
        assertThat(act.getDestination().getLocation().getLng()).isEqualTo(exp.getDestination().getLocation().getLng());
        assertThat(act.getDestination().getPointId()).isEqualTo(exp.getDestination().getPointId());

        for (int i = 0; i < act.getRouteIds().size(); i++) {
            assertThat(act.getRouteIds().get(i)).isEqualTo(exp.getRouteIds().get(i));
        }
        assertThat(act.getPlanStartTime()).isEqualTo(exp.getPlanStartTime());
        assertThat(act.getPlanEndTime()).isEqualTo(exp.getPlanEndTime());
        assertThat(act.getActualStartTime()).isEqualTo(exp.getActualStartTime());
        assertThat(act.getActualEndTime()).isEqualTo(exp.getActualEndTime());
        assertThat(act.getDurationSec()).isEqualTo(exp.getDurationSec());
        assertThat(act.getDescription()).isEqualTo(exp.getDescription());
    }
}
