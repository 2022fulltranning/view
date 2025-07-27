package interview.view.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoindeskResponseDTO {
	@JsonProperty("幣別")
	String code;
	@JsonProperty("幣別中文名稱")
	String currencyName;
	@JsonProperty("匯率")
	double rate_float;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public double getRate_float() {
		return rate_float;
	}

	public void setRate_float(double rateFloat) {
		this.rate_float = rateFloat;
	}
}