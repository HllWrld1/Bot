package com.simonenko.demo.Repository;

import com.simonenko.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query("UPDATE User p SET p.name = :name WHERE p.id = :id")
    @Transactional
    void changeName(@Param("id") long id, @Param("name") String name);

    @Modifying
    @Query("UPDATE User p SET p.age = :age WHERE p.id = :id")
    @Transactional
    void changeAge(@Param("id") long id, @Param("age") Integer age);

    @Modifying
    @Query("UPDATE User p SET p.height = :height WHERE p.id = :id")
    @Transactional
    void changeHeight(@Param("id") long id, @Param("height") Integer height);

    @Modifying
    @Query("UPDATE User p SET p.weight = :weight WHERE p.id = :id")
    @Transactional
    void changeWeight(@Param("id") long id, @Param("weight") Integer weight);

    @Modifying
    @Query("UPDATE User p SET p.activity = :activity WHERE p.id = :id")
    @Transactional
    void changeActivity(@Param("id") long id, @Param("activity") String activity);

    @Modifying
    @Query("UPDATE User p SET p.goal = :goal WHERE p.id = :id")
    @Transactional
    void changeGoal(@Param("id") long id, @Param("goal") String goal);

    @Modifying
    @Query("UPDATE User p SET p.calorieIntake = :calorieIntake WHERE p.id = :id")
    @Transactional
    void changeCalorie(@Param("id") long id, @Param("calorieIntake") String calorieIntake);

    @Modifying
    @Query("UPDATE User p SET p.imt = :imt WHERE p.id = :id")
    @Transactional
    void changeImt(@Param("id") long id, @Param("imt") String imt);

    @Query("SELECT name from User WHERE id = :id")
    String getNameById(@Param("id") long id);

    @Query("SELECT age from User WHERE id = :id")
    String getAgeById(@Param("id") long id);

    @Query("SELECT height from User WHERE id = :id")
    String getHeightById(@Param("id") long id);

    @Query("SELECT weight from User WHERE id = :id")
    String getWeightById(@Param("id") long id);

    @Query("SELECT activity from User WHERE id = :id")
    String getActivityById(@Param("id") long id);

    @Query("SELECT goal from User WHERE id = :id")
    String getGoalById(@Param("id") long id);

    @Query("SELECT calorieIntake from User WHERE id = :id")
    String getcalorieIntakeById(@Param("id") long id);

    @Query("SELECT name, age, height, weight, activity, goal from User WHERE id = :id")
    String getAllById(@Param("id") long id);
}