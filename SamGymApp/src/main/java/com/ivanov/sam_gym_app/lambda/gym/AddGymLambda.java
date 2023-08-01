package com.ivanov.sam_gym_app.lambda.gym;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.GymDTO;

import java.io.IOException;

public class AddGymLambda {

    private final GymManager gymManager = new GymManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            String requestBody = request.getBody();
            GymDTO gymDTO = gymManager.deserializeGymDTO(requestBody);
            gymManager.saveGym(gymDTO);
            System.out.println("Gym saved: " + gymDTO);
            return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Gym saved: " + gymDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
