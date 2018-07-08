
package com.pradeeprizal.ask.farmersmkt.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Welcome to the Community Farmers Skill, you can ask me about farmers market in the puget sound area";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Community Farmers", speechText)
                .withReprompt(speechText)
                .build();
    }

}
