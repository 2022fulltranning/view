package interview.view.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interview.view.api.entity.Currency;
import interview.view.api.repository.CurrencyDao;
import interview.view.dto.CoindeskResponseDTO;
import interview.view.dto.CurrencyRequestDTO;
import interview.view.response.CoindeskResponse;
import interview.view.response.CurrencyNotFoundException;

@Service
public class CurrencyService {
	@Autowired
	CurrencyDao currecnydao;

	public Currency findById(String code) {
		return currecnydao.findById(code).orElseThrow(() -> new CurrencyNotFoundException(code));
	}

	public void delete(CurrencyRequestDTO currencyDTO) {
		Currency currency = new Currency();
		currency.setCurrencyCode(currencyDTO.getCode());
		currency.setCurrencyName(currencyDTO.getName());
		currecnydao.delete(currency);
	}

	public void update(CurrencyRequestDTO currencyDTO) {
		Currency currency = new Currency();
		currency.setCurrencyCode(currencyDTO.getCode());
		currency.setCurrencyName(currencyDTO.getName());
		currecnydao.save(currency);
	}

	public void insert(CurrencyRequestDTO currencyDTO) {
		Currency currency = new Currency();
		currency.setCurrencyCode(currencyDTO.getCode());
		currency.setCurrencyName(currencyDTO.getName());
		currecnydao.save(currency);
	}
	
	public JSONObject coindeskData() {
		URL url;
		JSONObject json = null;
		try {
			url = new URL("https://kengp3.github.io/blog/coindesk.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET"); 
//			conn.setRequestProperty("Content-Type", "application/json");
			// 讀取回應
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuilder response = new StringBuilder();
			String responseLine;

			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}

			System.out.println("Response: " + response.toString());
			json = new JSONObject(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return json;
	}
	
	public CoindeskResponse formatCoindeskData(JSONObject coindesk) {
		CoindeskResponse response = new CoindeskResponse();
		List<CoindeskResponseDTO> allCurrency = new ArrayList<>();
		Iterator<String> keys = coindesk.getJSONObject("bpi").keys();
		while(keys.hasNext()) {
			String key = keys.next() ;
			CoindeskResponseDTO row = new CoindeskResponseDTO();
			JSONObject json = (JSONObject)coindesk.getJSONObject("bpi").get(key);
			row.setCode(json.getString("code"));
			row.setCurrencyName(findById(row.getCode()).getCurrencyName());
			row.setRate_float(json.getBigDecimal("rate_float").doubleValue());
			allCurrency.add(row);
		}
		try {
			response.setUpdatedTime(formatted(coindesk.getJSONObject("time").getString("updated")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.setCurrList(allCurrency);
		return response;
	}
	
	private String formatted(String input) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
        ZonedDateTime zdt = ZonedDateTime.parse(input, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formatted = zdt.format(outputFormatter);
        return formatted;
	}
}
