package interview.view.api.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import interview.view.api.entity.Currency;
import interview.view.api.service.CurrencyService;
import interview.view.dto.CurrencyRequestDTO;
import interview.view.response.CoindeskResponse;
import interview.view.response.CurrencyResponse;

@RestController
public class CurrencyController {
	@Autowired
	CurrencyService currencySer;

	@GetMapping("/coindesk")
	public String findCoindesk() {
		JSONObject coindesk = currencySer.coindeskData();
		return coindesk.toString();
	}

	@PostMapping("/findByCode")
	public CurrencyResponse findByCode(@RequestParam String code) {
		Currency currency = currencySer.findById(code);
		CurrencyResponse result = new CurrencyResponse();
		result.setCode(currency.getCurrencyCode());
		result.setName(currency.getCurrencyName());
		result.setMsg("查詢成功");
		return result;
	}
	
	@PostMapping("/insert")
	public CurrencyResponse insert(@RequestBody CurrencyRequestDTO currency) {
		 currencySer.insert(currency);
		 CurrencyResponse result = new CurrencyResponse();
		 result.setCode(currency.getCode());
		 result.setName(currency.getName());
		 result.setMsg("新增成功");
		 return result;
	}
	
	@DeleteMapping("/delete")
	public CurrencyResponse delete(@RequestBody CurrencyRequestDTO currency) {
		 currencySer.delete(currency);
		 CurrencyResponse result = new CurrencyResponse();
		 result.setCode(currency.getCode());
		 result.setName(currency.getName());
		 result.setMsg("刪除成功");
		 return result;
	}
	
	@PutMapping("/update")
	public CurrencyResponse update(@RequestBody CurrencyRequestDTO currency) {
		 currencySer.update(currency);
		 CurrencyResponse result = new CurrencyResponse();
		 result.setCode(currency.getCode());
		 result.setName(currency.getName());
		 result.setMsg("更新成功");
		 return result;
	}
	
	@PostMapping("/queryCurrency")
	public CoindeskResponse findCurrency() {
		JSONObject coindesk = currencySer.coindeskData();
		CoindeskResponse response = currencySer.formatCoindeskData(coindesk);
		return response;
	}
}
