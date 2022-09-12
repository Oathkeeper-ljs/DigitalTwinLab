package com.mobinets.digitaltwinlab.controller;

import com.mobinets.digitaltwinlab.entity.User;
import com.mobinets.digitaltwinlab.service.UserService;
import com.mobinets.digitaltwinlab.util.CommunityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class LoginController implements CommunityConstant {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

//    @Autowired
//    private Producer kaptchaProducer;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 注册页面，POST4个参数
     * @param username username
     * @param password password
     * @param campusNum campusNum
     * @param email email
     * @return return
     */
    @RequestMapping(path="/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> register(@RequestParam("username") String username, @RequestParam("password") String password,
                           @RequestParam("campusNum") Long campusNum, @RequestParam("email") String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCampusNum(campusNum);
        user.setEmail(email);
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            map.put("RegisterStatusCode", "1");
        } else{
            logger.info(map.toString());
            map.put("RegisterStatusCode", "0");
        }
        return map;
    }

    /**
     * 激活账号
     * @param userId UserId
     * @param code 激活码
     * @return return
     */
    @RequestMapping(path="/activation/{userId}/{code}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String activation(@PathVariable("userId") int userId, @PathVariable("code") String code) {
        int result = userService.activation(userId,code);
        if(result==ACTIVATION_SUCCESS) {
            return "Activation Success，您的账号已经可以正常使用！";
        } else if(result == ACTIVATION_REPEAT) {
            return "Invalid operation，该账号已经激活！";
        } else {
            return "Activation Fail，您提供的激活码不正确！";
        }
    }

    // 生成验证码
//    @RequestMapping(path="/kaptcha", method = RequestMethod.GET)
//    public void getKaptcha(HttpServletResponse response, HttpSession session) {
//        // 生成验证码
//        String text = kaptchaProducer.createText();
//        BufferedImage image = kaptchaProducer.createImage(text);
//
//        // 验证码文字存入session
//        session.setAttribute("kaptcha", text);
//
//        // 将图片输出给浏览器
//        response.setContentType("image/png");
//        try {
//            OutputStream os = response.getOutputStream();
//            ImageIO.write(image,"png", os);
//        } catch (IOException e) {
//            logger.error("响应验证码失败: " + e.getMessage());
//        }
//
//    }

    /**
     * 登录
     * ·登录log
     * @param username username
     * @param password password
     * @param rememberMe rememberme 过期时间
     * @param response response
     * @return return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam("rememberMe") boolean rememberMe, HttpServletResponse response) {
//        String kaptcha = (String) session.getAttribute("kaptcha");
        // 检查验证码
//        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
//            model.addAttribute("codeMsg","验证码不正确！");
//            return "/site/login";
//        }

        // 检查账号，密码
        int expiredSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username,password,expiredSeconds);
        if(map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            logger.info(map.get("userId") + " Log in success"); // check
            map.remove("ticket");
            map.put("loginStatusCode", "1");
        } else {
            logger.info(map.toString());
            map.put("loginStatusCode", "0");
        }
        return map;
    }

    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }
}
