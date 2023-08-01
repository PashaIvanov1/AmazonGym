package com.ivanov.sam_gym_app.lambda.client;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanov.sam_gym_app.dto.ClientDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    private static final String TABLE_NAME = System.getenv("CLIENT_TABLE");
    private final DynamoDB dynamoDB;

    public ClientManager() {
        this.dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void saveClient(ClientDTO clientDTO) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        Item item = new Item()
                .withPrimaryKey("id", clientDTO.getId())
                .withString("firstName", clientDTO.getFirstName())
                .withString("lastName", clientDTO.getLastName())
                .withInt("age", clientDTO.getAge())
                .withString("dayOfBirth", clientDTO.getDayOfBirth())
                .withString("phone", clientDTO.getPhone())
                .withString("email", clientDTO.getEmail());
        table.putItem(item);
    }

    public ClientDTO deserializeClientDTO(String json) throws IOException {
        if (json == null) {
            throw new IllegalArgumentException("argument \"content\" is null");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, ClientDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClient(String clientId) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("id", clientId);
        table.deleteItem(deleteItemSpec);
    }

    public void updateClient(ClientDTO clientDTO) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        Item item = new Item()
                .withPrimaryKey("id", clientDTO.getId())
                .withString("firstName", clientDTO.getFirstName())
                .withString("lastName", clientDTO.getLastName())
                .withInt("age", clientDTO.getAge())
                .withString("dayOfBirth", clientDTO.getDayOfBirth())
                .withString("phone", clientDTO.getPhone())
                .withString("email", clientDTO.getEmail());
        table.putItem(item);
    }

    public List<ClientDTO> getAllClients() {
        Table table = dynamoDB.getTable(TABLE_NAME);
        ScanSpec scanSpec = new ScanSpec();
        ItemCollection<ScanOutcome> items = table.scan(scanSpec);
        List<ClientDTO> clientList = new ArrayList<>();
        items.forEach(item -> {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(item.getString("id"));
            clientDTO.setFirstName(item.getString("firstName"));
            clientDTO.setLastName(item.getString("lastName"));
            clientDTO.setAge(item.getInt("age"));
            clientDTO.setDayOfBirth(item.getString("dayOfBirth"));
            clientDTO.setPhone(item.getString("phone"));
            clientDTO.setEmail(item.getString("email"));
            clientList.add(clientDTO);
        });
        return clientList;
    }
}
