
package com.chaojilaji.messageborad.service;


public interface UserSerivice {
    Boolean checkUser(String userName, String password);
    Boolean checkLogin(String userName,String password);
    Boolean messageCheck(String title,String message);
}