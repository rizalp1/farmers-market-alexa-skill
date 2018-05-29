/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.pradeeprizal.ask.farmersmkt;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.SkillStreamHandler;
import com.pradeeprizal.ask.farmersmkt.handlers.CancelandStopIntentHandler;
import com.pradeeprizal.ask.farmersmkt.handlers.FarmersMarketIntentHandler;
import com.pradeeprizal.ask.farmersmkt.handlers.HelpIntentHandler;
import com.pradeeprizal.ask.farmersmkt.handlers.SessionEndedRequestHandler;
import com.pradeeprizal.ask.farmersmkt.handlers.LaunchRequestHandler;

public class FarmersMarketStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new FarmersMarketIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler())
                // Add your skill id below
                //.withSkillId("")
                .build();
    }

    public FarmersMarketStreamHandler() {
        super(getSkill());
    }

}
