package cn.fzz.controller;

import cn.fzz.bean.UserEntity;
import cn.fzz.dao.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.logging.Logger;

/**
 * Created by Andy on 2018/11/23.
 * Desc:
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private Logger logger = Logger.getLogger(String.valueOf(UserController.class));

    private final UserJPA userService;

    @Autowired
    public UserController(UserJPA userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/register")
    public String register(@Valid String email,
                           @Valid String password,
                           @Valid String username,
                           ModelMap modelMap, HttpServletRequest request) {

        if (!(StringUtils.isEmpty(email) || StringUtils.isEmpty(password))) {
            UserEntity userBean = new UserEntity(username, password, email);
            UserEntity userEntity = userService.save(userBean);
            logger.info(JSONObject.toJSONString(userEntity));
            modelMap.addAttribute("email", email);
            modelMap.addAttribute("password", password);
            modelMap.addAttribute("username", username);
            request.getSession().setAttribute("account", email);
        }
        return "register";
    }

    @RequestMapping(value = "/login")
    public String login(@Valid String email,
                        @Valid String password,
                        ModelMap modelMap, HttpServletRequest request) {
        if (!(StringUtils.isEmpty(email) || StringUtils.isEmpty(password))) {
            modelMap.addAttribute("email", email);
            modelMap.addAttribute("password", password);
            if (password.equals(userService.getByEmail(email).getPassword())) {
                modelMap.addAttribute("email", "successful");
                modelMap.addAttribute("password", "successful");
                HttpSession session = request.getSession();
                session.setAttribute("account", email);
                return "redirect:/welcome";
            }
        }
        return "login";
    }
}
