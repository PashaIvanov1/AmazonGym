package com.ivanov.sam_gym_app.lambda.employee;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanov.sam_gym_app.dto.EmployeeDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private static final String TABLE_NAME = System.getenv("EMPLOYEE_TABLE");
    private final DynamoDB dynamoDB;

    public EmployeeManager() {
        this.dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void saveEmployee(EmployeeDTO employeeDTO) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        Item item = new Item()
                .withPrimaryKey("id", employeeDTO.getId())
                .withString("firstName", employeeDTO.getFirstName())
                .withString("lastName", employeeDTO.getLastName())
                .withInt("age", employeeDTO.getAge())
                .withString("dayOfBirth", employeeDTO.getDayOfBirth())
                .withString("phone", employeeDTO.getPhone())
                .withString("position", employeeDTO.getPosition())
                .withString("email", employeeDTO.getEmail())
                .withInt("experience", employeeDTO.getExperience())
                .withDouble("salary", employeeDTO.getSalary());
        table.putItem(item);
    }

    public EmployeeDTO deserializeEmployeeDTO(String json) throws IOException {
        if (json == null) {
            throw new IllegalArgumentException("argument \"content\" is null");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, EmployeeDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployee(String employeeId) {
        Table table = dynamoDB.getTable(TABLE_NAME);
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("id", employeeId);
        table.deleteItem(deleteItemSpec);
    }

    public List<EmployeeDTO> getAllEmployees() {
        Table table = dynamoDB.getTable(TABLE_NAME);
        ScanSpec scanSpec = new ScanSpec();
        ItemCollection<ScanOutcome> items = table.scan(scanSpec);
        List<EmployeeDTO> employeeList = new ArrayList<>();
        items.forEach(item -> {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(item.getString("id"));
            employeeDTO.setFirstName(item.getString("firstName"));
            employeeDTO.setLastName(item.getString("lastName"));
            employeeDTO.setAge(item.getInt("age"));
            employeeDTO.setDayOfBirth(item.getString("dayOfBirth"));
            employeeDTO.setPhone(item.getString("phone"));
            employeeDTO.setPosition(item.getString("position"));
            employeeDTO.setEmail(item.getString("email"));
            employeeDTO.setExperience(item.getInt("experience"));
            employeeDTO.setSalary(item.getDouble("salary"));
            employeeList.add(employeeDTO);
        });
        return employeeList;
    }
}
