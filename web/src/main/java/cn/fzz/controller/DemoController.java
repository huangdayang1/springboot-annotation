package cn.fzz.controller;

import cn.fzz.dao.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Controller
public class DemoController {

    private final UserJPA userService;

    @Autowired
    public DemoController(UserJPA userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/findAll")
    public @ResponseBody
    Map<String, Object> findAll() {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("data", userService.findAll());

        resultMap.put("returnCode", "");
        resultMap.put("message", "");
        return resultMap;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(ModelMap model) throws Exception {
        // 加入一个属性，用来在模板中读取
        model.addAttribute("message", "http://blog.didispace.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html

        throw new Exception("test");

//        return "welcome";
    }

    /**
     * 404页面
     */
    @RequestMapping(value = "/error/404")
    public String error_404() {
        return "error/404";
    }

    /**
     * 500页面
     */
    @RequestMapping(value = "/error/500")
    public String error_500() {
        return "error/500";
    }
}