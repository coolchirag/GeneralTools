package com.example.nse.dao.bean;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "nse_data")
public class NseDataBean {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "symbol")
	private String symbol;
	
	@Column(name = "open")
	private Double open;
	
	@Column(name = "high")
	private Double high;
	
	@Column(name = "low")
	private Double low;
	
	@Column(name = "ltp")
	private Double ltP;
	
	@Column(name = "trd_vol")
	private Double trdVol;
	
	@Column(name = "trd_volm")
	private Double trdVolM;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@PrePersist
	public void onCreate() {
		createdDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getLtP() {
		return ltP;
	}

	public void setLtP(Double ltP) {
		this.ltP = ltP;
	}

	public Double getTrdVol() {
		return trdVol;
	}

	public void setTrdVol(Double trdVol) {
		this.trdVol = trdVol;
	}

	public Double getTrdVolM() {
		return trdVolM;
	}

	public void setTrdVolM(Double trdVolM) {
		this.trdVolM = trdVolM;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
