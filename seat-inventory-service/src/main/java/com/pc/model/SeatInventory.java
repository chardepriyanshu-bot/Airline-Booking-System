package com.pc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "airline_seat_inventory_tb")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inventoryId;

    @Column(nullable = false, unique = true)
    private Long scheduleId;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer bookedSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Version
    private Long version;
}

