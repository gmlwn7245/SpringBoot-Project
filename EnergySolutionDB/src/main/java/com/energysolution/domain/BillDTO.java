package com.energysolution.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillDTO {
	
	//필요한 필드값
	private int BillId;
	private String date;
	private int Totalfee;
	
	public BillDTO(int BillId, String date, int Totalfee) {
		this.BillId = BillId;
		this.date = date;
		this.Totalfee =Totalfee;
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

}
