package jp.tier4.dataconversion.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

import jp.tier4.dataconversion.Application;
import jp.tier4.dataconversion.domain.model.fms.Location;
import jp.tier4.dataconversion.domain.model.fms.RetrieveAllVehicles;
import jp.tier4.dataconversion.domain.model.fms.Telemetry;
import jp.tier4.dataconversion.domain.model.fms.Vehicle;
import jp.tier4.dataconversion.service.impl.VehicleAllService;
import jp.tier4.dataconversion.service.impl.VehicleTelemetryService;

/**
 * 
 * 自動運転車両情報取得コントローラーテスト
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class VehicleControllerTest {

    @SpringJUnitConfig
    @SpringBootTest(classes = Application.class)
    @AutoConfigureMockMvc
    public static class controller_test {

        private MockMvc mockMvc;

        @InjectMocks
        VehiclesController target;

        @Mock
        private VehicleAllService vehicleAllService;

        @Mock
        private VehicleTelemetryService vehicleTelemetryService;

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
        @DisplayName("vehicles_正常系")
        void vehicles_test() throws Exception {

            // データ準備
            RetrieveAllVehicles mockValue = new RetrieveAllVehicles();
            mockValue.setTotal(5);

            List<Vehicle> vehicles = new ArrayList<Vehicle>();
            Vehicle vehicle = new Vehicle();
            vehicle.setProjectId("tier4");
            vehicle.setEnvironmentId("5b078865-ab8a-422d-aa11-1437907dae32");
            vehicle.setVehicleId("5b078865-ab8a-422d-aa11-1437907dae32");
            vehicle.setVehicleName("vehicle001_test");
            vehicle.setCanStart(false);
            vehicle.setAcceptableOrder("all");
            vehicle.setDescription("test");
            vehicle.setCreatedAt("2014-10-10T04:50:40.000001+00:00");
            vehicle.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");

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
            tele.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");
            vehicle.setTelemetry(tele);
            vehicles.add(vehicle);

            Vehicle vehicle2 = new Vehicle();
            vehicle2.setProjectId("tier4_2");
            vehicle2.setEnvironmentId("5b078865-ab8a-422d-aa11-1437907dae32_2");
            vehicle2.setVehicleId("5b078865-ab8a-422d-aa11-1437907dae32_2");
            vehicle2.setVehicleName("vehicle002_test");
            vehicle2.setCanStart(true);
            vehicle2.setAcceptableOrder("all");
            vehicle2.setDescription("test");
            vehicle2.setCreatedAt("2014-10-10T04:50:40.000001+00:00");
            vehicle2.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");

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
            tele2.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");
            vehicle2.setTelemetry(tele2);
            vehicles.add(vehicle2);

            mockValue.setVehicles(vehicles);

            // モックの動作を指定
            Mockito.when(vehicleAllService.service(any())).thenReturn(mockValue);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicles")
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            // モックの回数
            Mockito.verify(vehicleAllService, Mockito.times(1)).service(any());

            // 検証用ファイル取得
            String path = "/controller/Vehicles_200.json";
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
        @DisplayName("vehicles_異常系Nullチェック")
        void vehicles_test_forNull() throws Exception {

            // データ準備
            RetrieveAllVehicles mockValue = new RetrieveAllVehicles();
            mockValue.setTotal(5);
            // モックの動作を指定
            Mockito.when(vehicleAllService.service(any())).thenReturn(mockValue);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicles")
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            // モックの回数
            Mockito.verify(vehicleAllService, Mockito.times(1)).service(any());

            // 検証用ファイル取得
            String path = "/controller/Vehicles_200_forNull.json";
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
        @DisplayName("vehicle-telemetry_正常系")
        void vehicle_test() throws Exception {

            // データ準備
            Vehicle vehicle = new Vehicle();
            vehicle.setProjectId("tier4");
            vehicle.setEnvironmentId("5b078865-ab8a-422d-aa11-1437907dae32");
            vehicle.setVehicleId("5b078865-ab8a-422d-aa11-1437907dae32");
            vehicle.setVehicleName("vehicle001_test");
            vehicle.setCanStart(false);
            vehicle.setAcceptableOrder("all");
            vehicle.setDescription("test");
            vehicle.setCreatedAt("2014-10-10T04:50:40.000001+00:00");
            vehicle.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");

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
            tele.setHeading(50.5);
            tele.setUpdatedAt("2014-10-10T04:50:40.000001+00:00");
            vehicle.setTelemetry(tele);

            // モックの動作を指定
            Mockito.when(vehicleTelemetryService.service(any())).thenReturn(vehicle);

            String vehicleId = "vehicleId";
            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-telemetry")
                    .param("vehicle_id", vehicleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
            // リクエスト実行
            String actual = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            // モックの回数
            Mockito.verify(vehicleTelemetryService, Mockito.times(1)).service(any());

            // 検証用ファイル取得
            String path = "/controller/Vehicle_200.json";
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
    public static class vehicles_api_test {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private VehicleAllService vehicleAllService;

        @Autowired
        private VehicleTelemetryService vehicleTelemetryService;

        private String vehicleId = "vehicleId";

        @BeforeEach
        void setUp() {
            ReflectionTestUtils.setField(vehicleAllService, "authPrivateKey",
                    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUVoFRP6ZSajYv9+H33DbjSGMH9pDWA9ien7vJG9sm4rLoOg3JEmKX22BUVNJg24vO+5j9IoULJRT6HPyogcnXWROE8qt1VJzVQvrRM0WaIb5qIEe+MC7gphIa69oDyUKZNwqeUduG21vr8kzGXcIEX9QzsWLS9BMXp7jom4tykRY5mU/L0t+ucffw9QmEGI8iXUtgKWU1WPbpC31JqK49+G0b2Dmc/hKYwFuvSkMXFzT9A/KzoUaDJYAEMuVxCIYPdl4IRaPi5FJuf2S7ECvtnYeBhkUftZHWq4wwcMXjjrjvYHk+rkqD528FxSvQkPod1PDtTZLu6TpOgOuwwYh9AgMBAAECggEAAYSbDAeGyra760EDAowMSoKjWOslitl7MngCM9+VS1qP7ZMwRxK8GzvA8D1m+VRUa3OUACTrexCsEQDhFTRO7fR7rxRcA1KHLoPtUvN36erVGfddsFlPnX9avxs46xatVDjpk6fNeBZH91dweSJvwSclfDrwOxcniQovkzfNzZ6ogycqmMhzigfZxuqINrQ7I5JsIsfWrHd8z4HPNF2WDEZ3/tup8p49Mar304HUnibtlQTtMWE5idRioxcmPJ9/1akGaJpoABGu1A4YwO93upkofhtXEwQc78c3pWggb9s73PNm3umFL2SqVXTkfdjgr1+TG4FQF5VcDAAoe88MsQKBgQDLEARksXsbU6SoBdp6GPJrX20GZgxzdjZ1ZqM9ROECvUIL0I3L+HOKr8rvq+Uzs4nMFV1T9DzLCP0R3gqn/D+MW4jWRA8SWiA8Y7t4b6IoQO+bK8DCUIb8u0kNgu4ZEEyUvKhZLuGHKGKfW8qfV1zLDXjekPsw5K+s7eAwB/xa0QKBgQC7AkaT2yJbF9Yui7htaYIuXhHusEit16j3BdVVXfkPzkW0heVPLu+iB/YjGUn9StA6DB4x3XYbJDCA/FiDmTof2js0e1OC15TX6tTE2R4muNSkvzuh5Spj89Xm5lCuDi9CHsIBcENfBzem+1wfxgRrDduk3lYpuIXpkcpfsL1l7QKBgCU7GpMbt2abP2VPLW+Vg10McgDqVP4WfoWn++YP6vGFocZoxpbPRt/2u06WRb/k+y7f++yYq0zOqRfNjkaeiUhCwCQI9np269Imtwit7x1SSpw4uW7nNNjBvfMsPlt6EZBzxqoTXmZZkTuGqO/uJKVWIwMrseKVC2C5fJFR5YMBAoGBAKv5T8YwwstahFCRlKypVlolkAAchPm5VVy1NJYosR3j5x4388R5uU0cXTGx0+Tmo859zla0/iO/iAtWBGAgzN715XRB5W5xqiNVhQzxTVT2rDZE1iXvhKgeWBraul8WFEeN2YNRJeOB05/vj6x4gR+hwtc+z6XWVu+QbrbI5aORAoGASdviQ7xstczdRvIfAAo2ZLk8VrxyymmfmvSOhslhwkoMc3JkEZz4WRDcJL6STma/pkfz8wat3noQrRdmzk3Gt0ohRU7AlauR8N4Nm91bUNTZbVcH72nKsmu0tXxnlL46Fd8/BwkEywWpszJCDnHRtOBnxUasyb+68CCDiq4gcZc=",
                    String.class);
        }

        @AfterEach
        void closeMocks() throws Exception {
        }

        @Test
        @DisplayName("400エラー")
        void vehicles_400() throws Exception {

            ReflectionTestUtils.setField(vehicleAllService, "vehicleAllUrl", "/400", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicles")
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
        void vehicles_401() throws Exception {
            ReflectionTestUtils.setField(vehicleAllService, "vehicleAllUrl", "/401", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicles")
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
        void vehicles_500_tokenError() throws Exception {
            ReflectionTestUtils.setField(vehicleAllService, "authPrivateKey", "aaaa", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicles")
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
        void vehicles_500() throws Exception {
            ReflectionTestUtils.setField(vehicleAllService, "vehicleAllUrl", "/500", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicles")
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
        void vehicles_503() throws Exception {
            ReflectionTestUtils.setField(vehicleAllService, "vehicleAllUrl", "/503", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicles")
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

            ReflectionTestUtils.setField(vehicleTelemetryService, "vehicleTelemetryUrl", "/400", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-telemetry")
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
        void vehicle_401() throws Exception {
            ReflectionTestUtils.setField(vehicleTelemetryService, "vehicleTelemetryUrl", "/401", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-telemetry")
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
        void vehicle_500_tokenError() throws Exception {
            ReflectionTestUtils.setField(vehicleTelemetryService, "vehicleTelemetryUrl", "aaaa", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-telemetry")
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
        void vehicle_500() throws Exception {
            ReflectionTestUtils.setField(vehicleTelemetryService, "vehicleTelemetryUrl", "/500", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-telemetry")
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
        void vehicle_503() throws Exception {
            ReflectionTestUtils.setField(vehicleTelemetryService, "vehicleTelemetryUrl", "/503", String.class);

            // 実行＆結果検証
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/vehicle-telemetry")
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
    }
}
