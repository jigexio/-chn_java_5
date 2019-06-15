
/**
 * testaop
 * com.chaojilaji.testaop.controller
 * <p>
 * Copyright © 2019 重庆市信息通信咨询设计院有限公司.版权所有.
 * 重庆市九龙坡区科园四路257号,400041.
 * <p>
 * 此软件未经重庆市信息通信咨询设计院有限公司许可，严禁发布、传播、使用.
 */
package com.chaojilaji.testaop.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/testaop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getTest(){
        Map<String,Object> ans = new HashMap<>();
        ans.put("code",200);
        return ans;
    }


}