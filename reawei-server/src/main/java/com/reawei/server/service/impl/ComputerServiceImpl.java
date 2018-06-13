package com.reawei.server.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.reawei.api.ComputerService;
import com.reawei.model.Person;
import com.reawei.server.mapper.ComputerMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created in 2018/6/12 11:02
 *
 * @author qigong
 */
@Component("computerService")
@Service(timeout = 20000)
public class ComputerServiceImpl implements ComputerService {

    @Resource
    private ComputerMapper computerMapper;

    @Override
    public Person getPersonById(Integer id) {
        return computerMapper.getPersonById(id);
    }

    @Override
    public boolean updatePersonById(Person person) {
        return computerMapper.updatePersonById(person) > 0;
    }
}
