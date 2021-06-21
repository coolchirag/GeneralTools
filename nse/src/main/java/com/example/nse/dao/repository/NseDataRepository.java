package com.example.nse.dao.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nse.dao.bean.NseDataBean;

@Repository
public interface NseDataRepository extends JpaRepository<NseDataBean, Long> {

	NseDataBean findFirstBySymbolAndCreatedDateAfterOrderByCreatedDateDesc(String symbol, Date date);
	//NseDataBean findBySymbolAndCreatedDateAfterOrderByCreatedDateDescLimitedTo(String symbol, Date date, int limit);
}
