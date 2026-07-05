package com.example.oms.shared.idempotency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class IdempotencyService {
    private final IdempotencyRepository idempotencyRepository;
    private final ObjectMapper objectMapper;

    public void saveResponse(String key,Object responseBody, int status ){
        if(key == null|| key.trim().isEmpty()){
            return;
        }
        try{
            String jsonString = objectMapper.writeValueAsString(responseBody);
            IdempotencyRecord record = new IdempotencyRecord(key,jsonString,status);
            idempotencyRepository.save(record);
        } catch (Exception e) {
            System.err.print("Failed to serialize idempotency request" + e.getMessage());
        }

    }
}
