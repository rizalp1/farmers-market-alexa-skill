
package com.pradeeprizal.ask.farmersmkt.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.pradeeprizal.ask.farmersmkt.constants.AlexaSkillsConstants;
import com.pradeeprizal.ask.farmersmkt.dataset.*;
import com.pradeeprizal.ask.farmersmkt.utils.TimeSlotDayUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * Answers questions like which market is open today.
 */
public class FarmersMarketWhichIntentHandler implements RequestHandler {
    private final static int MARKET_RESPONSE_COUNT = 5;
    private static Logger LOGGER = LoggerFactory.getLogger(FarmersMarketWhichIntentHandler.class);

    private final FarmersMarketDayGrouper mFarmersMarketDayGrouper;

    public FarmersMarketWhichIntentHandler(
            final DatasetLoader datasetLoader) {
        mFarmersMarketDayGrouper = new FarmersMarketDayGrouper(datasetLoader.load());
    }

    public FarmersMarketWhichIntentHandler() {
        this(new FileBasedDatasetLoader());
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("FarmersMarketWhichIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Slot timeSlot = intent.getSlots().get(AlexaSkillsConstants.Slots.TIME_SLOT);
        String slotRequested = "today"; // default
        if (timeSlot != null) {
            LOGGER.info("Timeslot is not null so it is " + timeSlot.getValue());
            slotRequested = timeSlot.getValue();
        }

        LOGGER.debug("Timeslot requested is " + slotRequested);
        String dayRequestedTranslation = TimeSlotDayUtils.getDay(slotRequested);
        LOGGER.debug("Day requested translation is " + dayRequestedTranslation);
        List<String> markets = mFarmersMarketDayGrouper.getRandomMarketsForDay(dayRequestedTranslation, MARKET_RESPONSE_COUNT);

        if (markets.isEmpty()) {
            return buildResponse(input, "Sorry I don't know which market is open on " + dayRequestedTranslation);
        }

        String speechText = new StringBuilder()
                .append("Here are a few markets open on ")
                .append(dayRequestedTranslation)
                .append(". <break time=\"1s\"/>  ")
                .append(markets.stream().collect(Collectors.joining(", <break time=\"1s\"/>  ")))
                .append(". You can ask me about a specific market.")
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
