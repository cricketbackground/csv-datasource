package com.vp.messaging.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.vp.messaging.model.SubscriptionOrderMessage;
import com.vp.messaging.service.OrderMessageProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping(path = "uploadcsv")
@RequiredArgsConstructor
public class OrderUploaderController {

    private final OrderMessageProducerService csvMessageProducerService;

    @PostMapping(path = "order")
    public ResponseEntity<List<SubscriptionOrderMessage>> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // create csv bean reader
            CsvToBean<SubscriptionOrderMessage> csvToBean = new CsvToBeanBuilder<SubscriptionOrderMessage>(reader)
                    .withType(SubscriptionOrderMessage.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    //.withSkipLines(1)
                    .build();

            // convert `CsvToBean` object to list of users
            List<SubscriptionOrderMessage> SubscriptionOrderMessages = csvToBean.parse();
            SubscriptionOrderMessages.forEach(csvMessageProducerService::sendMessage);
            return ResponseEntity.ok(SubscriptionOrderMessages);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
