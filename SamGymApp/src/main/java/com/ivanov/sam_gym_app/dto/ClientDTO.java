package com.ivanov.sam_gym_app.dto;

import lombok.Data;

@Data
public class ClientDTO {

    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String dayOfBirth;
    private String phone;
    private String email;
}
