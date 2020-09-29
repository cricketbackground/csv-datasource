package com.vp.messaging.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vp.messaging.model.Employee;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class EmployeeMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendMessage(Employee employee) {
        try {
            kafkaTemplate.send("test", objectMapper.writeValueAsString(employee));
            log.info("Line with id: {} sent", employee.getId());
        } catch (JsonProcessingException e) {
            log.error("Error while serializing " + employee.toString(), e);
        }
    }
}
