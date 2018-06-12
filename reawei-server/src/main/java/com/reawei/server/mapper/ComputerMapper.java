package com.reawei.server.mapper;

import com.reawei.model.Person;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created in 2018/6/12 11:01
 *
 * @author qigong
 */
@Repository
public interface ComputerMapper {

    Person getPersonById(@Param(value = "id") Integer id);

    int updatePersonById(Person person);
}
