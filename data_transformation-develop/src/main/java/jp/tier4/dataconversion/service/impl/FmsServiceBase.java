package jp.tier4.dataconversion.service.impl;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.openssl.PEMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.web.client.RestClient;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jp.tier4.dataconversion.constants.Constants;
import jp.tier4.dataconversion.domain.model.fms.FmsAuthRequest;
import jp.tier4.dataconversion.domain.model.fms.FmsAuthResponse;
import jp.tier4.dataconversion.service.ServiceBase;

/**
 * 
 * FMS APIサービスベース
 *
 * @param <R> 結果
 * @param <P> パラメータ
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class FmsServiceBase<R, P> implements ServiceBase<R, P> {

    /** FMS Auth:ベースURL */
    @Value("${fms.auth.url.base}")
    String authBaseUrl;
    /** FMS Auth:バージョン */
    @Value("${fms.auth.url.version}")
    String authVersionUrl;
    /** FMS Auth:トークン取得URL */
    @Value("${fms.auth.url.token}")
    String authTokenUrl;
    /** コネクションタイムアウト設定時間 */
    @Value("${fms.auth.timeout.conn}")
    int authConnTimeout;
    /** リードタイムアウト設定時間 */
    @Value("${fms.auth.timeout.read}")
    int authReadTimeout;
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
    /** FMS Authクライアント */
    private RestClient authClient;

    /** FMS Auth:ベースURL */
    @Value("${fms.api.url.base}")
    String fmsBaseUrl;
    /** FMS Auth:バージョン */
    @Value("${fms.api.url.version}")
    String fmsVersionUrl;
    /** コネクションタイムアウト設定時間 */
    @Value("${fms.api.timeout.conn}")
    int fmsConnTimeout;
    /** リードタイムアウト設定時間 */
    @Value("${fms.api.timeout.read}")
    int fmsReadTimeout;
    /** FMS APIクライアント */
    protected RestClient fmsClient;

    /** FMS API:プロジェクトID */
    @Value("${fms.api.project.id}")
    String fmsProjectId;

    /** FMS Auth:環境ID */
    @Value("${fms.api.environment.id}")
    String fmsEnvId;

    /**
     * 
     * ベアラートークンを取得する（FMS Auth）
     *
     * @return "bearer "+トークン
     * @throws NoSuchAlgorithmException
     * @throws IllegalArgumentException
     * @throws InvalidKeySpecException
     * @throws PEMException
     * @throws IOException
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    protected String getToken()
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {
        // 固定文言"bearer" + FMS Authから取得したtoken
        return Constants.FMS_AUTH_TOKEN_BEARER + this.getFmsAuthToken();
    }

    /**
     * 
     * FMS Authからトークン取得する
     *
     * @return トークン
     * @throws NoSuchAlgorithmException
     * @throws IllegalArgumentException
     * @throws InvalidKeySpecException
     * @throws PEMException
     * @throws IOException
     *
     * @version 0.0.1
     * @since 0.0.1
     */
    private String getFmsAuthToken()
            throws NoSuchAlgorithmException, IllegalArgumentException, InvalidKeySpecException, PEMException,
            IOException {

        // FMS Authの基本設定（タイムアウト時間）
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(authConnTimeout))
                .withReadTimeout(Duration.ofSeconds(authReadTimeout));

        // FNS AuthのURL組み立て
        StringBuilder fmsAuthUrl = new StringBuilder();
        fmsAuthUrl.append(authBaseUrl);
        fmsAuthUrl.append(authVersionUrl);
        fmsAuthUrl.append(authTokenUrl);
        this.authClient = RestClient.builder().baseUrl(fmsAuthUrl.toString())
                .requestFactory(ClientHttpRequestFactories.get(settings)).build();

        // JWT検証
        String token = null;
        // 秘密鍵をBase64でデコード
        byte[] decoded = Base64.getDecoder().decode(this.authPrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);

        KeyFactory keyFactory = KeyFactory.getInstance(Constants.FMS_AUTH_RSA);
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

        FmsAuthRequest req = new FmsAuthRequest();
        req.setGrantType(Constants.FMS_GRANT_TYPE);
        //		req.setScope(null); // デフォルトでよい
        //		req.setAssertion(null); // 非必須
        //		req.setRefresh_token(null); // 非必須
        req.setClientId(this.authClientId); // 非必須
        //		req.setClient_secret(null); // 非必須
        req.setClientAssertion(jwtToken);
        req.setClientAssertionType(Constants.FMS_CLIENT_ASSERTION);
        //		req.setSubject_token(null); // 非必須
        //		req.setSubject_token_type(null); // 非必須
        FmsAuthResponse res = this.authClient.post().body(req).retrieve().body(FmsAuthResponse.class);
        token = res.getAccessToken();
        return token;
    }

    /**
     * FMS APIを呼び出すRestClientの準備を行う
     */
    protected void serviceAdvice() {

        // FMS APIの基本設定（タイムアウト時間）
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(this.fmsConnTimeout))
                .withReadTimeout(Duration.ofSeconds(this.fmsReadTimeout));

        // FMS APIのベースURL作成
        StringBuilder fmsApiUrl = new StringBuilder();
        fmsApiUrl.append(this.fmsBaseUrl);
        fmsApiUrl.append(this.fmsVersionUrl);

        // FMS APIのベースUR設定
        this.fmsClient = RestClient.builder().baseUrl(fmsApiUrl.toString())
                .requestFactory(ClientHttpRequestFactories.get(settings)).build();

    }

    @Override
    public R service(P p) throws Exception {
        // 継承のため空実装
        return null;
    }
}
