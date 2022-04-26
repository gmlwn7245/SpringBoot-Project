package com.energysolution.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TotalBillDTO {
	
	//필요한 필드값
	private int BillId;
	private String date;
	private int TotalFee;
	private int WaterFee;
	private int WaterUsage;
	private int ElectricityFee;
	private int ElectricityUsage;
	
	public TotalBillDTO(int BillId, String date, int TotalFee,int WaterFee,int WaterUsage,int ElectricityFee, int ElectricityUsage) {
		this.BillId = BillId;
		this.date = date;
		this.TotalFee =TotalFee;
		this.WaterFee=WaterFee;
		this.WaterUsage=WaterUsage;
		this.ElectricityFee=ElectricityFee;
		this.ElectricityUsage=ElectricityUsage;
	}
		
	//출력
	public int getBillId() {
		return BillId;
	}
	public String getBilldate() {
		return date;
	}
	public int getTotalFee() {
		return TotalFee;
	}
	public int getWaterFee() {
		return WaterFee;
	}
	public int getWaterUsage() {
		return WaterUsage;
	}
	public int getElectricityFee() {
		return ElectricityFee;
	}
	public int getElectricityUsage() {
		return ElectricityUsage;
	}

	//입력
	public void setBillId(int BillId) {
		this.BillId = BillId;
	}
	public void setBilldate(String date) {
		this.date = date;
	}
	public void setTotalFee(int TotalFee) {
		this.TotalFee = TotalFee;
	}
	public void setWaterFee(int WaterFee) {
		this.WaterFee=WaterFee;
	}
	public void setWaterUsage(int WaterUsage) {
		this.WaterUsage=WaterUsage;
	}
	public void setElectricityFee(int ElectricityFee) {
		this.ElectricityFee=ElectricityFee;
	}
	public void setElectricityUsage(int ElectricityUsage) {
		this.ElectricityUsage=ElectricityUsage;
	}

}
