package com.ivanov.sam_gym_app.lambda.client;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.ClientDTO;

import java.io.IOException;

public class UpdateClientLambda {
    private final ClientManager clientManager = new ClientManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            String requestBody = request.getBody();
            ClientDTO clientDTO = clientManager.deserializeClientDTO(requestBody);
            clientManager.updateClient(clientDTO);
            System.out.println("Client updated: " + clientDTO);
            return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Client updated: " + clientDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
