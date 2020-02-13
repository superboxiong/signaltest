package com.tydic.signaltest.service;


import java.util.Map;

public interface LoginService {

    Map<String,Object> getUser(String phone, String password) ;
}
