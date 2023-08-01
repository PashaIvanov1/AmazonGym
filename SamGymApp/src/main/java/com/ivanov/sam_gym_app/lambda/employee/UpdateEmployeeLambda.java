package com.ivanov.sam_gym_app.lambda.employee;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.EmployeeDTO;

import java.io.IOException;

public class UpdateEmployeeLambda {
    private final EmployeeManager employeeManager = new EmployeeManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            String requestBody = request.getBody();
            EmployeeDTO employeeDTO = employeeManager.deserializeEmployeeDTO(requestBody);
            employeeManager.saveEmployee(employeeDTO);
            System.out.println("Employee updated: " + employeeDTO);
            return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Employee updated: " + employeeDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
