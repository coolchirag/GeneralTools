package com.example.nse.schedular;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.nse.StringToDoubleDeserializer;
import com.example.nse.dto.NseResponse;
import com.example.nse.service.NseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class Schedular {

	private final ObjectMapper mapper = new ObjectMapper();
	
	Gson gson;
	
	@Autowired
	private NseService nseService;

	public Schedular() {
		GsonBuilder gbuild = new GsonBuilder();
		StringToDoubleDeserializer tempObj = new StringToDoubleDeserializer();
		gbuild.registerTypeHierarchyAdapter(Double.class, tempObj);
		gson = gbuild.create();
	}

	Map<String, String> stockCodes;

	// @Scheduled(fixedDelay = 3*1000)
	public void captureNseData_old() throws ClientProtocolException, IOException, HttpException {
		// tempF1();
		// f2();
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});

		System.out.println("-------Started----------");
		RestTemplate restTemp = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
		String url_old = "https://www.nseindia.com/api/equity-stockIndices?index=NIFTY%2050";
		String url = "https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/niftyStockWatch.json";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("user-agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36");
			headers.add("pragma", "no-cache");
			headers.add("sec-fetch-dest", "document");

			headers.add("accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			headers.add("accept-encoding", "gzip, deflate, br");
			headers.add("accept-language", "en-GB,en-US;q=0.9,en;q=0.8");

			// headers.add("Cookie",
			// "ak_bmsc=AD8156BE45F216D5C7BA2FB6DB50147D312C8C97975F00005F951660930C1D27~plc4B1WprKdNvPfjDkqmJRp7uAXFd7yls3NAC1Ge1ZtKgsD44QRoxxXvgOBs9PfzfGfwTSpa+1OAJNTwsvOdZWpEptny+F+xW54q4F9HVGdXFfZIUpurftx97BAawbCiPreYfy2hCjO8ceRP4Xa9bA0UbKjcgXgBb+wj2qjaZWiKmv+AcmHmDtV++Gm7M0KBnNT6qGbFenleK5aY3dPZyD4ozFIb2Nzz2gr6EaAYguu+o+udPNk1vPI5beJBkFj3z4;
			// bm_sv=5DE4EB06FE24503CE41FADF6A1727A3D~VkKZskA6GJoKaBcnNJix5UbghWjxnuoZIxN/l8u9bYR4j6xQH4LqCDNzWxEGMt4UsPfzC6VVoAvD/yo/GjHJ2+7Di4tf0ExsWKMqbovgP/1DC8rFfFPgvCqA5/hUXhCUJD+Oyf+hO7ZOlcbjyM8CNXpnAK+UTeaarxDUDxs8hxE=");
			// headers.add("Host", "www1.nseindia.com");
			// headers.add("Referer",
			// "http://www1.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm");
			// headers.add("Sec-Fetch-Mode", "cors");
			// headers.add("Sec-Fetch-Site", "same-origin");
			// headers.add("Accept-Encoding", "gzip, deflate, br");
			HttpEntity requestEntity = new HttpEntity(headers);
			ResponseEntity<NseResponse> response = restTemp.exchange(url, HttpMethod.GET, requestEntity,
					NseResponse.class);
			System.out.println("-----------------------------------------------");
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//@Scheduled(fixedDelay = 3 * 1000)
	@Scheduled(cron = "0 1/5 9-15 ? * MON-FRI")
	public void captureNseData() throws ClientProtocolException, IOException, HttpException {
		// String uri_temp =
		// "https://www.nseindia.com/api/equity-stockIndices?index=NIFTY%2050";
		String uri = "https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/niftyStockWatch.json";
		URL url = new URL(uri);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestProperty("user-agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36");
		con.setRequestProperty("pragma", "no-cache");
		con.setRequestProperty("sec-fetch-dest", "document");

		con.setRequestProperty("accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		con.setRequestProperty("accept-encoding", "gzip, deflate, br");
		con.setRequestProperty("accept-language", "en-GB,en-US;q=0.9,en;q=0.8");
		con.setRequestMethod("GET");

		System.out.println(con.getResponseCode());
		System.out.println(con.getContentType());
		System.out.println(con.getResponseMessage());
		System.out.println(con.getContentEncoding());
		InputStream is = con.getInputStream();
		if ("gzip".equalsIgnoreCase(con.getContentEncoding())) {
			is = new GZIPInputStream(is);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(is));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String responseStr = response.toString();
		GsonBuilder gbuild = new GsonBuilder();
		StringToDoubleDeserializer tempObj = new StringToDoubleDeserializer();
		gbuild.registerTypeHierarchyAdapter(Double.class, tempObj);
		Gson gson = gbuild.create();

		NseResponse data2 = gson.fromJson(responseStr, NseResponse.class);
		System.out.println("----------------Receivevd respsonse ------------------------\n"+response.toString());
		nseService.processNseResponse(data2);
		
	}

	/*
	 * public void tempF1() throws ClientProtocolException, IOException,
	 * HttpException { CloseableHttpClient client =
	 * HttpClientBuilder.create().build(); String stocksCSVURL =
	 * "http://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/niftyStockWatch.json";
	 * CloseableHttpResponse response = client.execute(new HttpGet(stocksCSVURL));
	 * if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) { throw new
	 * HttpException("Unable to connect to NSE"); } try { this.stockCodes = new
	 * HashMap<String, String>(); BufferedReader rd = new BufferedReader(new
	 * InputStreamReader(response.getEntity().getContent())); String line =
	 * rd.readLine(); while ((line = rd.readLine()) != null) {
	 * this.stockCodes.put(line.split(",")[0], line.split(",")[1]); }
	 * System.out.println(this.stockCodes); } catch (Exception e) { throw e; } }
	 */

}
