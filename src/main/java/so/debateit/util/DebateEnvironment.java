package so.debateit.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DebateEnvironment {

    @Value("${custom.path.uploadImage}")
    private  String uploadImage;

    @Value("${custom.path.uploadRoot}")
    private  String uploadRoot;

    public  static String uploadPath;

    @Value("${custom.uploadPath}")
    private void setUploadPath(String value) {
        uploadPath = value;
    }

    @Value("${custom.defaultImg}")
    public static String defaultImg;

    public static String uploadImgServer;

    @Value("${custom.uploadImgServer}")
    private void setUploadImgServer(String value) {
        uploadImgServer = value;
    }

    @Value("${custom.defaultImg}")
    private void setDefaultImg(String value) {
        defaultImg = value;
    }


    public static String googleLoginUrl;

    public static String googleClientId;

    public static String googleRedirectUrl;

    public static String googleSecret;

    public static String googleAuthUrl;


    @Value("${google.login.url}")
    private void setGoogleLoginUrl(String value) {
        googleLoginUrl = value;
    }

    @Value("${google.redirect.url}")
    private void setGoogleRedirectUrl(String value) {
        googleRedirectUrl = value;
    }

    @Value("${google.client.id}")
    private void setGoogleClientId(String value) {
        googleClientId = value;
    }

    @Value("${google.secret}")
    private void setGoogleSecret(String value) {
        googleSecret = value;
    }

    @Value("${google.auth.url}")
    private void setGoogleAuthUrl(String value) {
        googleAuthUrl = value;
    }


    public static String mailRegisterUrl;

    @Value("${mail.mailRegisterUrl}")
    private void setMailRegisterUrl(String value) {
        mailRegisterUrl = value;
    }

    public static String socketServer1;

    @Value("${socket.server1}")
    private void setSocketServer1(String value) {
        socketServer1 = value;
    }

    public static String socketServer2;

    @Value("${socket.server2}")
    private void setSocketServer2(String value) {
        socketServer2 = value;
    }

    public static String redirectFromUrl;

    @Value("${redirect.fromUrl}")
    private void setRedirectFromUrl(String value) {
        redirectFromUrl = value;
    }

    public static String redirectToUrl;

    @Value("${redirect.toUrl}")
    private void setRedirectToUrl(String value) {
        redirectToUrl = value;
    }

}
