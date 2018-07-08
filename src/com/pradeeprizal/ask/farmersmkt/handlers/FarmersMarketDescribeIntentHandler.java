package com.pradeeprizal.ask.farmersmkt.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.pradeeprizal.ask.farmersmkt.constants.AlexaSkillsConstants;
import com.pradeeprizal.ask.farmersmkt.dataset.FarmersMarketDatasetClient;
import com.pradeeprizal.ask.farmersmkt.dataset.FileBasedDatasetLoader;
import com.pradeeprizal.ask.farmersmkt.dataset.InMemoryFarmersMarketClient;
import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * Answers questions like where is a market, when is market open, and describe the market.
 */
public class FarmersMarketDescribeIntentHandler implements RequestHandler {

    private final FarmersMarketDatasetClient mClient;

    public FarmersMarketDescribeIntentHandler(final FarmersMarketDatasetClient client) {
        mClient = client;
    }

    public FarmersMarketDescribeIntentHandler() {
        this(new InMemoryFarmersMarketClient(new FileBasedDatasetLoader().load()));
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("FarmersMarketDescribeIntent")) ||
                input.matches(intentName("FarmersMarketWhereIntent")) ||
                input.matches(intentName("FarmersMarketWhenIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Slot marketNameSlot = intent.getSlots().get(AlexaSkillsConstants.Slots.MARKET_NAME);
        if (marketNameSlot == null) {
            return buildResponse(input, "Sorry I don't understand which market - Please try again.");
        }

        Optional<FarmersMarketRecord> optionalMarket = mClient.query(marketNameSlot.getValue());
        if (! optionalMarket.isPresent()) {
            return buildResponse(input, "Sorry I don't know which market " + marketNameSlot.getValue() + " is.");
        }

        FarmersMarketRecord market =  optionalMarket.get();
        String speechText = new StringBuilder()
                .append(market.getMarket())
                .append(" is located at ")
                .append(market.getAddress())
                .append(" and is open on ")
                .append(market.getDays().stream().collect(Collectors.joining(" and ")))
                .append(" from ")
                .append(market.getStartTime())
                .append(" to ")
                .append(market.getEndTime())
                .toString();

        return buildResponse(input, speechText);
    }

    private Optional<Response> buildResponse(final HandlerInput input, final String speechText) {
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("PugetSound Community Farmers", speechText)
                .build();
    }
}
