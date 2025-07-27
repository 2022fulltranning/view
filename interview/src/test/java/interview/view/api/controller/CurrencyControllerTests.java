package interview.view.api.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import interview.view.api.service.CurrencyService;
import interview.view.response.CoindeskResponse;
import interview.view.response.CurrencyNotFoundException;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CurrencyControllerTests {
	@Test
//	@Disabled
	public void testCallCoinDeskApi() {
		CurrencyService service = new CurrencyService();
		String json = service.coindeskData().toString();

		System.out.println(json);

		assert json.contains("bpi"); 
		assert json.contains("time"); 
	}
	
	@Test
	@Order(7)
//	@Disabled
	public void testQueryCurrency() {
		String json = conn("http://localhost:9100/queryCurrency", "POST","","");
		System.out.println(json);
		assert json.toString().contains("幣別相關資訊"); 
		assert json.toString().contains("更新時間"); 
	}
	
//	@Test
//	public void testQueryApi() {
//		String json = "";
//		json = conn("http://localhost:9100/findByCode", "POST","code=USD","application/x-www-form-urlencoded");
//
//		System.out.println(json);
//
//		assert json.contains("USD"); 
//		assert json.contains("美元"); 
//	}
	
	@Test
	@Order(1)
//	@Disabled
	public void testInsertApi() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("code","NTD");
			obj.put("name","新台幣");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String json = "";
		json = conn("http://localhost:9100/insert", "POST",obj.toString(),"application/json");
		System.out.println(json);
		json = conn("http://localhost:9100/findByCode", "POST","code=NTD","application/x-www-form-urlencoded");
		System.out.println(json);

		assert json.contains("NTD"); 
		assert json.contains("新台幣"); 

	}
	
	@Test
	@Order(2)
//	@Disabled
	public void testUpdateApi() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("code","NTD");
			obj.put("name","新台幣_元");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String json = conn("http://localhost:9100/update", "PUT",obj.toString(),"application/json");
		System.out.println(json);
		json = conn("http://localhost:9100/findByCode", "POST","code=NTD","application/x-www-form-urlencoded");

		System.out.println(json);

		assert json.contains("NTD"); 
		assert json.contains("新台幣_元"); 
	}
	
	@Test
	@Order(3)
//	@Disabled
	public void testDeleteApi() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("code","NTD");
			obj.put("name","新台幣_元");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String json = conn("http://localhost:9100/delete", "DELETE",obj.toString(),"application/json");
		assertTrue(json.contains("刪除成功"));
//		assertThatExceptionOfType(CurrencyNotFoundException.class).isThrownBy(() -> conn("http://localhost:9100/findByCode", "POST","code=NTD","application/x-www-form-urlencoded")).withMessageContaining("找不到幣別代碼");
	}
	
	public String conn(String urlStr, String method,String jsonInput, String type) {
		URL url;
		JSONObject json = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method); 
			
			if(!"".equals(jsonInput)) {
				conn.setRequestProperty("Content-Type", type);
		        conn.setDoOutput(true);

		        try (OutputStream os = conn.getOutputStream()) {
		            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
		            os.write(input, 0, input.length);
		        }
			}
			BufferedReader br = null;
	        if(null!=conn.getInputStream()) {
	        	br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				StringBuilder response = new StringBuilder();
				String responseLine;

				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
	            
//				System.out.println("Response: " + response.toString());
				json = new JSONObject(response.toString());
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
