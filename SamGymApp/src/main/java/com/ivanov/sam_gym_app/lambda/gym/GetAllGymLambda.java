package com.ivanov.sam_gym_app.lambda.gym;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.GymDTO;

import java.util.List;

public class GetAllGymLambda {
    private final GymManager gymManager = new GymManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            List<GymDTO> gymList = gymManager.getAllGyms();
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(gymList.toString());
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("Internal server error");
        }
    }
}
