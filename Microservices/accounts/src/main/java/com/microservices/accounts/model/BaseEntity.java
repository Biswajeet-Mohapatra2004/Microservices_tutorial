package com.microservices.accounts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@MappedSuperclass // act as a superclass for all other entities
@Getter @Setter @ToString
public class BaseEntity {

    @Column(updatable = false) /// cant be updated
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private String createdBy;

    @Column(insertable = false) // can't be
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private String updatedBy;
}
