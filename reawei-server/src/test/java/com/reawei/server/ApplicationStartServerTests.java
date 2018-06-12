package com.reawei.server;

import com.reawei.api.ComputerService;
import com.reawei.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class ApplicationStartServerTests {


    @Resource
    private ComputerService computerService;

    @Test
    public void contextLoads() {
        Person person = computerService.getPersonById(1);
        System.out.println(person.getName());
    }

    @Test
    public void updateName() {
        Person person = new Person();
        person.setId(1);
        person.setName("test");
        System.out.println(computerService.updatePersonById(person));
    }

}
