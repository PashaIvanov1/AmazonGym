package com.ivanov.sam_gym_app.lambda.trainer;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class DeleteTrainerLambda {
    private final TrainerManager trainerManager = new TrainerManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        String trainerId = request.getPathParameters().get("id");
        trainerManager.deleteTrainer(trainerId);
        System.out.println("Trainer deleted: " + trainerId);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Trainer deleted: " + trainerId);
    }
}
