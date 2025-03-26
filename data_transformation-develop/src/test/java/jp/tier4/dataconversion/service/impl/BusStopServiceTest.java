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
import jp.tier4.dataconversion.domain.model.fms.Location;
import jp.tier4.dataconversion.domain.model.fms.Place;
import jp.tier4.dataconversion.domain.model.fms.RetrieveAllPlaces;

/**
 * 
 * 乗降地（バス停）の情報取得サービステスト
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringJUnitConfig
@SpringBootTest(classes = Application.class)
public class BusStopServiceTest {

    @Autowired
    private BusStopService service;

    @Test
    void test_service() throws Exception {
        ReflectionTestUtils.setField(service, "authPrivateKey",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUVoFRP6ZSajYv9+H33DbjSGMH9pDWA9ien7vJG9sm4rLoOg3JEmKX22BUVNJg24vO+5j9IoULJRT6HPyogcnXWROE8qt1VJzVQvrRM0WaIb5qIEe+MC7gphIa69oDyUKZNwqeUduG21vr8kzGXcIEX9QzsWLS9BMXp7jom4tykRY5mU/L0t+ucffw9QmEGI8iXUtgKWU1WPbpC31JqK49+G0b2Dmc/hKYwFuvSkMXFzT9A/KzoUaDJYAEMuVxCIYPdl4IRaPi5FJuf2S7ECvtnYeBhkUftZHWq4wwcMXjjrjvYHk+rkqD528FxSvQkPod1PDtTZLu6TpOgOuwwYh9AgMBAAECggEAAYSbDAeGyra760EDAowMSoKjWOslitl7MngCM9+VS1qP7ZMwRxK8GzvA8D1m+VRUa3OUACTrexCsEQDhFTRO7fR7rxRcA1KHLoPtUvN36erVGfddsFlPnX9avxs46xatVDjpk6fNeBZH91dweSJvwSclfDrwOxcniQovkzfNzZ6ogycqmMhzigfZxuqINrQ7I5JsIsfWrHd8z4HPNF2WDEZ3/tup8p49Mar304HUnibtlQTtMWE5idRioxcmPJ9/1akGaJpoABGu1A4YwO93upkofhtXEwQc78c3pWggb9s73PNm3umFL2SqVXTkfdjgr1+TG4FQF5VcDAAoe88MsQKBgQDLEARksXsbU6SoBdp6GPJrX20GZgxzdjZ1ZqM9ROECvUIL0I3L+HOKr8rvq+Uzs4nMFV1T9DzLCP0R3gqn/D+MW4jWRA8SWiA8Y7t4b6IoQO+bK8DCUIb8u0kNgu4ZEEyUvKhZLuGHKGKfW8qfV1zLDXjekPsw5K+s7eAwB/xa0QKBgQC7AkaT2yJbF9Yui7htaYIuXhHusEit16j3BdVVXfkPzkW0heVPLu+iB/YjGUn9StA6DB4x3XYbJDCA/FiDmTof2js0e1OC15TX6tTE2R4muNSkvzuh5Spj89Xm5lCuDi9CHsIBcENfBzem+1wfxgRrDduk3lYpuIXpkcpfsL1l7QKBgCU7GpMbt2abP2VPLW+Vg10McgDqVP4WfoWn++YP6vGFocZoxpbPRt/2u06WRb/k+y7f++yYq0zOqRfNjkaeiUhCwCQI9np269Imtwit7x1SSpw4uW7nNNjBvfMsPlt6EZBzxqoTXmZZkTuGqO/uJKVWIwMrseKVC2C5fJFR5YMBAoGBAKv5T8YwwstahFCRlKypVlolkAAchPm5VVy1NJYosR3j5x4388R5uU0cXTGx0+Tmo859zla0/iO/iAtWBGAgzN715XRB5W5xqiNVhQzxTVT2rDZE1iXvhKgeWBraul8WFEeN2YNRJeOB05/vj6x4gR+hwtc+z6XWVu+QbrbI5aORAoGASdviQ7xstczdRvIfAAo2ZLk8VrxyymmfmvSOhslhwkoMc3JkEZz4WRDcJL6STma/pkfz8wat3noQrRdmzk3Gt0ohRU7AlauR8N4Nm91bUNTZbVcH72nKsmu0tXxnlL46Fd8/BwkEywWpszJCDnHRtOBnxUasyb+68CCDiq4gcZc=",
                String.class);

        // 検証用変数
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

        // テスト対象実行
        RetrieveAllPlaces result = service.service("");

        // 検証
        assertEquals(retrieveAllPlaces.getAreaMapVersionId(), result.getAreaMapVersionId());
        placeAssert(retrieveAllPlaces.getPlaces().get(0), result.getPlaces().get(0));
        placeAssert(retrieveAllPlaces.getPlaces().get(1), result.getPlaces().get(1));
    }

    @Test
    void test_service_exception() {
        ReflectionTestUtils.setField(service, "authPrivateKey", "aaa", String.class);

        // テスト対象実行
        try {
            service.service("");
            // Exception発生予定のためこちらの分岐に入った場合エラーとする
            assertEquals(false, true);
        } catch (NoSuchAlgorithmException | IllegalArgumentException | InvalidKeySpecException | IOException e) {
            // 例外発生していたら正とする
            assertEquals(true, true);
        }
    }

    /**
     * 場所情報を検証する
     * 
     * @param act 実績値
     * @param exp 検証値
     */
    private void placeAssert(Place act, Place exp) {

        assertThat(act.getPointId()).isEqualTo(exp.getPointId());
        assertThat(act.getName()).isEqualTo(exp.getName());
        assertThat(act.getLocation().getLat()).isEqualTo(exp.getLocation().getLat());
        assertThat(act.getLocation().getLng()).isEqualTo(exp.getLocation().getLng());

    }
}
