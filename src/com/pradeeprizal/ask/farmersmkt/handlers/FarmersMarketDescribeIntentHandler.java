/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.pradeeprizal.ask.farmersmkt.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.pradeeprizal.ask.farmersmkt.constants.AlexaSkillsConstants;
import com.pradeeprizal.ask.farmersmkt.dataset.FarmersMarketDatasetClient;
import com.pradeeprizal.ask.farmersmkt.dataset.InMemoryFarmersMarketClient;
import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazon.ask.request.Predicates.intentName;

public class FarmersMarketDescribeIntentHandler implements RequestHandler {

    private final FarmersMarketDatasetClient mClient;

    public FarmersMarketDescribeIntentHandler(final FarmersMarketDatasetClient client) {
        mClient = client;
    }

    public FarmersMarketDescribeIntentHandler() {
        // TODO: Replace with real list.
        this(new InMemoryFarmersMarketClient(Collections.emptyList()));
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("FarmersMarketIntent"));
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
                .append(marketNameSlot.getValue())
                .append(" is located at ")
                .append(market.getAddress())
                .append(" and is open on ")
                .append(market.getDays().stream().collect(Collectors.joining(" and ")))
                .append(" from ")
                .append(market.getStartTime())
                .append(" to ")
                .append(market.getEndTime())
                .toString();

        return buildResponse(input, speechText.toString());
    }

    private Optional<Response> buildResponse(final HandlerInput input, final String speechText) {
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("PugetSound Community Farmers", speechText)
                .build();
    }
}
