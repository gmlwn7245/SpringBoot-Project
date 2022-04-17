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
	private long Totalfee;
	private long Waterfee;
	private long Heatingfee;
	private long Electricityfee;
	
	public TotalBillDTO(int BillId, String date, long Totalfee,long Waterfee,long Heatingfee,long Electricityfee) {
		this.BillId = BillId;
		this.date = date;
		this.Totalfee =Totalfee;
		this.Electricityfee=Electricityfee;
		this.Heatingfee=Heatingfee;
		this.Waterfee=Waterfee;
	}
		
	//출력
	public int getBillId() {
		return BillId;
	}
	public String getBilldate() {
		return date;
	}
	
	public long getTotalfee() {
		return Totalfee;
	}
	
	public long getWaterFee() {
		return Waterfee;
	}
	public long getHeatingFee() {
		return Heatingfee;
	}
	public long getElectricityFee() {
		return Electricityfee;
	}

	//입력
	public void setBillId(int BillId) {
		this.BillId = BillId;
	}
	public void setBilldate(String date) {
		this.date = date;
	}
	public void setTotalfee(int Totalfee) {
		this.Totalfee = Totalfee;
	}	
	public void setElectricityfee(int Electricityfee) {
		this.Electricityfee=Electricityfee;
	}
	public void setHeatingfee(int Heatingfee) {
		this.Heatingfee=Heatingfee;
	}
	public void setWaterfee(int Waterfee) {
		this.Waterfee=Waterfee;
	}

}
