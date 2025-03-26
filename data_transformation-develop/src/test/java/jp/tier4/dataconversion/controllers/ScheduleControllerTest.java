package jp.tier4.dataconversion.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.tier4.dataconversion.Application;
import jp.tier4.dataconversion.constants.Constants;
import jp.tier4.dataconversion.domain.model.BaseModel;
import jp.tier4.dataconversion.domain.model.ScheduleGet;
import jp.tier4.dataconversion.domain.model.SchedulePutDataModel;
import jp.tier4.dataconversion.domain.model.fms.Location;
import jp.tier4.dataconversion.domain.model.fms.Place;
import jp.tier4.dataconversion.domain.model.fms.RegisterScheduleVehicleResult;
import jp.tier4.dataconversion.domain.model.fms.RetrieveSchedule;
import jp.tier4.dataconversion.domain.model.fms.RetrieveSchedulelsVehicle;
import jp.tier4.dataconversion.domain.model.fms.Schedule;
import jp.tier4.dataconversion.domain.model.fms.ScheduleTask;
import jp.tier4.dataconversion.domain.model.fms.Tag;
import jp.tier4.dataconversion.service.impl.ScheduleGetService;
import jp.tier4.dataconversion.service.impl.ScheduleRegisterService;

/**
 * 
 * スケジュールコントローラーテスト
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class ScheduleControllerTest {

    @SpringJUnitConfig
    @SpringBootTest(classes = Application.class)
    @AutoConfigureMockMvc
    public static class controller_test {

        private MockMvc mockMvc;

        @InjectMocks
        ScheduleController target;

        @Mock
        private ScheduleGetService scheduleGetService;

        @Mock
        private ScheduleRegisterService scheduleRegisterService;

        private AutoCloseable closeable;

        @BeforeEach
        void setUp() {
            closeable = MockitoAnnotations.openMocks(target);
            mockMvc = MockMvcBuilders.standaloneSetup(target).build();
        }

        @AfterEach
        void closeMocks() throws Exception {
            closeable.close();
        }

        @Test
        @DisplayName("getSchedule_正常系")
        void getSchedule_test() throws Exception {

            // データ準備
            ScheduleGet mockValue = new ScheduleGet();
            // 自動運転車両スケジュール情報取得モデル
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
                    tag.setKey("userId" + j);
                    tag.setValue("U13" + j);
                    tags.add(tag);
                }
                schedule.setTags(tags);
                schedule.setCreatedAt("2014-10-05T04:50:40.000001+00:00");
                schedule.setUpdatedAt("2014-10-06T04:50:40.000001+00:00");
                schedules.add(schedule);
            }
            retrieveSchedulelsVehicle.setSchedules(schedules);
            mockValue.setVehicleSchedule(retrieveSchedulelsVehicle);

            // スケジュール情報取得モデル
            RetrieveSchedule retrieveSchedule = new RetrieveSchedule();
            List<ScheduleTask> scheduleTasks = new ArrayList<ScheduleTask>();

            retrieveSchedule.setScheduleId("03486544-ab8b-4225-cc34-3457608vby560");
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
                tag.setValue("U14" + i);
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
                originLocation.setLat(35.624268125444 + i);
                originLocation.setLng(139.7425864098 + i);
                origin.setLocation(originLocation);
                tasks.setOrigin(origin);

                Place destination = new Place();
                destination.setPointId(2);
                destination.setName("SecondBusStop" + i);
                Location destinationLocation = new Location();
                destinationLocation.setLat(55.624268125444 + i);
                destinationLocation.setLng(150.7425864098 + i);
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
            mockValue.setScheduleTask(retrieveSchedule);

            // モックの動作を指定
            String vehicleId = "vehicleId";
            Mockito.when(scheduleGetService.service(any())).thenReturn(mockValue);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-schedules")
                    .param("vehicle_id", vehicleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            // モックの回数
            Mockito.verify(scheduleGetService, Mockito.times(1)).service(any());

            // 検証用ファイル取得
            String path = "/controller/VehicleSchedule_200.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("registSchedule_正常系")
        void registSchedule_test() throws Exception {

            // データ準備
            BaseModel<SchedulePutDataModel> putParam = new BaseModel<SchedulePutDataModel>();
            String vehicleId = "vehicleId";
            SchedulePutDataModel param = new SchedulePutDataModel();
            param.setVehicleId(vehicleId);
            param.setDestinationPoints(Arrays.asList(1, 2, 3));
            putParam.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
            putParam.setAttribute(param);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(putParam);

            RegisterScheduleVehicleResult result = new RegisterScheduleVehicleResult();

            // モックの動作を指定
            Mockito.when(scheduleRegisterService.service(any())).thenReturn(result);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/vehicle-schedule")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            // モックの回数
            Mockito.verify(scheduleRegisterService, Mockito.times(1)).service(any());

            // 検証用ファイル取得
            String path = "/controller/VehicleScheduleRegist_200.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }
    };

    @SpringJUnitConfig
    @SpringBootTest(classes = Application.class)
    @AutoConfigureMockMvc
    public static class vehicleSchedules_api_test {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ScheduleGetService scheduleGetService;

        @Autowired
        private ScheduleRegisterService scheduleRegisterService;

        private String vehicleId = "vehicleId";

        @BeforeEach
        void setUp() {
            ReflectionTestUtils.setField(scheduleGetService, "authPrivateKey",
                    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUVoFRP6ZSajYv9+H33DbjSGMH9pDWA9ien7vJG9sm4rLoOg3JEmKX22BUVNJg24vO+5j9IoULJRT6HPyogcnXWROE8qt1VJzVQvrRM0WaIb5qIEe+MC7gphIa69oDyUKZNwqeUduG21vr8kzGXcIEX9QzsWLS9BMXp7jom4tykRY5mU/L0t+ucffw9QmEGI8iXUtgKWU1WPbpC31JqK49+G0b2Dmc/hKYwFuvSkMXFzT9A/KzoUaDJYAEMuVxCIYPdl4IRaPi5FJuf2S7ECvtnYeBhkUftZHWq4wwcMXjjrjvYHk+rkqD528FxSvQkPod1PDtTZLu6TpOgOuwwYh9AgMBAAECggEAAYSbDAeGyra760EDAowMSoKjWOslitl7MngCM9+VS1qP7ZMwRxK8GzvA8D1m+VRUa3OUACTrexCsEQDhFTRO7fR7rxRcA1KHLoPtUvN36erVGfddsFlPnX9avxs46xatVDjpk6fNeBZH91dweSJvwSclfDrwOxcniQovkzfNzZ6ogycqmMhzigfZxuqINrQ7I5JsIsfWrHd8z4HPNF2WDEZ3/tup8p49Mar304HUnibtlQTtMWE5idRioxcmPJ9/1akGaJpoABGu1A4YwO93upkofhtXEwQc78c3pWggb9s73PNm3umFL2SqVXTkfdjgr1+TG4FQF5VcDAAoe88MsQKBgQDLEARksXsbU6SoBdp6GPJrX20GZgxzdjZ1ZqM9ROECvUIL0I3L+HOKr8rvq+Uzs4nMFV1T9DzLCP0R3gqn/D+MW4jWRA8SWiA8Y7t4b6IoQO+bK8DCUIb8u0kNgu4ZEEyUvKhZLuGHKGKfW8qfV1zLDXjekPsw5K+s7eAwB/xa0QKBgQC7AkaT2yJbF9Yui7htaYIuXhHusEit16j3BdVVXfkPzkW0heVPLu+iB/YjGUn9StA6DB4x3XYbJDCA/FiDmTof2js0e1OC15TX6tTE2R4muNSkvzuh5Spj89Xm5lCuDi9CHsIBcENfBzem+1wfxgRrDduk3lYpuIXpkcpfsL1l7QKBgCU7GpMbt2abP2VPLW+Vg10McgDqVP4WfoWn++YP6vGFocZoxpbPRt/2u06WRb/k+y7f++yYq0zOqRfNjkaeiUhCwCQI9np269Imtwit7x1SSpw4uW7nNNjBvfMsPlt6EZBzxqoTXmZZkTuGqO/uJKVWIwMrseKVC2C5fJFR5YMBAoGBAKv5T8YwwstahFCRlKypVlolkAAchPm5VVy1NJYosR3j5x4388R5uU0cXTGx0+Tmo859zla0/iO/iAtWBGAgzN715XRB5W5xqiNVhQzxTVT2rDZE1iXvhKgeWBraul8WFEeN2YNRJeOB05/vj6x4gR+hwtc+z6XWVu+QbrbI5aORAoGASdviQ7xstczdRvIfAAo2ZLk8VrxyymmfmvSOhslhwkoMc3JkEZz4WRDcJL6STma/pkfz8wat3noQrRdmzk3Gt0ohRU7AlauR8N4Nm91bUNTZbVcH72nKsmu0tXxnlL46Fd8/BwkEywWpszJCDnHRtOBnxUasyb+68CCDiq4gcZc=",
                    String.class);
        }

        @AfterEach
        void closeMocks() throws Exception {
        }

        @Test
        @DisplayName("400エラー")
        void vehicleSchedules_400() throws Exception {

            ReflectionTestUtils.setField(scheduleGetService, "vehicleScheduleUrl", "/400", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-schedules")
                    .param("vehicle_id", vehicleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_400.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("401エラー")
        void vehicleSchedules_401() throws Exception {
            ReflectionTestUtils.setField(scheduleGetService, "vehicleScheduleUrl", "/401", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-schedules")
                    .param("vehicle_id", vehicleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_401.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("500エラー")
        void vehicleSchedules_500_tokenError() throws Exception {
            ReflectionTestUtils.setField(scheduleGetService, "authPrivateKey", "aaaa", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-schedules")
                    .param("vehicle_id", vehicleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_500.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("500エラー")
        void vehicleSchedules_500() throws Exception {
            ReflectionTestUtils.setField(scheduleGetService, "vehicleScheduleUrl", "/500", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-schedules")
                    .param("vehicle_id", vehicleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_500.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("503エラー")
        void vehicleSchedules_503() throws Exception {
            ReflectionTestUtils.setField(scheduleGetService, "vehicleScheduleUrl", "/503", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-schedules")
                    .param("vehicle_id", vehicleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_503.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("400エラー")
        void vehicle_400() throws Exception {

            ReflectionTestUtils.setField(scheduleRegisterService, "scheduleRegisterAllUrl", "/400", String.class);

            BaseModel<SchedulePutDataModel> putParam = new BaseModel<SchedulePutDataModel>();
            String vehicleId = "vehicleId";
            SchedulePutDataModel param = new SchedulePutDataModel();
            param.setVehicleId(vehicleId);
            param.setDestinationPoints(Arrays.asList(1, 2, 3));
            putParam.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
            putParam.setAttribute(param);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(putParam);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/vehicle-schedule")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_400.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("401エラー")
        void vehicle_401() throws Exception {
            ReflectionTestUtils.setField(scheduleRegisterService, "scheduleRegisterAllUrl", "/401", String.class);

            BaseModel<SchedulePutDataModel> putParam = new BaseModel<SchedulePutDataModel>();
            String vehicleId = "vehicleId";
            SchedulePutDataModel param = new SchedulePutDataModel();
            param.setVehicleId(vehicleId);
            param.setDestinationPoints(Arrays.asList(1, 2, 3));
            putParam.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
            putParam.setAttribute(param);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(putParam);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/vehicle-schedule")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_401.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("500エラー")
        void vehicle_500_tokenError() throws Exception {
            ReflectionTestUtils.setField(scheduleRegisterService, "scheduleRegisterAllUrl", "aaaa", String.class);

            BaseModel<SchedulePutDataModel> putParam = new BaseModel<SchedulePutDataModel>();
            String vehicleId = "vehicleId";
            SchedulePutDataModel param = new SchedulePutDataModel();
            param.setVehicleId(vehicleId);
            param.setDestinationPoints(Arrays.asList(1, 2, 3));
            putParam.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
            putParam.setAttribute(param);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(putParam);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/vehicle-schedule")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_500.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("500エラー")
        void vehicle_500() throws Exception {
            ReflectionTestUtils.setField(scheduleRegisterService, "scheduleRegisterAllUrl", "/500", String.class);

            BaseModel<SchedulePutDataModel> putParam = new BaseModel<SchedulePutDataModel>();
            String vehicleId = "vehicleId";
            SchedulePutDataModel param = new SchedulePutDataModel();
            param.setVehicleId(vehicleId);
            param.setDestinationPoints(Arrays.asList(1, 2, 3));
            putParam.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
            putParam.setAttribute(param);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(putParam);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/vehicle-schedule")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_500.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("503エラー")
        void vehicle_503() throws Exception {
            ReflectionTestUtils.setField(scheduleRegisterService, "scheduleRegisterAllUrl", "/503", String.class);

            BaseModel<SchedulePutDataModel> putParam = new BaseModel<SchedulePutDataModel>();
            String vehicleId = "vehicleId";
            SchedulePutDataModel param = new SchedulePutDataModel();
            param.setVehicleId(vehicleId);
            param.setDestinationPoints(Arrays.asList(1, 2, 3));
            putParam.setDataModelType(Constants.DATA_MODEL_TYPE_TEST1);
            putParam.setAttribute(param);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(putParam);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/vehicle-schedule")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isServiceUnavailable())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            // 検証用ファイル取得
            String path = "/controller/Common_503.json";
            String expected = "";
            try (InputStream is = getClass().getResourceAsStream(path);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                // 一行ごとに読み込み
                String str = null;
                while ((str = br.readLine()) != null) {
                    expected += str;
                }

            } catch (IOException e) {
                // エラー発生時は明示的にエラーとする
                assertEquals(true, false);
            }

            // 検証
            assertEquals(expected, actual);
        }
    }
}
