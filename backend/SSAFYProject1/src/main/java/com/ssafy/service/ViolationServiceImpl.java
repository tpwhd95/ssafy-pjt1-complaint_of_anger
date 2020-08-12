package com.ssafy.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.domain.Violation;
import com.ssafy.repository.ViolationRepository;

@Service
public class ViolationServiceImpl implements ViolationService {
	
	@Autowired
	ViolationRepository repository;

	@Override
	public Violation viewViolation(int violationNo, int userNo) throws Exception {
		return repository.select(violationNo, userNo);
	}

	@Override
	public List<Violation> viewViolationList(int userNo) throws Exception {
		return repository.selectAll(userNo);
	}

	@Override
	public int addViolation(Violation violation) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String clientId = "jj121pv6s1";
		String clientSecret = "wVqpq3w1PkpJ0S5Qm2IZqHHVKDydoTW7KVq9HM7F";

		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=";
		try {
			apiURL += violation.getLng() + "," + violation.getLat() + "&output=json&orders=addr,admcode,roadaddr";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
			
			int responseCode = con.getResponseCode();
			if(responseCode == 200) br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			else br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
			
			String line;
			StringBuffer response = new StringBuffer(); // JSON
			while((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			
			JSONArray array = object.getJSONArray("results");
			JSONObject addr = object.getJSONArray("results").getJSONObject(2);
			String regionRo = addr.getJSONObject("land").getString("name");
			
			JSONObject region = addr.getJSONObject("region");
			String regionDo = region.getJSONObject("area1").getString("name");
			String regionSiGu = region.getJSONObject("area2").getString("name");
			
			System.out.println(regionDo + " " + regionSiGu + " " + regionRo);
			String address = regionDo + " " + regionSiGu + " " + regionRo;
			violation.setAddress(address);
			
			System.out.println(violation.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repository.insert(violation);
	}

	@Override
	public int modifyViolation(Violation violation) throws Exception {
		return repository.update(violation);
	}
	
	@Override
	public int modifyCondition(int violationNo, int userNo, int reportStatus) throws Exception {
		return repository.updateCondition(violationNo, userNo, reportStatus);
	}

	@Override
	public int removeViolation(int violationNo, int userNo) throws Exception {
		return repository.delete(violationNo, userNo);
	}

}
