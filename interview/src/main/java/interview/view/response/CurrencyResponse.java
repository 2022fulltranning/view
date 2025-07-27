package interview.view.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyResponse {
	String code;
	String name;
	@JsonProperty("回傳訊息")
	String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
