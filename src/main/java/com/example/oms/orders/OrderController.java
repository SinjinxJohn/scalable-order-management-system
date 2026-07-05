package com.example.oms.orders;


import com.example.oms.shared.idempotency.IdempotencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final IdempotencyService idempotencyService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid CreateOrderRequestDTO createOrderRequestDTO,
                                                        @RequestHeader(value = "Idempotency-key",required = true) String idempotencyKey){

        OrderResponseDTO orderResponseDTO = orderService.createOrder(createOrderRequestDTO);
        if(idempotencyKey != null && !idempotencyKey.trim().isEmpty()){
            idempotencyService.saveResponse(idempotencyKey,orderResponseDTO,HttpStatus.CREATED.value());
        }
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
