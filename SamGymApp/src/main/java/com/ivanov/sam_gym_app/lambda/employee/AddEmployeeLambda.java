package com.ivanov.sam_gym_app.lambda.employee;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.EmployeeDTO;

import java.io.IOException;

public class AddEmployeeLambda {
    private final EmployeeManager employeeManager = new EmployeeManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            String requestBody = request.getBody();
            EmployeeDTO employeeDTO = employeeManager.deserializeEmployeeDTO(requestBody);
            employeeManager.saveEmployee(employeeDTO);
            System.out.println("Employee saved: " + employeeDTO);
            return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Employee saved: " + employeeDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
