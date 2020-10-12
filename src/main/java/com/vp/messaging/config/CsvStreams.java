package com.vp.messaging.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CsvStreams {

    String CSV_MESSAGE_PRODUCER_CHANNEL = "susbcriptionorders";

    @Output(CSV_MESSAGE_PRODUCER_CHANNEL)
    MessageChannel outboundCsvMessage();

}
