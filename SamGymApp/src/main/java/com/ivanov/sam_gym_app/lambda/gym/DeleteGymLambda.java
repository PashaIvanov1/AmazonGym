package com.ivanov.sam_gym_app.lambda.gym;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class DeleteGymLambda {
    private final GymManager gymManager = new GymManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        String gymId = request.getPathParameters().get("id");
        gymManager.deleteGym(gymId);
        System.out.println("Gym deleted: " + gymId);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Gym deleted: " + gymId);
    }
}
