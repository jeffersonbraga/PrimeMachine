package br.com.opsocial.server.utils;

import java.math.BigDecimal;

public abstract class Calc {

	public static Double calcPercentageStatistcs(Double currentValue, Double oldValue, Integer precision) {

		Double res;

		if(currentValue == 0.0 && oldValue == 0.0) {
			res = 0.0;
		} else if (oldValue == 0.0) {
			res = 100.00;
		} else {
			res = new BigDecimal(Double.valueOf((currentValue * 100) / oldValue)).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
			if(res == 100) {
				res = 0.0;
			} else {
				res = res - 100;
			}
		}

		return res;
	}


	public static Double calcPercentageNormal(Double searchValue, Double amount, Integer precision) {
		
		Double res;
		
		if (searchValue == 0.0 || amount == 0.0) {
			res = 0.0;
		} else {
			res = new BigDecimal(Double.valueOf((searchValue * 100) / amount)).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		return res;
	}
	
	public static Double calcPercentageGrowth(Double oldTotal, Double newTotal, Integer precision) {
		
		Double res;
		
		if (oldTotal == 0.0 || newTotal == 0.0) {
			res = 0.0;
		} else {
			res = new BigDecimal(((newTotal / oldTotal) -1) * 100).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		return res;
	}
	
	public static Double calcAverage(Double value, Integer numberOfValues, Integer precision) {
		
		Double res;
		
		if (value == 0.0 || numberOfValues == 0) {
			res = 0.0;
		} else {
			res = new BigDecimal(Double.valueOf(value / numberOfValues)).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		return res;
	}
	
}
