package jp.tier4.stub.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * エラーコントローラー
 * 
 */
@RestController
public class ErrorController {

	/**
	 * 400エラー
	 * 
	 * @return 
	 */
	@RequestMapping("/v1/400")
	public ResponseEntity<Map<String, Object>> error400() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", "InvalidParameter");
		map.put("message", "Invalid parameter error.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
	}
	/**
	 * 401エラー
	 * 
	 * @return 
	 */
	@RequestMapping("/v1/401")
	public ResponseEntity<Map<String, Object>> error401() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", "Unauthenticated");
		map.put("message", "Unauthenticated.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.UNAUTHORIZED);
	}
	/**
	 * 403エラー
	 * 
	 * @return 
	 */
	@RequestMapping("/v1/403")
	public ResponseEntity<Map<String, Object>> error403() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", "AccessForbidden");
		map.put("message", "Access forbidden.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.FORBIDDEN);
	}
	/**
	 * 404エラー
	 * 
	 * @return 
	 */
	@RequestMapping("/v1/404")
	public ResponseEntity<Map<String, Object>> error404() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", "EnvironmentNotFound");
		map.put("message", "The environment is not found.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}
	/**
	 * 429エラー
	 * 
	 * @return 
	 */
	@RequestMapping("/v1/429")
	public ResponseEntity<Map<String, Object>> error429() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", "RequestThrottledError");
		map.put("message", "Too many requests.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.TOO_MANY_REQUESTS);
	}
	/**
	 * 500エラー
	 * 
	 * @return 
	 */
	@RequestMapping("/v1/500")
	public ResponseEntity<Map<String, Object>> error500() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", "InternalServerError");
		map.put("message", "Internal server error.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/**
	 * 503エラー
	 * 
	 * @return 
	 */
	@RequestMapping("/v1/503")
	public ResponseEntity<Map<String, Object>> error503() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", "ServiceUnavailable");
		map.put("message", "The service is unavailable.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
