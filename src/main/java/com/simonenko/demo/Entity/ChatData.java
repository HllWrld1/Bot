package com.simonenko.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "chat_data")
public class ChatData {
    @Id
    @Column(name = "chat_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected ChatData() {}

    public ChatData(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}