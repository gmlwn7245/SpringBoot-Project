package com.energysolution.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {
	private String UserId;
	private int BillId;

	public PaymentDTO(String UserId, int BillId) {
		this.UserId = UserId;
		this.BillId = BillId;
	}
	
	// 출력
	public String getUserId() {
		return UserId;
	}
	public int getBillId() {
		return BillId;
	}

	// 입력
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}

	public void setBillId(int BillId) {
		this.BillId = BillId;
	}

	@Override
	public String toString() {
		return "PaymentDTO [ UserId=" + UserId + ",BillId = " + BillId+"]";
	}
}
