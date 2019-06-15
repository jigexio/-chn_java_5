
package com.chaojilaji.messageborad.service.Impl;


import com.chaojilaji.messageborad.mapper.SqlMapper;
import com.chaojilaji.messageborad.service.UserSerivice;
import com.chaojilaji.messageborad.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserSerivice {

    @Autowired
    private SqlMapper sqlMapper;


    @Override
    public Boolean checkUser(String userName, String password) {
        String newPassword = Token.md5Encode(password);
//        String sql1 = String.format("select count(*) from user where user_name='%s'",userName);
        String sql = String.format("insert into user(user_name,user_password) values('%s','%s')",userName,newPassword);
        Map<String, Object> params = new HashMap<>();
        params.put("sql", sql);
        params.put("user_name", userName);
        params.put("user_password", password);

        try {
            sqlMapper.sql(params);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



    @Override
    public Boolean checkLogin(String userName, String password) {
        password = Token.md5Encode(password);
        String sql = "select count(*) from user where user_name=#{username} and user_password=#{password}";
        Map<String,Object> params = new HashMap<>();
        params.put("sql",sql);
        params.put("username",userName);
        params.put("password",password);
        List<Map<String,Object>> res = sqlMapper.sql(params);
        if (Objects.nonNull(res) && res.size()>0){
            Map<String,Object> tmp = res.get(0);
            long cnt = (long) tmp.get("count(*)");
            if (cnt == 1){
                return true;
            }
        }
        return false;
    }


    @Override
    public Boolean messageCheck(String title, String message) {

        String sql = String.format("insert into MessageBoard(content,username) values('%s','%s')", title, message);
        Map<String, Object> params = new HashMap<>();

        params.put("sql", sql);
        params.put("title",title );
        params.put("message",message );

        try {
            sqlMapper.sql(params);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



}