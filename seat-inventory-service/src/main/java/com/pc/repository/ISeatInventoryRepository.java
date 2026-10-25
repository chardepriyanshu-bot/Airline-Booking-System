package com.pc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pc.model.SeatInventory;

public interface ISeatInventoryRepository extends JpaRepository<SeatInventory, Long> {
	
	 public Optional<SeatInventory> findByScheduleId(Long scheduleId);

	 public boolean existsByScheduleId(Long scheduleId);
	 
	 @Query("SELECT s FROM SeatInventory s WHERE s.scheduleId = :scheduleId")
	 Optional<SeatInventory> findByScheduleIdForUpdate(Long scheduleId);
	 
}
