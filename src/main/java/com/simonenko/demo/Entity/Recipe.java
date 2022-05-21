package com.simonenko.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @Column(name = "recipe_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "recipe_name", length = 80)
    private String recipeName;

    @Column(name = "cooking_time")
    private Integer cookingTime;

    @Column(name = "calories")
    private Boolean calories;

    @Column(name = "squirrels")
    private Integer squirrels;

    @Column(name = "fats")
    private Integer fats;

    @Column(name = "carbohydrates")
    private Integer carbohydrates;

    @Column(name = "food_intake", length = 30)
    private String foodIntake;

    public String getFoodIntake() {
        return foodIntake;
    }

    public void setFoodIntake(String foodIntake) {
        this.foodIntake = foodIntake;
    }

    public Integer getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Integer getFats() {
        return fats;
    }

    public void setFats(Integer fats) {
        this.fats = fats;
    }

    public Integer getSquirrels() {
        return squirrels;
    }

    public void setSquirrels(Integer squirrels) {
        this.squirrels = squirrels;
    }

    public Boolean getCalories() {
        return calories;
    }

    public void setCalories(Boolean calories) {
        this.calories = calories;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}