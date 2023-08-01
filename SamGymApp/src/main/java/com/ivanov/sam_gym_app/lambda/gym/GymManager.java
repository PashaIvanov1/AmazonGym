package com.ivanov.sam_gym_app.lambda.gym;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanov.sam_gym_app.dto.GymDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GymManager {
    private static final String TABLE_NAME = System.getenv("GYM_TABLE");
    private final DynamoDB dynamoDB;

    public GymManager() {
        this.dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void saveGym(GymDTO gymDTO) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        Item item = new Item()
                .withPrimaryKey("id", gymDTO.getId())
                .withString("city", gymDTO.getCity())
                .withString("street", gymDTO.getStreet());
        table.putItem(item);
    }

    public GymDTO deserializeGymDTO(String json) throws IOException {
        if (json == null) {
            throw new IllegalArgumentException("argument \"content\" is null");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, GymDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGym(String gymId) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("id", gymId);
        table.deleteItem(deleteItemSpec);
    }

    public List<GymDTO> getAllGyms() {
        Table table = dynamoDB.getTable(TABLE_NAME);
        ScanSpec scanSpec = new ScanSpec();
        ItemCollection<ScanOutcome> items = table.scan(scanSpec);
        List<GymDTO> gymList = new ArrayList<>();
        items.forEach(item -> {
            GymDTO gymDTO = new GymDTO();
            gymDTO.setId(item.getString("id"));
            gymDTO.setCity(item.getString("city"));
            gymDTO.setStreet(item.getString("street"));
            gymList.add(gymDTO);
        });
        return gymList;
    }
}
