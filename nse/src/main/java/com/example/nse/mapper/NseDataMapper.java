package com.example.nse.mapper;

import com.example.nse.dao.bean.NseDataBean;
import com.example.nse.dto.NseResponse.StockData;

public class NseDataMapper {

	public NseDataBean mappNseResponseToNseDataBean(StockData stockDataResponse) {
		NseDataBean bean = new NseDataBean();
		bean.setSymbol(stockDataResponse.getSymbol());
		bean.setOpen(stockDataResponse.getOpen());
		bean.setHigh(stockDataResponse.getHigh());
		bean.setLow(stockDataResponse.getLow());
		bean.setLtP(stockDataResponse.getLtP());
		bean.setTrdVol(stockDataResponse.getTrdVol());
		bean.setTrdVolM(stockDataResponse.getTrdVolM());
		return bean;
	}
}
