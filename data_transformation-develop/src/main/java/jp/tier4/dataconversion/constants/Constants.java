package jp.tier4.dataconversion.constants;

/**
 * 
 * 定数クラス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class Constants {

    /** データモデルタイプ：テスト1 */
    public static final String DATA_MODEL_TYPE_TEST1 = "test1";
    /** データモデルタイプ：テスト2 */
    public static final String DATA_MODEL_TYPE_TEST2 = "test2";

    /** FMSベアラートークン：bearer */
    public static final String FMS_AUTH_TOKEN_BEARER = "Bearer ";
    /** FMS暗号化方式：RSA */
    public static final String FMS_AUTH_RSA = "RSA";

    /** FMS Auth：グラント種別 */
    public static final String FMS_GRANT_TYPE = "client_credentials";

    /** FMS Auth：クライアントアサーション */
    public static final String FMS_CLIENT_ASSERTION = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";

    /** FMS API：自動運転車両スケジュール登録 スケジュールタイプmove */
    public static final String FMS_REGIST_SCHEDULE_TASK_TYPE_MOVE = "move";

    /** 400エラーコード */
    public static final String CODE_400 = "InvalidParameter";

    /** 400エラーメッセージ */
    public static final String MESSAGE_400 = "Invalid parameter error.";

    /** 400エラーコード */
    public static final String CODE_401 = "Unauthenticated";

    /** 400エラーメッセージ */
    public static final String MESSAGE_401 = "Unauthenticated.";

    /** 400エラーコード */
    public static final String CODE_500 = "InternalServerError";

    /** 400エラーメッセージ */
    public static final String MESSAGE_500 = "Internal server error.";

    /** 400エラーコード */
    public static final String CODE_503 = "ServiceUnavailable";

    /** 400エラーメッセージ */
    public static final String MESSAGE_503 = "The service is unavailable.";
}
