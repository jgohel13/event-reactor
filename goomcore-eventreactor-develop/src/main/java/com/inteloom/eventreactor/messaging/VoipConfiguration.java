package com.inteloom.eventreactor.messaging;

import com.inteloom.eventreactor.service.GoomObjectService;
import com.inteloom.eventreactor.service.dto.GoomObjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

import java.io.IOException;


/**
 * Created by ghumphries on 2017-11-14.
 */
@EnableBinding(VoipConfiguration.Voip.class)
public class VoipConfiguration {

    private static Logger logger = LoggerFactory.getLogger(VoipConfiguration.class);

    private final GoomObjectService goomObjectService;

    public VoipConfiguration(GoomObjectService goomObjectService) {
        this.goomObjectService = goomObjectService;
    }

/*    @StreamListener(Voip.VOIP_INBOUND)
    public void incomingVoip(String message) throws IOException {
        logger.info("Voip message received: " + message);

        GoomObjectDTO goomObjectDTO = new GoomObjectDTO();
        goomObjectDTO.setTitle("Reply to voip " + message);
        goomObjectDTO.setDescription(goomObjectDTO.getTitle());

        goomObjectService.createGoomObject(goomObjectDTO, "voip-tasks");
    }*/

    interface Voip {
        String VOIP_INBOUND = "voip-inbound";

        String VOIP_OUTBOUND = "voip-outbound";

        @Input(VOIP_INBOUND)
        SubscribableChannel received();

        @Input(VOIP_OUTBOUND)
        SubscribableChannel sent();
    }
}
