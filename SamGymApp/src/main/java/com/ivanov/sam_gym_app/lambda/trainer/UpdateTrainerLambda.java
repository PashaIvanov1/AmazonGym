package com.ivanov.sam_gym_app.lambda.trainer;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.TrainerDTO;

import java.io.IOException;

public class UpdateTrainerLambda {
    private final TrainerManager trainerManager = new TrainerManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            String requestBody = request.getBody();
            TrainerDTO trainerDTO = trainerManager.deserializeTrainerDTO(requestBody);
            trainerManager.updateTrainer(trainerDTO);
            System.out.println("Trainer updated: " + trainerDTO);
            return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Trainer updated: " + trainerDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
