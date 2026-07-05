package com.example.oms.shared.idempotency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdempotencyRecord {
    @Id
    private String idempotencyKey;

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    private String responseString;

    @Column(nullable = false)
    private int responseStatus;

    @Column(updatable = false,nullable = true)
    private LocalDateTime createdAt;

    IdempotencyRecord(String idempotencyKey,String responseString,int responseStatus){
        this.idempotencyKey = idempotencyKey;
        this.responseString = responseString;
        this.responseStatus = responseStatus;
        this.createdAt = LocalDateTime.now();
    }

}
