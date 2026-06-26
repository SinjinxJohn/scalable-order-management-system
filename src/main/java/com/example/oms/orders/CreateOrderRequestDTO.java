package com.example.oms.orders;

import lombok.Getter;

import java.util.List;


@Getter
public class CreateOrderRequestDTO {
    List<OrderItemRequestDTO> orderItemList;
}
