package com.energysolution.dto;

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
	private int TotalFee;
	
	public BillDTO(String date, int TotalFee) {
		this.date = date;
		this.TotalFee =TotalFee;
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

}
