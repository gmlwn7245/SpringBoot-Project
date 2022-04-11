package com.energysolution.domain;

import lombok.Getter;
import java.sql.Date;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchBillVO {
	
	//필요한 필드값
	private String startDate;
	private String endDate;
	private String SearchField;
	
	
	//출력
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getBilldate() {
		return SearchField;
	}
	
	//입력
	public void setUserId(String startDate) {
		this.startDate = startDate;
	}
	public void setBillId(String endDate) {
		this.endDate = endDate;
	}
	public void setBilldate(String SearchField) {
		this.SearchField = SearchField;
	}

}
