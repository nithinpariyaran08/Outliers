package com.hk.outliers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hk.outliers.service.OutlierService;

@SpringBootTest
class OutliersControllerTests {
	
	@Mock
	private static OutlierService outlierService;
	
	
	@BeforeAll
    public static void setUp()
    {
        System.out.println("Runs before all tests in the annotation above.");
        outlierService = new OutlierService();
    }
	
	
	@Test
	public void testfindOutliers() {
		assertNotNull(outlierService.findCleanData());
	}
}
