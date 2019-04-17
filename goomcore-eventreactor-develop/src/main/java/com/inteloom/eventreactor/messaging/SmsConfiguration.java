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
@EnableBinding(SmsConfiguration.Sms.class)
public class SmsConfiguration {

    private static Logger logger = LoggerFactory.getLogger(SmsConfiguration.class);

    private final GoomObjectService goomObjectService;

    public SmsConfiguration(GoomObjectService goomObjectService) {
        this.goomObjectService = goomObjectService;
    }

/*     @StreamListener(Sms.SMS_INBOUND)
    public void incomingSms(String message) throws IOException {
        logger.info("Sms message received: " + message);

       GoomObjectDTO goomObjectDTO = new GoomObjectDTO();
        goomObjectDTO.setTitle("Reply to sms " + message);
        goomObjectDTO.setDescription(goomObjectDTO.getTitle());

        goomObjectService.createGoomObject(goomObjectDTO, "sms-tasks");
    }*/

    interface Sms {
        String SMS_INBOUND = "sms-inbound";

        String SMS_OUTBOUND = "sms-outbound";

        @Input(SMS_INBOUND)
        SubscribableChannel received();

        @Input(SMS_OUTBOUND)
        SubscribableChannel sent();
    }
}
