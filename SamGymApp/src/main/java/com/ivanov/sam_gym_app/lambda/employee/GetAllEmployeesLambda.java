package com.ivanov.sam_gym_app.lambda.employee;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.EmployeeDTO;

import java.util.List;

public class GetAllEmployeesLambda {
    private final EmployeeManager employeeManager = new EmployeeManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            List<EmployeeDTO> employeeList = employeeManager.getAllEmployees();
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(employeeList.toString());
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("Internal server error");
        }
    }
}
