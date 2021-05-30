package com.hk.outliers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.hk.outliers.model.DiscountModel;
import com.hk.outliers.service.OutlierService;

@SpringBootTest
class OutliersServiceTest {
	
	private static OutlierService outlierService;
	
	@BeforeAll
    public static void setUp()
    {
        System.out.println("Runs before all tests in the annotation above.");
        outlierService = new OutlierService();
    }
	
	
	@Test
	public void testReadCSVFile() {
		assertNotNull(outlierService.readCSV());
		
	}
	
	
	@Test
	public void testSortedDiscountModelList() {
		assertNotNull(outlierService.getSortedDiscountList(outlierService.readCSV()));
	}
	
	@Test
	public void testMedianDiscountModelList() {
		List<DiscountModel> discountModels = outlierService.getSortedDiscountList(outlierService.readCSV());
        System.out.println(outlierService.findMedianValue(discountModels));
		assertNotNull(outlierService.findMedianValue(discountModels));
	}
	
	@Test
	public void testfindMedianPosition() {
		List<DiscountModel> discountModels = outlierService.getSortedDiscountList(outlierService.readCSV());
		BigDecimal median = outlierService.findMedianValue(discountModels);
		assertNotNull(outlierService.findMedianPosition(discountModels,median));
		System.out.println(outlierService.findMedianPosition(discountModels,median));
	}
	
	@Test
	public void testGetLowerQuaratile() {
		List<DiscountModel> discountModels = outlierService.getSortedDiscountList(outlierService.readCSV());
		System.out.println(discountModels);
		BigDecimal median = outlierService.findMedianValue(discountModels);
		int medianPosition = outlierService.findMedianPosition(discountModels,median);
		System.out.println(medianPosition);
		System.out.println(outlierService.getLowerQuaratile(discountModels,medianPosition));
	}
	
	@Test
	public void testGetUpperQuaratile() {
		List<DiscountModel> discountModels = outlierService.getSortedDiscountList(outlierService.readCSV());
		System.out.println(discountModels);
		BigDecimal median = outlierService.findMedianValue(discountModels);
		int medianPosition = outlierService.findMedianPosition(discountModels,median);
		System.out.println(medianPosition);
		System.out.println(outlierService.getUpperQuaratile(discountModels,medianPosition));
	}
	
	
	@Test
	public void testCalculateCleanData() {
		List<DiscountModel> discountModels = outlierService.getSortedDiscountList(outlierService.readCSV());
		BigDecimal median = outlierService.findMedianValue(discountModels);
		int medianPosition = outlierService.findMedianPosition(discountModels,median);
		BigDecimal upperQuartile = outlierService.getUpperQuaratile(discountModels,medianPosition);
		BigDecimal lowerQuartile = outlierService.getLowerQuaratile(discountModels,medianPosition);
		System.out.println(lowerQuartile);
		System.out.println(upperQuartile);
		System.out.println(outlierService.calculateCleanData(discountModels,lowerQuartile,upperQuartile));
		
	}
	
	@Test
	public void testfindCleanData() {
		List<DiscountModel> discountModels = outlierService.findCleanData();
		System.out.println("The total clean data size "+discountModels.size());
		System.out.println("The clean data "+discountModels);
		
	}

	

}
