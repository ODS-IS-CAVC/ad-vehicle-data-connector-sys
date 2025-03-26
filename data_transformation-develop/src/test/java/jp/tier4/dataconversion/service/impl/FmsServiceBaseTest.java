package jp.tier4.dataconversion.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jp.tier4.dataconversion.Application;

/**
 * 
 * FMS APIサービスベーステスト
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringJUnitConfig
@SpringBootTest(classes = Application.class)
public class FmsServiceBaseTest {

    @Test
    void test_getToken() {
        FmsServiceBase<String, String> testClass = new FmsServiceBase<String, String>();
        try {
            String result = testClass.service("");
            assertEquals(result, null);
        } catch (Exception e) {
            assertEquals(true, false);
        }
    }
}
