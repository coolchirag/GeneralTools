package com.example.nse.dto;

import java.util.List;

import com.example.nse.StringToDoubleDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NseResponse {

	@JsonProperty("data")
	private List<StockData> data;
	
	
	
	public List<StockData> getData() {
		return data;
	}

	public void setData(List<StockData> data) {
		this.data = data;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class StockData {
		private String symbol;
		private Double open;
		private Double high;
		private Double low;
		private Double ltP;
		private Double trdVol;
		private Double trdVolM;
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
		@Override
		public String toString() {
			return "StockData [symbol=" + symbol + ", open=" + open + ", high=" + high + ", low=" + low + ", ltP=" + ltP
					+ ", trdVol=" + trdVol + ", trdVolM=" + trdVolM + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "NseResponse [data=" + data + "]";
	}
	
	
}
