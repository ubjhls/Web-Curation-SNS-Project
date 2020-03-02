package com.web.curation.controller.account;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.web.curation.controller.mail.MailUtil;
import com.web.curation.jwt.JwtService;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import com.web.curation.security.PasswordEncoding;
import com.web.curation.service.IPostService;
import com.web.curation.service.IProfileService;
import com.web.curation.service.IUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin("*")
@RestController
public class AccountController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IUserService userService;

	@Autowired
	private IProfileService profileService;
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestParam(required = true) String email, @RequestParam(required = true) String password) {
		log.info("POST : /account/login");
		
		PasswordEncoding passwordEncoding = new PasswordEncoding();
		User user = userService.findUserByEmailAndPassword(new User(email, password));
	    
		BasicResponse result = new BasicResponse();
		result.status = true;
		
		if (user == null) {
			result.data = "failed";
		} else if (!user.getConfirm().equals("Y")) {
			log.debug("/account/login " + email + " " + password);
			result.data = "noemailcheck";
		} else {
			if (user.getEmail().equalsIgnoreCase(email) && passwordEncoding.matches(password, user.getPassword())) {
				// 우영이형 소스
				user.setPicture(postService.getPicture(user.getNum()));
				String token = jwtService.create("member", user, "user");
				result.data = token;
			} else {
				log.error("/account/login " + email + " " + password);
				result.data = "failed";
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/account/user")
	@ApiOperation(value = "회원가입")
	public Object signUp(@Valid @RequestBody SignupRequest request) throws Exception {
		log.info("POST : /account/user");
		
		String email = request.getEmail();
		String nickname = request.getNickname();
		String name = request.getName();
		String password = request.getPassword();
		String intro = request.getIntro();
		PasswordEncoding passwordEncoding = new PasswordEncoding();
		password = passwordEncoding.encode(password);
		

		String key = "null";
		User user = new User(email, nickname, name, password, intro, key, 0);

		int isSuccess = userService.signUp(user);
		BasicResponse result = new BasicResponse();
		result.status = true;

		if (isSuccess != 1) {
			log.error("/account/user");
			result.data = "failed";
		} else {
			String isSuccess2 = sendMailForJoin(email); // 메일 전송
			if(!isSuccess2.equals("success")) {
				log.error("/account/user");
				result.data = "failed";
			}
			result.data = "success";
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/account/nickname")
	@ApiOperation(value = "닉네임 중복체크")
	public Object checkNick(@RequestParam(required = true) String nickname) {
		log.info("GET : /account/nickname");
		String temp = userService.checkNick(nickname);

		BasicResponse result = new BasicResponse();
		if (temp == null || temp == "") {
			result.status = false;
		} else {
			result.status = true;
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/account/email")
	@ApiOperation(value = "이메일 중복체크")
	public Object checkEmail(@RequestParam(required = true) final String email) {
		log.info("GET : /account/email");

		String temp = userService.checkEmail(email);

		BasicResponse result = new BasicResponse();
		if (temp == null || temp == "") {
			result.status = false;
		} else {
			result.status = true;
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "account/naverlogin", method = RequestMethod.GET)
	@ApiOperation(value = "네이버 로그인")
    public RedirectView test2(@RequestParam(value = "code") String code, @RequestParam(value = "state") String state) throws Exception {
    	log.info("GET : /account/naverlogin");
    	
        String clientId = "OmIgPMkxDFxNbKvzwMAw";//애플리케이션 클라이언트 아이디값";
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + clientId;
        apiURL += "&client_secret=" + "oYNFjkxJyb";
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;
        String access_token = "";
        String refresh_token = "";
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if(responseCode==200) { // 성공적으로 토큰을 가져온다면
                int id;
                String nickName, email, tmp, name;
                JsonParser parser = new JsonParser();
                JsonElement accessElement = parser.parse(res.toString());
                access_token = accessElement.getAsJsonObject().get("access_token").getAsString();
                tmp = getUserInfo(access_token);
                JsonElement userInfoElement = parser.parse(tmp);
                id = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsInt();
                nickName = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("nickname").getAsString();
                email = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
                name = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString();
                User user = userService.getUserByEmail(email);
                if(user == null) { 
                	// 없는 사용자임!! 회원가입(df주입)
                	user = new User();
                	user.setEmail(email);
                	user.setNickname(nickName);
                	user.setName(name);
                	user.setPassword("NAVER");
                	user.setIntro("NAVER 가입자");
                	user.setConfirm("Y");
                	user.setSocial(1);
                	userService.signUp(user);
                	createProfile(email);
                }
                       
                access_token = jwtService.create("member", user, "user");
            }
        } catch (Exception e) {
           	log.error(e+"");
        }
        return new RedirectView("https://i02b103.p.ssafy.io/#/?token="+access_token);
    }
	
	// 네이버에서 받아오는 함수
	private String getUserInfo(String access_token) {
		log.info("METHOD : getUserInfo");
		
        String header = "Bearer " + access_token; // Bearer 다음에 공백 추가
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
       
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            return res.toString();
        } catch (Exception e) {
            System.err.println(e);
            return "Err";
        }
    }
	
	// 회원가입 이메일 전송 메소드
	public String sendMailForJoin(String email) throws Exception {
		log.info("METHOD : sendMailForJoin");
		
		String keyCode = MailUtil.createKey();
		User user = new User(email, keyCode, 0);
		if (userService.updateConfirm(user) != 1) {
			return "failed";
		}

		String subject = "푸드득(得) 회원가입 인증 메일입니다.";

		StringBuilder msg = new StringBuilder();
		msg.append("<div align='center' style='border:1px solid black; font-family:verdana'>");
		msg.append("<h2> 푸드득(得) 회원가입 인증 메일입니다. </h2>");
		msg.append("<div style='font-size: 100%'>");
		msg.append("<h3>환영합니다! ");
		msg.append("<strong> " + keyCode + "</strong> 를 입력해주세요.</h2></div></div>");
		try {
			MailUtil.sendMail(email, subject, msg.toString());
			return "success";
		} catch (Exception e) {
			log.error(e+"");
			return "failed";
		}

	}
	
	// 유저 프로필 생성 메소드
	public String createProfile(@RequestParam(required = true) final String email) {
		log.info("METHOD : createProfile");
		
		int num = userService.getNumByEmail(email);
		int isSuccess = profileService.createProfile(num);
		
		if(isSuccess != 1) {
			return "failed";
		}

		return "success";
	}	
	
	// 토큰 검사 함수(내 정보 토큰)
	@PostMapping("/account/loginToken")
	@ApiOperation(value = "로그인 시 토큰 발급")
	public Object token(@Valid @RequestParam(required = true) String access_token) {
		log.info("POST : /account/loginToken");
		
		BasicResponse result = new BasicResponse();
		if(jwtService.isUsable(access_token)) {
			Object o = jwtService.get("member");
			//
			result.status = true;
			result.data="success";
			result.object = o;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
