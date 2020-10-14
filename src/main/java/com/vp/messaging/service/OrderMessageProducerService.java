package com.vp.messaging.service;

import com.vp.messaging.config.CsvStreams;
import com.vp.messaging.model.SubscriptionOrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderMessageProducerService {

    private final CsvStreams csvStreams;

    public void sendMessage(SubscriptionOrderMessage subscriptionOrderMessage) {
        log.info("Producer ----> Sending CSV message {}", subscriptionOrderMessage);
        MessageChannel messageChannel = csvStreams.outboundCsvMessage();
        messageChannel.send(MessageBuilder
                .withPayload(subscriptionOrderMessage)
                .setHeader("Content-Type", "application/json")
                .build()
        );
    }

}
