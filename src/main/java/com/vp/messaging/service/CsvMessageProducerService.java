package com.vp.messaging.service;

import com.vp.messaging.config.CsvStreams;
import com.vp.messaging.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CsvMessageProducerService {

    private final CsvStreams csvStreams;

    public void sendCsvMessage(Employee employee) {
        log.info("Producer ----> Sending CSV message {}", employee);
        MessageChannel messageChannel = csvStreams.outboundCsvMessage();
        messageChannel.send(MessageBuilder
                .withPayload(employee)
                .setHeader("Content-Type", "application/json")
                .build()
        );
    }

}
