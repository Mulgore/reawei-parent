package com.reawei.api;

import com.reawei.model.Person;

/**
 * Created in 2018/6/12 10:51
 *
 * @author qigong
 */
public interface ComputerService {

    Person getPersonById(Integer id);

    boolean updatePersonById(Person person);
}
