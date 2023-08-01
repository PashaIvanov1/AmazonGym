package com.ivanov.sam_gym_app.lambda.trainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanov.sam_gym_app.dto.TrainerDTO;

public class TrainerManager {
    private static final String TABLE_NAME = System.getenv("TRAINER_TABLE");
    private final DynamoDB dynamoDB;

    public TrainerManager() {
        this.dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void saveTrainer(TrainerDTO trainerDTO) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        Item item = new Item()
                .withPrimaryKey("id", trainerDTO.getId())
                .withString("firstName", trainerDTO.getFirstName())
                .withString("lastName", trainerDTO.getLastName())
                .withString("description", trainerDTO.getDescription())
                .withString("phoneNumber", trainerDTO.getPhoneNumber())
                .withInt("experienceYears", trainerDTO.getExperienceYears())
                .withString("email", trainerDTO.getEmail())
                .withInt("age", trainerDTO.getAge());
        table.putItem(item);
    }

    public TrainerDTO deserializeTrainerDTO(String json) throws IOException {
        if (json == null) {
            throw new IllegalArgumentException("argument \"content\" is null");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, TrainerDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTrainer(String trainerId) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("id", trainerId);
        table.deleteItem(deleteItemSpec);
    }

    public List<TrainerDTO> getAllTrainers() {
        Table table = dynamoDB.getTable(TABLE_NAME);
        ScanSpec scanSpec = new ScanSpec();
        ItemCollection<ScanOutcome> items = table.scan(scanSpec);
        List<TrainerDTO> trainerList = new ArrayList<>();
        items.forEach(item -> {
            TrainerDTO trainerDTO = new TrainerDTO();
            trainerDTO.setId(item.getString("id"));
            trainerDTO.setFirstName(item.getString("firstName"));
            trainerDTO.setLastName(item.getString("lastName"));
            trainerDTO.setDescription(item.getString("description"));
            trainerDTO.setPhoneNumber(item.getString("phoneNumber"));
            trainerDTO.setExperienceYears(item.getInt("experienceYears"));
            trainerDTO.setEmail(item.getString("email"));
            trainerDTO.setAge(item.getInt("age"));
            trainerList.add(trainerDTO);
        });
        return trainerList;
    }

    public void updateTrainer(TrainerDTO trainerDTO) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("id", trainerDTO.getId())
                .withUpdateExpression("set firstName = :firstName, lastName = :lastName, description = :description, phoneNumber = :phoneNumber, experienceYears = :experienceYears, email = :email, age = :age")
                .withValueMap(new ValueMap()
                        .withString(":firstName", trainerDTO.getFirstName())
                        .withString(":lastName", trainerDTO.getLastName())
                        .withString(":description", trainerDTO.getDescription())
                        .withString(":phoneNumber", trainerDTO.getPhoneNumber())
                        .withInt(":experienceYears", trainerDTO.getExperienceYears())
                        .withString(":email", trainerDTO.getEmail())
                        .withInt(":age", trainerDTO.getAge()))
                .withReturnValues(ReturnValue.ALL_NEW);
        table.updateItem(updateItemSpec);
    }
}
