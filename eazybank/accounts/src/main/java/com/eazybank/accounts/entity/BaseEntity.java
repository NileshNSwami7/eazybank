package com.eazybank.accounts.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class BaseEntity {
    @Column(updatable = false)
    private LocalDateTime upadatedAt;

    @Column(updatable = false)
    private String updatedBy;

    @Column(insertable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private String createdBy;
}

