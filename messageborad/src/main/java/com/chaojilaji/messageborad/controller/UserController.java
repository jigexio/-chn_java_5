
package com.chaojilaji.messageborad.controller;


import com.chaojilaji.messageborad.model.User;
import com.chaojilaji.messageborad.service.UserSerivice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class UserController{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserSerivice userSerivice;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(@RequestParam("username")final String username,
                                       @RequestParam("password")final String password){
        Map<String,Object> ans = new HashMap<>();
        logger.info("提交的参数{}{}",username,password);
        if (userSerivice.checkUser(username,password)){
            ans.put("info","成功");
            ans.put("code",200);
        }
        return ans;
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(@RequestParam("username")final String userName,
                                    @RequestParam("password")final String password,
                                    Model model,
                                    HttpServletRequest httpServletRequest){
        Map<String,Object> ans = new HashMap<>();
        ans.put("code",400);
        ans.put("info","失败");
        if (userSerivice.checkLogin(userName,password)){
            ans.put("code",200);
            ans.put("info","成功");
            User user = new User();
            user.setUserName(userName);
            model.addAttribute("user",user);
            httpServletRequest.getSession();
        }
        return ans;
    }

    @RequestMapping(value = "/logincheck",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> loginCheck(@ModelAttribute("user")final User user){
        String userName = user.getUserName();
        Map<String,Object> ans = new HashMap<>();
        ans.put("code",200);
        ans.put("info",userName);
        return ans;
    }



    @RequestMapping(value = "/MessageBoard",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> messageCheck(@RequestParam("title") String title,
                                           @RequestParam("message") String message){
        Map<String,Object> ans = new HashMap<>();

        if (userSerivice.messageCheck(title,message)){
            ans.put("info","成功");
            ans.put("code",200);
        }
        return ans;
    }

    }

