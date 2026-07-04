package com.example.oms.shared.idempotency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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
@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor
public class IdempotencyRecord {
    @Id
    private String idempotencyKey;

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    private String responseString;

    @Column(nullable = false)
    private int responseStatus;

    @CreatedDate
    private LocalDateTime createdAt;

}
