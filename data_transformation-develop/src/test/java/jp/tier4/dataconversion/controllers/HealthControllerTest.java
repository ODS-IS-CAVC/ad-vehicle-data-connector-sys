package jp.tier4.dataconversion.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.tier4.dataconversion.Application;

/**
 * 
 * 死活監視用コントローラーテスト
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringJUnitConfig
@SpringBootTest(classes = Application.class)
public class HealthControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    HealthController target;

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
    @DisplayName("createUser_正常系")
    void vehiclesAllMapper_test() throws Exception {

        // 実行＆結果検証
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/health")
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // リクエスト実行
        String actual = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        // 検証用ファイル取得
        String expected = "";

        // 検証
        assertEquals(expected, actual);
    }
}
