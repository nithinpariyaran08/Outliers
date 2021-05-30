package com.hk.outliers.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hk.outliers.model.DiscountModel;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

@Service
public class OutlierService {

	final static Logger logger = Logger.getLogger(OutlierService.class);


	/**
	 * The method is used to read the csv data.
	 * @return List of String array
	 */
	public List<String[]> readCSV() {
		List<String[]> csvData = null;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("Outliers.csv").getFile());
		CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();

		try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).withSkipLines(1).withCSVParser(parser)
				.build()) {
			csvData = reader.readAll();
		} catch (FileNotFoundException e) {
			logger.error("File not found exception", e);
		} catch (CsvException e) {
			logger.error("CsvException exception", e);
		} catch (IOException e) {
			logger.error("IOException exception", e);
		}
		return csvData;
	}
	
	/**
	 * The method will read the csv array and convert to an object 
	 * @param csvInput
	 * @return list of model
	 */
	public List<DiscountModel> getSortedDiscountList(List<String[]> csvInput) {
		List<DiscountModel> discountModels = new ArrayList<DiscountModel>();
		for (int i = 0; i < csvInput.size(); i++) {
			String[] csvRow = csvInput.get(i);
			DiscountModel discountModel = new DiscountModel();
			discountModel.setDate(csvRow[0]);
			discountModel.setAmount(new BigDecimal(csvRow[1]));
			discountModels.add(discountModel);
		}
		Collections.sort(discountModels);
		System.out.println(discountModels.size());
		return discountModels;

	}

	/**
	 * The method will help to find the median value provided the list of discount models
	 * @param discountModels
	 * @return BigDecimal
	 */
	public BigDecimal findMedianValue(List<DiscountModel> discountModels) {
		int size = discountModels.size();
		BigDecimal median = null;
		if (size % 2 == 0) {
			median = (discountModels.get(size / 2).getAmount().add(discountModels.get(size / 2 - 1).getAmount()))
					.multiply(new BigDecimal(0.5));
		} else {
			median = discountModels.get(size / 2).getAmount();
		}
		return median;
	}
	
	/**
	 * The method will find the position of the median value from the list of models
	 * @param discountModels
	 * @param median
	 * @return Integer
	 */
	public int findMedianPosition(List<DiscountModel> discountModels, BigDecimal median) {
		int count = 0;
		for (DiscountModel dscModel : discountModels) {
			if (dscModel.getAmount().compareTo(median) <= 0) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * The method will calculate the lower quartile value
	 * @param discountModels
	 * @param position
	 * @return BigDecimal
	 */
	public BigDecimal getLowerQuaratile(List<DiscountModel> discountModels, int position) {
		List<DiscountModel> lowerArray = discountModels.subList(0, position);
		BigDecimal lowerQuartile = findMedianValue(lowerArray);
		return lowerQuartile;

	}



	/**
	 * The method will calculate the upper quartile value
	 * @param discountModels
	 * @param position
	 * @return BigDecimal
	 */
	public BigDecimal getUpperQuaratile(List<DiscountModel> discountModels, int medianPosition) {
		List<DiscountModel> upperArray = discountModels.subList(medianPosition, discountModels.size());
		BigDecimal upperQuartile = findMedianValue(upperArray);
		return upperQuartile;
	}


	/**
	 * The method will calculate the outliers based on the criteria
	 * @param discountModels
	 * @param lowerQuartile
	 * @param upperQuartile
	 * @return list
	 */
	public List<DiscountModel> calculateCleanData(List<DiscountModel> discountModels, BigDecimal lowerQuartile,
			BigDecimal upperQuartile) {
		List<DiscountModel> cleanData = new ArrayList<>();
		BigDecimal iqr = new BigDecimal(1.5).multiply(upperQuartile.subtract(lowerQuartile));
		BigDecimal lowerRange = lowerQuartile.subtract(iqr);
		BigDecimal upperRange = lowerQuartile.add(iqr);

		for (DiscountModel discountModel : discountModels) {

			if (!(discountModel.getAmount().compareTo(lowerRange) < 0
					|| discountModel.getAmount().compareTo(upperRange) > 0)) {
				cleanData.add(discountModel);
			}

		}
		return cleanData;
	}
	
	/**
	 * The method plays the orchrstrator role to return the outlier objects
	 * @return
	 */
	public List<DiscountModel> findCleanData(){
		List<String[]> csvInput = readCSV();
		List<DiscountModel> discountModels = getSortedDiscountList(csvInput);
		BigDecimal median = findMedianValue(discountModels);
		int position = findMedianPosition(discountModels,median);
		BigDecimal lowerQuartile = getLowerQuaratile(discountModels,position);
		BigDecimal upperQuartile = getUpperQuaratile(discountModels,position);
		return calculateCleanData(discountModels,lowerQuartile,upperQuartile);
	}
	
}
