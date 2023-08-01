package com.ivanov.sam_gym_app.lambda.client;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ivanov.sam_gym_app.dto.ClientDTO;

import java.util.List;

public class GetAllClientLambda {
    private final ClientManager clientManager = new ClientManager();

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        try {
            List<ClientDTO> clientList = clientManager.getAllClients();
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(clientList.toString());
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("Internal server error");
        }
    }
}
