package com.ivanov.sam_gym_app.lambda.trainer;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.TrainerDTO;

import java.util.List;

public class GetAllTrainerLambda {
    private final TrainerManager trainerManager = new TrainerManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            List<TrainerDTO> trainerList = trainerManager.getAllTrainers();
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(trainerList.toString());
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("Internal server error");
        }
    }
}
