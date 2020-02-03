package com.web.curation.controller.account;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.web.curation.model.Follow;

import com.web.curation.controller.mail.MailUtil;
import com.web.curation.jwt.JwtService;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import com.web.curation.service.IFollowService;
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
	@Autowired
	private IUserService userService;

	@Autowired
	private IProfileService profileService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private IFollowService followService;
	
	@PostMapping("/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestParam(required = true) String email, @RequestParam(required = true) String password) {

		System.out.println("-----------------login-----------------");
		System.out.println("email : " + email);
		System.out.println("password : " + password);

		User user = userService.findUserByEmailAndPassword(new User(email, password));
		
		BasicResponse result = new BasicResponse();
		result.status = true;
		
		if (user == null) {
			System.out.println("로그인 실패");
			result.data = "failed";
		} else if (!user.getConfirm().equals("Y")) {
			System.out.println("이메일 미 인증 사용자");
			result.data = "noemailcheck";
		} else {
			if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
				System.out.println("로그인 성공");
				// 우영이형 소스
				String token = jwtService.create("member", user, "user");
				System.out.println(token);
				result.data = token;
			} else {
				System.out.println("로그인 실패");
				result.data = "failed";
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/account/signUp")
	@ApiOperation(value = "회원가입")
	public Object signUp(@Valid @RequestBody SignupRequest request) throws Exception {
		
		System.out.println("-----------------signUp-----------------");
		String email = request.getEmail();
		String nickname = request.getNickname();
		String name = request.getName();
		String password = request.getPassword();
		String intro = request.getIntro();
		System.out.println("email : " + email);
		System.out.println("nickname : " + nickname);
		System.out.println("name : " + name);
		System.out.println("password : " + password);
		System.out.println("intro : " + intro);

		String key = "null";
		User user = new User(email, nickname, name, password, intro, key, 0);

		int isSuccess = userService.signUp(user);
		BasicResponse result = new BasicResponse();
		result.status = true;

		if (isSuccess != 1) {
			System.out.println("회원가입 실패");
			result.data = "failed";
		} else {
			System.out.println("회원가입 성공");
			String isSuccess2 = sendMailForJoin(email); // 메일 전송
			if(!isSuccess2.equals("success")) {
				result.data = "failed";
			}
			result.data = "success";
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/account/checkNick")
	@ApiOperation(value = "닉네임 중복체크")
	public Object checkNick(@RequestParam(required = true) String nickname) {
		System.out.println("-----------------checkNick-----------------");
		System.out.println("nickname : " + nickname);
		String temp = userService.checkNick(nickname);

		BasicResponse result = new BasicResponse();
		if (temp == null || temp == "") {
			result.status = false;
		} else {
			result.status = true;
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/account/checkEmail")
	@ApiOperation(value = "이메일 중복체크")
	public Object checkEmail(@RequestParam(required = true) final String email) {
		System.out.println("-----------------checkEmail-----------------");
		System.out.println("email : " + email);

		String temp = userService.checkEmail(email);

		BasicResponse result = new BasicResponse();
		if (temp == null || temp == "") {
			result.status = false;
		} else {
			result.status = true;
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/account/createProfile")
	@ApiOperation(value = "유저 프로필 생성")
	public String createProfile(@RequestParam(required = true) final String email) {
		System.out.println("-----------------createProfile-----------------");
		System.out.println("email : " + email);
		
		int num = userService.getNumByEmail(email);
		System.out.println(num);
		int isSuccess = profileService.createProfile(num);
		System.out.println(isSuccess);
		
		if(isSuccess != 1) {
			return "failed";
		}

		return "success";
	}	
	
	@RequestMapping(value = "account/naverlogin", method = RequestMethod.GET)
    public RedirectView test2(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "state") String state
    ) throws Exception {
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
                	System.out.println(user.toString());
                	userService.signUp(user);
                	createProfile(email);
                }
                              
                access_token = jwtService.create("member", user, "user");
                System.out.println(access_token);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //return "redirect:http://localhost:8081/mypage?token=" + acc+ess_token;
        return new RedirectView("http://192.168.31.135:8080/#/?token="+access_token);
    }
	
	// 네이버에서 받아오는 함수
	private String getUserInfo(String access_token) {
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
		System.out.println("-----------------sendMailForJoin-----------------");
		System.out.println("email : " + email);

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
			System.out.println(e);
			return "failed";
		}

	}
	

	// 토큰 검사 함수(내 정보 토큰)
	@PostMapping("/account/loginToken")
	@ApiOperation(value = "로그인 시 토큰 발급")
	public Object token(@Valid @RequestParam(required = true) String access_token) {
		System.out.println("-----------------token-----------------");
		System.out.println("access_token : " + access_token);
		
		BasicResponse result = new BasicResponse();
		if(jwtService.isUsable(access_token)) {
			System.out.println("유효");
			Object o = jwtService.get("member");
			//
			result.status = true;
			result.data="success";
			result.object = o;
			System.out.println(o);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	


}