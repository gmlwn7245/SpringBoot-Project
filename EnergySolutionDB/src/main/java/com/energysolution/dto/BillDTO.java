package com.energysolution.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDTO {
	private Long BillId;			//회원번호
	private Date date;				//고지서날짜 (년도와 월)
	private Long ElectricityFee;	//전기요금
	private Long HeatingFee;		//난방요금
	private Long WaterFee;			//수도요금
	//private Long totalFee;			//총요금 ☆계산 메소드 추가☆
	
	//출력
	public Long getBillId() {
		return BillId;
	}
	public Date getDate() {
		return date;
	}
	public Long getElectricityFee() {
		return ElectricityFee;		
	}
	public Long getHeatingFee() {
		return HeatingFee;
	}
	public Long getWaterFee() {
		return WaterFee;
	}
	
	//설정
	public void setBillId(Long BillId) {
		this.BillId = BillId;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setElectricityFee(Long ElectricityFee) {
		this.ElectricityFee = ElectricityFee;
	}
	public void setHeatingFee(Long HeatingFee) {
		this.HeatingFee = HeatingFee;
	}
	public void setWaterFee(Long WaterFee) {
		this.WaterFee = WaterFee;
	}
}
