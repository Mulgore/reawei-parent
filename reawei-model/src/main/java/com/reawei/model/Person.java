package com.reawei.model;

import java.io.Serializable;

/**
 * Created in 2018/6/12 10:50
 *
 * @author qigong
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 512530747856196280L;
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
