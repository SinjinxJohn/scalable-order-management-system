package com.example.oms.shared.idempotency;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdempotencyInterceptor implements HandlerInterceptor {// This interceptor basically handles requests before they reach the controller.
    private final IdempotencyRepository idempotencyRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if(!"POST".equalsIgnoreCase(request.getMethod())){
            return true;
        }
        String key = request.getHeader("Idempotency-Key");
        if(key == null || key.trim().isEmpty()){
            return false;
        }
        Optional<IdempotencyRecord> idempotencyRecord = idempotencyRepository.findById(key);
        if(idempotencyRecord.isPresent()){
            IdempotencyRecord record = idempotencyRecord.get();
            response.setStatus(record.getResponseStatus());
            response.setContentType("application/json");
            response.getWriter().write(record.getResponseString());
            //forces data to send immediately
            response.getWriter().flush();
            return false;
        }
        return true;
    }
}
