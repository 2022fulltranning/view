package interview.view.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import interview.view.dto.CoindeskResponseDTO;

public class CoindeskResponse {
	@JsonProperty("幣別相關資訊")
	List<CoindeskResponseDTO> currList = new ArrayList<>();
	@JsonProperty("更新時間")
	String updatedTime;
	
	public List<CoindeskResponseDTO> getCurrList() {
		return currList;
	}
	public void setCurrList(List<CoindeskResponseDTO> currList) {
		this.currList = currList;
	}
	public String getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
}
