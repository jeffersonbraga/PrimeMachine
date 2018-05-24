package br.com.opsocial.ejb.entity.report;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class FanPageGrowth extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Long date;
	private Integer year;
	private Integer month;
	private Integer day;
	private Long sum;
	private Integer growthRate;
	
	public FanPageGrowth() {
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}

	public Integer getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(Integer growthRate) {
		this.growthRate = growthRate;
	}
}
