package com.hk.outliers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hk.outliers.model.DiscountModel;
import com.hk.outliers.service.OutlierService;

@RestController
public class OutlierController {
	final static Logger logger = Logger.getLogger(OutlierController.class);
	
	@Autowired
	private OutlierService outlierService;

	
	@RequestMapping(path = "/api/v1/cleandata", method = RequestMethod.GET)
	public List<DiscountModel> findOutliers() {
		return outlierService.findCleanData();
	}
	
	

}