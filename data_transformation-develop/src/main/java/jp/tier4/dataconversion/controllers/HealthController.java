package jp.tier4.dataconversion.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 死活監視用コントローラー
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class HealthController {

    /**
     * 
     * 死活管理用API
     *
     * @return
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    @GetMapping("/health")
    public String vehicles() {
        return "";
    }
}
