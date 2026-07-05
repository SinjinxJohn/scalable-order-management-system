package com.example.oms.listeners;

import com.example.oms.shared.events.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    @Async
    @EventListener
    public void handlerOrderCreatedNotification(OrderCreatedEvent event){
        System.out.println("Notification listener for the thread" + Thread.currentThread().getName());
        System.out.println("Processing email dispatch for order Id:" + event.getOrderId());

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("✅ Email invoice sent successfully to: " + event.getEmail());

    }
}
