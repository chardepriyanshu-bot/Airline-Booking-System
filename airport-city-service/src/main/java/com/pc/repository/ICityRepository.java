package com.pc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pc.model.City;


public interface ICityRepository extends JpaRepository<City, Long> {
	
	boolean existsByCityCode(String cityCode);
	boolean existsByCityCodeAndIdNot(String cityCode, Long id);
	
	Page<City> findByCountryCodeIgnoreCase(String countryCode, Pageable pageable);
	
	
	@Query("""
		    select c from City c
		    where lower(c.name) like lower(concat('%', :keyword, '%'))
		       or lower(c.countryCode) like lower(concat('%', :keyword, '%'))
		       or lower(c.countryName) like lower(concat('%', :keyword, '%'))
		       or lower(c.regionCode) like lower(concat('%', :keyword, '%'))
		""")
		Page<City> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
