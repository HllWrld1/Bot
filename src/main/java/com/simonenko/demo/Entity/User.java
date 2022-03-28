package com.simonenko.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "chat_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "activity")
    private String activity;

    @Column(name = "goal", length = 30)
    private String goal;

    @Column(name = "calorie_intake")
    private String calorieIntake;

    @Column(name = "imt")
    private String imt;

    @Column(name = "bju", length = 20)
    private String bju;

    public String getBju() {
        return bju;
    }

    public void setBju(String bju) {
        this.bju = bju;
    }

    public String getImt() {
        return imt;
    }

    public void setImt(String imt) {
        this.imt = imt;
    }

    public String getCalorieIntake() {
        return calorieIntake;
    }

    public void setCalorieIntake(String calorieIntake) {
        this.calorieIntake = calorieIntake;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

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

    protected User() {
    }

    public User(Long id, String name, Integer age, Integer height, Integer weight, String activity, String goal, String calorieIntake, String imt, String bju) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activity = activity;
        this.goal = goal;
        this.calorieIntake = calorieIntake;
        this.imt = imt;
        this.bju = bju;
    }

    public User(Long id) {
        this.id = id;
    }

}