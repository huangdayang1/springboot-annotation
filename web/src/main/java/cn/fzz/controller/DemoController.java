package cn.fzz.controller;

import cn.fzz.dao.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
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
}