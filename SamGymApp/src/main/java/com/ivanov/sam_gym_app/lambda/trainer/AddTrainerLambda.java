package com.ivanov.sam_gym_app.lambda.trainer;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.TrainerDTO;

import java.io.IOException;

public class AddTrainerLambda {
    private final TrainerManager trainerManager = new TrainerManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            String requestBody = request.getBody();
            TrainerDTO trainerDTO = trainerManager.deserializeTrainerDTO(requestBody);
            trainerManager.saveTrainer(trainerDTO);
            System.out.println("Trainer saved: " + trainerDTO);
            return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Trainer saved: " + trainerDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
