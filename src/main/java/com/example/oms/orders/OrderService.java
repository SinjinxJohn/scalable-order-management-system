package com.example.oms.orders;

import com.example.oms.exceptions.InsufficientInventoryException;
import com.example.oms.exceptions.ResourceNotFoundException;
import com.example.oms.inventory.Inventory;
import com.example.oms.inventory.InventoryRepository;
import com.example.oms.product.Product;
import com.example.oms.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO){

        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for(OrderItemRequestDTO orderItemRequestDTO: createOrderRequestDTO.getOrderItemList()){
            Product product = productRepository.findById(orderItemRequestDTO.getProductId()).orElseThrow(()->new ResourceNotFoundException("Product with id not found"));
            Inventory inventory = inventoryRepository.findByProductId(orderItemRequestDTO.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Inventory with id not found"));
            int rowsChanged = inventoryRepository.reserveStock(product.getId(), orderItemRequestDTO.getQuantity(),inventory.getVersion());
            if(rowsChanged == 0){
                throw new ObjectOptimisticLockingFailureException(Inventory.class,product.getId());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequestDTO.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            BigDecimal totalPrice = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

            totalAmount = totalAmount.add(totalPrice);
            order.addOrderItem(orderItem);
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        OrderResponseDTO orderReponseDTO = modelMapper.map(order, OrderResponseDTO.class);
        orderReponseDTO.setOrderItemIds(order.getOrderItems().stream()
                .map(OrderItem::getId)
                .toList());
        
        return orderReponseDTO;


    }

    public List<OrderResponseDTO> getAllOrders(){
        List<Order> orders = orderRepository.findAllOrdersWithOrderItems();
        return orders.stream().map(order -> {
            OrderResponseDTO orderResponseDTO = modelMapper.map(order, OrderResponseDTO.class);
            if(order.getOrderItems() != null){
                orderResponseDTO.setOrderItemIds(order.getOrderItems().stream().map(OrderItem::getId).toList());
            }else{
                orderResponseDTO.setOrderItemIds(List.of());
            }
            return orderResponseDTO;
        }).toList();
    }

}
