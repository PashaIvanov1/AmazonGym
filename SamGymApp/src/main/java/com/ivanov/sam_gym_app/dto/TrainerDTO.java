package com.ivanov.sam_gym_app.dto;

import lombok.Data;

@Data
public class TrainerDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String description;
    private String phoneNumber;
    private int experienceYears;
    private String email;
    private int age;
}
