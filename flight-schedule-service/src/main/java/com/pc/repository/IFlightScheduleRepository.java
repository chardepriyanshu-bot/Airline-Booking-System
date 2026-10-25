package com.pc.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pc.model.FlightSchedule;

public interface IFlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {

		@Query("""
		    SELECT fs
		    FROM FlightSchedule fs
		    WHERE fs.departureDateTime >= :startTime
		      AND fs.departureDateTime < :endTime
		    ORDER BY fs.departureDateTime ASC
		    """)
		List<FlightSchedule> searchFlights(
		        @Param("startTime") LocalDateTime startTime,
		        @Param("endTime") LocalDateTime endTime);

}
