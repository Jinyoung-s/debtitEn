/**
 * The UserServiceImpl class implement features for member management.
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/02/2022
 */
package so.debateit.domain.application.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;
import so.debateit.common.model.User;
import so.debateit.config.UserNotiException;
import so.debateit.domain.application.UserService;
import so.debateit.domain.model.memo.UserMemo;
import so.debateit.domain.model.user.PoinHist;
import so.debateit.domain.model.user.SessionUser;
import so.debateit.domain.model.user.UserAuth;
import so.debateit.domain.model.user.UserDelete;
import so.debateit.domain.service.RegistrationManagement;
import so.debateit.infrastructure.repository.*;
import so.debateit.infrastructure.repository.elastic.UserAuthRepository;
import so.debateit.util.DebateEnvironment;
import so.debateit.util.MailUtils;
import so.debateit.util.TempKey;
import so.debateit.web.model.UserMemoParam;
import so.debateit.web.model.UserParam;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RegistrationManagement registrationManagement;

    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    //private final ElasticUserImgRepository elasticUserImgRepository;

    private final UserMemoRepository userMemoRepository;

    private final UserPoinRepository userPoinRepository;

    private final UserAuthRepository userAuthRepository;

    private final TempKey tempKey;

    private final JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    private final UserInfoDeleteRepository userInfoDeleteRepository;

    @Value("${custom.uploadPath}")
    private String uploadPath;

    @Override
    public void register(User user) throws Exception {

        User existUser = userInfoRepository.findByUserId(user.getUserId());
        if(existUser != null) {
         throw new UserNotiException("error.request.existEmail");
        }

        user.setUserAuth(0L);
        User newUser = registrationManagement.register(user);

        String key = tempKey.getKey(50, false);

        UserAuth userAuth = new UserAuth();
        userAuth.setUserMail(user.getUserId());
        userAuth.setAuthKet(key);
        userAuthRepository.save(userAuth);

        MailUtils sendMail = new MailUtils(mailSender);
        sendMail.setSubject("Debtit 회원가입 인증 메일");
        String mailUrl = DebateEnvironment.mailRegisterUrl;
        sendMail.setText(
                "<h1>회원가입 인증</h1>" +
                        "<br/>아래 링크를 클릭해 주세요<br/>"+
                        "<a href='" + mailUrl + user.getUserId() +
                        "&key=" + key +
                        "' target='_blenk'>이메일 인증 확인</a>");
        sendMail.setFrom("debateit23@gmail.com", "Debtit");
        sendMail.setTo(user.getUserId());
        sendMail.send();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(userId)) {
            throw new UsernameNotFoundException("no User found");
        }

        User user = userRepository.findByUserId(userId);

        if(user == null) {
            throw  new UsernameNotFoundException("no user found :" + userId);
        }

        return new SessionUser(user);
    }

    @Override
    public User getUserInfo(SessionUser user) {
        return userInfoRepository.findById(user.getUserNmbr()).orElseGet(User::new);
    }

    @Override
    public User getUserInfoById(String userId) {
        return userInfoRepository.findByUserId(userId);
    }

    @Override
    public User getUserSignUpInfo(SessionUser user) {
        return userInfoRepository.findByUserId(user.getUsername());
    }

    @Override
    public UserParam getUserInfoAndMemo(SessionUser user) {
        UserParam result = new UserParam();
        User searchUser = userInfoRepository.findById(user.getUserNmbr()).orElseGet(User::new);
        Pageable sortedByName = PageRequest.of(0, 10, Sort.by("cretDate").descending());

        List<UserMemo> userMemo = userMemoRepository.findByRecvUserAndReadCunt(searchUser, 0L, sortedByName);
        result.setUser(searchUser);
        result.setUserMemo(userMemo);
        return result;
    }

    @Override
    public void userMemoCheck(UserMemo memo) {
        memo.setReadCunt(1L);
        userMemoRepository.save(memo);
    }

    @Override
    public User modifyUserInfo(User user) {
        Long userNmbr = user.getUserNmbr();
        User dbUserInfo = userInfoRepository.getById(userNmbr);

        String userImgName = "";

        if(user.getUserImag() != null) {
            userImgName = fileMove(user.getUserImag());
        }

        dbUserInfo.setUserNick(user.getUserNick());
        dbUserInfo.setUserImag(userImgName);
        userInfoRepository.save(dbUserInfo);
        return dbUserInfo;
    }

    private String fileMove(String fileStringData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());
        String path = uploadPath + current_date;
        String data = fileStringData.split(",")[1];
        String imgType =fileStringData.split(";")[0].split("/")[1];

        String fileName =  Long.toString(System.nanoTime()) + '.' + imgType;
        String fileUrl = path+ "/" + fileName;
        File file = new File(path);

        if(!file.exists()) {
            file.mkdir();
        }

        try {
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            ImageIO.write(bufferedImage, imgType,  new File(fileUrl));

        } catch (Exception ex) {
            throw  new UserNotiException("error.unknownServerError");
        }

        return current_date + "/" + fileName;
    }

    @Override
    public UserMemo sendMemo(UserMemoParam memo) {
        UserMemo userMemo = new UserMemo();
        User sendUser = new User();
        sendUser.setUserNmbr(memo.getSendUser());
        User recvUser = new User();
        recvUser.setUserNmbr(memo.getRecvUser());

        userMemo.setSendUser(sendUser);
        userMemo.setCretUser(memo.getSendUser());
        userMemo.setRecvUser(recvUser);
        userMemo.setMemoCont(memo.getMemoCont());
        userMemo.setCretDate(DateUtils.createNow().getTime());
        userMemo.setReadCunt(0L);
        userMemoRepository.save(userMemo);
        return userMemo;
    }

    @Override
    public List<UserMemo> getUserMemoList(UserMemoParam param, SessionUser sessionUser) {
        Pageable sortedByName = PageRequest.of(param.getPageNo(), 50, Sort.by("cretDate").descending());
        User user = new User();
        user.setUserNmbr(sessionUser.getUserNmbr());
        return userMemoRepository.findByRecvUser(user, sortedByName);
    }

    @Override
    public void userPointUpdate(User user, Long point) {
        //user point update
        user.setUserPoin(user.getUserPoin() + point);
        userRepository.save(user);

        //user point history update
        PoinHist userPoin = new PoinHist();
        userPoin.setPoinAmnt(point);
        userPoin.setUserNmbr(user.getUserNmbr());
        userPoin.setCretUser(user.getUserNmbr());
        userPoin.setCretDate(DateUtils.createNow().getTime());
        userPoinRepository.save(userPoin);
    }

    @Override
    public void memberAuth(String memberEmail, String authKey) throws Exception{
        UserAuth userAuth = userAuthRepository.findById(memberEmail).get();

        if(userAuth != null && userAuth.getAuthKet().equals(authKey)){
            User user = userRepository.findByUserId(memberEmail);
            user.setUserAuth(1L);
            userRepository.save(user);
        }
    }

    @Override
    public User changePassword(UserParam userParam) {
        User user = userParam.getUser();
        User existUser = userInfoRepository.findByUserId(user.getUserId());
        if(!passwordEncoder.matches(userParam.getUser().getUserPass(), existUser.getUserPass())) {
            throw new UserNotiException("error.invalidPass");
        }

        existUser.setUserPass(userParam.getUserNewPass());
        registrationManagement.resetPass(existUser);
        return existUser;
    }

    @Override
    public User getSocialUserInfo(UserParam userParam) {

        User user = new User();
        final String RequestUrl = "https://www.googleapis.com/oauth2/v4/token";

        String socialCode = userParam.getUserSocialCode();
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParams.add(new BasicNameValuePair("client_id", DebateEnvironment.googleClientId));
        postParams.add(new BasicNameValuePair("client_secret", DebateEnvironment.googleSecret));
        postParams.add(new BasicNameValuePair("redirect_uri", DebateEnvironment.googleRedirectUrl)); // 리다이렉트 URI
        postParams.add(new BasicNameValuePair("code", userParam.getUserSocialCode()));

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(RequestUrl);
        JsonNode jsonToken  = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(postParams));
            final HttpResponse response = client.execute(post);
            final int responseCode = response.getStatusLine().getStatusCode();

            System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
            System.out.println("Post parameters : " + postParams);
            System.out.println("Response Code : " + responseCode);

            // JSON 형태 반환값 처리
            ObjectMapper mapper = new ObjectMapper();
            jsonToken  = mapper.readTree(response.getEntity().getContent());

            String accessToken = jsonToken.get("access_token").toString();
            String refreshToken = "";
            if(jsonToken.has("refresh_token")) {
                refreshToken = jsonToken.get("refresh_token").toString();
            }

            final String RequestUrlUserInfo = "https://www.googleapis.com/oauth2/v2/userinfo";
            final HttpClient clientUserInfo = HttpClientBuilder.create().build();
            final HttpGet get = new HttpGet(RequestUrlUserInfo);

            JsonNode userInfo = null;
            get.addHeader("Authorization", "Bearer " + accessToken);

            final HttpResponse responseUserInfo = clientUserInfo.execute(get);
            final int responseCodeUserInfo = responseUserInfo.getStatusLine().getStatusCode();

            ObjectMapper mapper1 = new ObjectMapper();
            userInfo = mapper1.readTree(responseUserInfo.getEntity().getContent());

            System.out.println("\nSending 'GET' request to URL : " + RequestUrl);
            System.out.println("Response Code : " + responseCodeUserInfo);

            String socialMail = userInfo.get("email").asText();
            String userPass = userInfo.get("id").asText();

            user.setUserId(socialMail);
            user.setUserPass(userPass);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // clear resources
        }
        return user;
    }

    public void deleteUser(User user) {

        UserDelete deleteUser = new UserDelete();
        deleteUser.setUserId(user.getUserId());
        deleteUser.setUserPoin(user.getUserPoin());
        deleteUser.setUserId(user.getUserId());
        deleteUser.setUserPass(user.getUserPass());
        deleteUser.setCretDate(DateUtils.createNow().getTime());
        deleteUser.setUserNick(user.getUserNick());
        deleteUser.setUserImag(user.getUserImag());

        //TO-DO storage for 30 days - need to create the batch
        userInfoDeleteRepository.save(deleteUser);
        userInfoRepository.delete(user);
        userInfoRepository.flush();
    }
}