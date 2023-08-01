package com.ivanov.sam_gym_app.lambda.client;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class DeleteClientLambda {
    private final ClientManager clientManager = new ClientManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        String clientId = request.getPathParameters().get("id");
        clientManager.deleteClient(clientId);
        System.out.println("Client deleted: " + clientId);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Client deleted: " + clientId);
    }
}
