package com.ivanov.sam_gym_app.entity;

import lombok.Data;

@Data
public class Trainer {

    private String id;
    private String firstName;
    private String lastName;
    private String description;
    private String phoneNumber;
    private int experienceYears;
    private String email;
    private int age;
}
