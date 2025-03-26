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
import jp.tier4.dataconversion.domain.model.ScheduleGet;
import jp.tier4.dataconversion.domain.model.fms.Location;
import jp.tier4.dataconversion.domain.model.fms.Place;
import jp.tier4.dataconversion.domain.model.fms.RetrieveSchedule;
import jp.tier4.dataconversion.domain.model.fms.RetrieveSchedulelsVehicle;
import jp.tier4.dataconversion.domain.model.fms.Schedule;
import jp.tier4.dataconversion.domain.model.fms.ScheduleTask;
import jp.tier4.dataconversion.domain.model.fms.Tag;

/**
 * 
 * スケジュール情報取得サービステスト
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringJUnitConfig
@SpringBootTest(classes = Application.class)
public class ScheduleGetServiceTest {

    @Autowired
    private ScheduleGetService service;

    @Test
    void test_service() throws Exception {
        ReflectionTestUtils.setField(service, "scheduleUrl",
                "/projects/{project_id}/environments/{environment_id}/schedules/{schedule_id}", String.class);
        ReflectionTestUtils.setField(service, "authPrivateKey",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUVoFRP6ZSajYv9+H33DbjSGMH9pDWA9ien7vJG9sm4rLoOg3JEmKX22BUVNJg24vO+5j9IoULJRT6HPyogcnXWROE8qt1VJzVQvrRM0WaIb5qIEe+MC7gphIa69oDyUKZNwqeUduG21vr8kzGXcIEX9QzsWLS9BMXp7jom4tykRY5mU/L0t+ucffw9QmEGI8iXUtgKWU1WPbpC31JqK49+G0b2Dmc/hKYwFuvSkMXFzT9A/KzoUaDJYAEMuVxCIYPdl4IRaPi5FJuf2S7ECvtnYeBhkUftZHWq4wwcMXjjrjvYHk+rkqD528FxSvQkPod1PDtTZLu6TpOgOuwwYh9AgMBAAECggEAAYSbDAeGyra760EDAowMSoKjWOslitl7MngCM9+VS1qP7ZMwRxK8GzvA8D1m+VRUa3OUACTrexCsEQDhFTRO7fR7rxRcA1KHLoPtUvN36erVGfddsFlPnX9avxs46xatVDjpk6fNeBZH91dweSJvwSclfDrwOxcniQovkzfNzZ6ogycqmMhzigfZxuqINrQ7I5JsIsfWrHd8z4HPNF2WDEZ3/tup8p49Mar304HUnibtlQTtMWE5idRioxcmPJ9/1akGaJpoABGu1A4YwO93upkofhtXEwQc78c3pWggb9s73PNm3umFL2SqVXTkfdjgr1+TG4FQF5VcDAAoe88MsQKBgQDLEARksXsbU6SoBdp6GPJrX20GZgxzdjZ1ZqM9ROECvUIL0I3L+HOKr8rvq+Uzs4nMFV1T9DzLCP0R3gqn/D+MW4jWRA8SWiA8Y7t4b6IoQO+bK8DCUIb8u0kNgu4ZEEyUvKhZLuGHKGKfW8qfV1zLDXjekPsw5K+s7eAwB/xa0QKBgQC7AkaT2yJbF9Yui7htaYIuXhHusEit16j3BdVVXfkPzkW0heVPLu+iB/YjGUn9StA6DB4x3XYbJDCA/FiDmTof2js0e1OC15TX6tTE2R4muNSkvzuh5Spj89Xm5lCuDi9CHsIBcENfBzem+1wfxgRrDduk3lYpuIXpkcpfsL1l7QKBgCU7GpMbt2abP2VPLW+Vg10McgDqVP4WfoWn++YP6vGFocZoxpbPRt/2u06WRb/k+y7f++yYq0zOqRfNjkaeiUhCwCQI9np269Imtwit7x1SSpw4uW7nNNjBvfMsPlt6EZBzxqoTXmZZkTuGqO/uJKVWIwMrseKVC2C5fJFR5YMBAoGBAKv5T8YwwstahFCRlKypVlolkAAchPm5VVy1NJYosR3j5x4388R5uU0cXTGx0+Tmo859zla0/iO/iAtWBGAgzN715XRB5W5xqiNVhQzxTVT2rDZE1iXvhKgeWBraul8WFEeN2YNRJeOB05/vj6x4gR+hwtc+z6XWVu+QbrbI5aORAoGASdviQ7xstczdRvIfAAo2ZLk8VrxyymmfmvSOhslhwkoMc3JkEZz4WRDcJL6STma/pkfz8wat3noQrRdmzk3Gt0ohRU7AlauR8N4Nm91bUNTZbVcH72nKsmu0tXxnlL46Fd8/BwkEywWpszJCDnHRtOBnxUasyb+68CCDiq4gcZc=",
                String.class);

        // 検証用変数
        ScheduleGet expected = new ScheduleGet();
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
        expected.setVehicleSchedule(retrieveSchedulelsVehicle);

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
        expected.setScheduleTask(retrieveSchedule);

        // テスト対象実行
        ScheduleGet result = service.service("vehicleId");
        // 検証
        for (int i = 0; i < expected.getVehicleSchedule().getSchedules().size(); i++) {
            vehicleScheduleAssert(result.getVehicleSchedule().getSchedules().get(i),
                    expected.getVehicleSchedule().getSchedules().get(i));
        }
        vehicleScheduleAssert(result.getScheduleTask(), expected.getScheduleTask());

        for (int i = 0; i < expected.getScheduleTask().getTasks().size(); i++) {
            taskAssert(result.getScheduleTask().getTasks().get(i), expected.getScheduleTask().getTasks().get(i));
        }
    }

    @Test
    void test_service_firstError() throws Exception {
        ReflectionTestUtils.setField(service, "authPrivateKey",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUVoFRP6ZSajYv9+H33DbjSGMH9pDWA9ien7vJG9sm4rLoOg3JEmKX22BUVNJg24vO+5j9IoULJRT6HPyogcnXWROE8qt1VJzVQvrRM0WaIb5qIEe+MC7gphIa69oDyUKZNwqeUduG21vr8kzGXcIEX9QzsWLS9BMXp7jom4tykRY5mU/L0t+ucffw9QmEGI8iXUtgKWU1WPbpC31JqK49+G0b2Dmc/hKYwFuvSkMXFzT9A/KzoUaDJYAEMuVxCIYPdl4IRaPi5FJuf2S7ECvtnYeBhkUftZHWq4wwcMXjjrjvYHk+rkqD528FxSvQkPod1PDtTZLu6TpOgOuwwYh9AgMBAAECggEAAYSbDAeGyra760EDAowMSoKjWOslitl7MngCM9+VS1qP7ZMwRxK8GzvA8D1m+VRUa3OUACTrexCsEQDhFTRO7fR7rxRcA1KHLoPtUvN36erVGfddsFlPnX9avxs46xatVDjpk6fNeBZH91dweSJvwSclfDrwOxcniQovkzfNzZ6ogycqmMhzigfZxuqINrQ7I5JsIsfWrHd8z4HPNF2WDEZ3/tup8p49Mar304HUnibtlQTtMWE5idRioxcmPJ9/1akGaJpoABGu1A4YwO93upkofhtXEwQc78c3pWggb9s73PNm3umFL2SqVXTkfdjgr1+TG4FQF5VcDAAoe88MsQKBgQDLEARksXsbU6SoBdp6GPJrX20GZgxzdjZ1ZqM9ROECvUIL0I3L+HOKr8rvq+Uzs4nMFV1T9DzLCP0R3gqn/D+MW4jWRA8SWiA8Y7t4b6IoQO+bK8DCUIb8u0kNgu4ZEEyUvKhZLuGHKGKfW8qfV1zLDXjekPsw5K+s7eAwB/xa0QKBgQC7AkaT2yJbF9Yui7htaYIuXhHusEit16j3BdVVXfkPzkW0heVPLu+iB/YjGUn9StA6DB4x3XYbJDCA/FiDmTof2js0e1OC15TX6tTE2R4muNSkvzuh5Spj89Xm5lCuDi9CHsIBcENfBzem+1wfxgRrDduk3lYpuIXpkcpfsL1l7QKBgCU7GpMbt2abP2VPLW+Vg10McgDqVP4WfoWn++YP6vGFocZoxpbPRt/2u06WRb/k+y7f++yYq0zOqRfNjkaeiUhCwCQI9np269Imtwit7x1SSpw4uW7nNNjBvfMsPlt6EZBzxqoTXmZZkTuGqO/uJKVWIwMrseKVC2C5fJFR5YMBAoGBAKv5T8YwwstahFCRlKypVlolkAAchPm5VVy1NJYosR3j5x4388R5uU0cXTGx0+Tmo859zla0/iO/iAtWBGAgzN715XRB5W5xqiNVhQzxTVT2rDZE1iXvhKgeWBraul8WFEeN2YNRJeOB05/vj6x4gR+hwtc+z6XWVu+QbrbI5aORAoGASdviQ7xstczdRvIfAAo2ZLk8VrxyymmfmvSOhslhwkoMc3JkEZz4WRDcJL6STma/pkfz8wat3noQrRdmzk3Gt0ohRU7AlauR8N4Nm91bUNTZbVcH72nKsmu0tXxnlL46Fd8/BwkEywWpszJCDnHRtOBnxUasyb+68CCDiq4gcZc=",
                String.class);

        ReflectionTestUtils.setField(service, "scheduleUrl", "/schedules/firstError", String.class);

        // 検証用変数
        ScheduleGet expected = new ScheduleGet();
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
        expected.setVehicleSchedule(retrieveSchedulelsVehicle);

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
        expected.setScheduleTask(retrieveSchedule);

        // テスト対象実行
        ScheduleGet result = service.service("vehicleId");
        // 検証
        for (int i = 0; i < expected.getVehicleSchedule().getSchedules().size(); i++) {
            vehicleScheduleAssert(result.getVehicleSchedule().getSchedules().get(i),
                    expected.getVehicleSchedule().getSchedules().get(i));
        }
        vehicleScheduleAssert(result.getScheduleTask(), expected.getScheduleTask());

        for (int i = 0; i < expected.getScheduleTask().getTasks().size(); i++) {
            taskAssert(result.getScheduleTask().getTasks().get(i), expected.getScheduleTask().getTasks().get(i));
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
