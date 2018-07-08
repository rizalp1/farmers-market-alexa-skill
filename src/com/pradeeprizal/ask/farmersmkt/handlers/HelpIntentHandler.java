
package com.pradeeprizal.ask.farmersmkt.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class HelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Ask 'when is the Ballard farmers market open' or 'where is University District farmers market'";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Community Farmers", speechText)
                .withReprompt(speechText)
                .build();
    }
}
