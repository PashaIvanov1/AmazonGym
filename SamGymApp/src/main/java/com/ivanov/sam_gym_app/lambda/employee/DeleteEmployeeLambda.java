package com.ivanov.sam_gym_app.lambda.employee;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class DeleteEmployeeLambda {
    private final EmployeeManager employeeManager = new EmployeeManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        String employeeId = request.getPathParameters().get("id");
        employeeManager.deleteEmployee(employeeId);
        System.out.println("Employee deleted: " + employeeId);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Employee deleted: " + employeeId);
    }
}
