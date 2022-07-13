package com.prices.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "positions")
@Data
@Accessors(chain = true)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Code")
    private Long code;
    @Column(name = "Name")
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "Action_type")
    private ActionType actionType;
    @Column(name = "Retail_price")
    private BigDecimal retailPrice;
    @Column(name = "Purchase_price")
    private BigDecimal purchasePrice;
    @Column(name = "Action_price")
    private BigDecimal actionPrice;


}
