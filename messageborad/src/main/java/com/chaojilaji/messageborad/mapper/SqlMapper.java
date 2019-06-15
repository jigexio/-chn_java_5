package com.chaojilaji.messageborad.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "sqlMapper")
public interface SqlMapper{
    List<Map<String,Object>> sql(Map<String,Object> map);
}