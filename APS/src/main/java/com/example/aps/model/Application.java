package com.example.aps.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long appId;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_program")
    private String productProgram;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "campaign_code")
    private String campaignCode;

    @Column(name = "is_vip")
    private Boolean isVip;

    @Column(name = "app_status")
    private String appStatus;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;
}
