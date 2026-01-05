package com.eazybytes.eazystore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Size(max = 150)
    @NotNull
    @Column(name = "street", nullable = false, length = 150)
    private String street;

    @Size(max = 100)
    @NotNull
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Size(max = 100)
    @NotNull
    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @Size(max = 20)
    @NotNull
    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Size(max = 100)
    @NotNull
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @CreationTimestamp
    private Instant createdAt;

    @CreatedBy
    @Column(name = "created_by", length = 20, updatable = false, nullable = false)
    private String createdBy;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private Instant updatedAt;

    @Column(name = "updated_by", length = 20, insertable = false)
    @LastModifiedBy
    private String updatedBy;

}