package com.example.nse.service;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nse.dao.bean.NseDataBean;
import com.example.nse.dao.repository.NseDataRepository;
import com.example.nse.dto.NseDataResult;
import com.example.nse.dto.NseResponse;
import com.example.nse.mapper.NseDataMapper;

@Service
@Transactional
public class NseService {

	private NseDataMapper mapper = new NseDataMapper();

	@Autowired
	private NseDataRepository nseDataRepository;
	
	public NseDataResult processNseResponse(NseResponse nseResponse) {
		NseDataResult result = new NseDataResult();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		Date currentDateOnly = calendar.getTime();
		nseResponse.getData().forEach(stock -> {
			NseDataBean bean = mapper.mappNseResponseToNseDataBean(stock);
			NseDataBean priviousRecord = nseDataRepository.findFirstBySymbolAndCreatedDateAfterOrderByCreatedDateDesc(bean.getSymbol(),	currentDateOnly);
			if(priviousRecord != null) {
				bean.setTrdVol(bean.getTrdVol()-priviousRecord.getTrdVol());
				bean.setTrdVolM(bean.getTrdVolM()-priviousRecord.getTrdVolM());
			}
			nseDataRepository.save(bean);
		});
		
		return result;
	}
}
