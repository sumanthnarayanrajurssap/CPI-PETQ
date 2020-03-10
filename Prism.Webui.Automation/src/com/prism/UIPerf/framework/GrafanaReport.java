package com.prism.UIPerf.framework;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

class GrafanaReport {
	
	private HttpClient httpClient = HttpClients.createDefault();
	
	void pushAggregatedSUPAResultsToGrafana(
			
			double aggregatedValue, 
			String KPI, 
			String variant,
			String action,
			double baselineValue,
			String landscape,
			String nodeAssemblyVersion,
			String comparePurpose,
			String grafanaURL
			
			) {
		
		sendAggregatedValuesToGrafana(
				aggregatedValue,
				KPI,
				variant,
				action,
				baselineValue,
				landscape,
				nodeAssemblyVersion,
				comparePurpose,
				grafanaURL
				);
	}
	
	private void sendAggregatedValuesToGrafana(
			 
			 double aggregatedValue,
			 String KPI,
			 String variant,
			 String action,
			 double baselineValue,
			 String landscape,
			 String nodeAssemblyVersion,
			 String comparePurpose,
			 String grafanaURL
			
			 )
		{
		 
		 if(KPI.equals("EndToEndResponseTimesS")) {
			String baselinePayload =KPI+"_baseline"+","
					+ ""+"transaction="+action.replaceAll("\\s", "")+","
					+ ""+"variant="+variant+","
					+ ""+"landscape="+landscape+","
					+ ""+"testType="+"SingleUser"+","
					+ ""+"nodeAssemblyVersion="+nodeAssemblyVersion+","
					+ ""+"category="+comparePurpose+" "+"value="+baselineValue;	
			
			pushToInflux(baselinePayload,grafanaURL);
		 }
			 String payload =KPI+","
			 		+ ""+"transaction="+action.replaceAll("\\s", "")+","
			 		+ ""+"variant="+variant+","
			 		+ ""+"landscape="+landscape+","
			 		+ ""+"testType="+"SingleUser"+","
			 		+ ""+"nodeAssemblyVersion="+nodeAssemblyVersion+","
			 		+ ""+"category="+comparePurpose+" "+"value="+aggregatedValue;
			 
			 pushToInflux(payload, grafanaURL);
		}
	
	private HttpResponse pushToInflux(String payload, String grafanaURL){

		HttpPost httpPost = new HttpPost(grafanaURL);
		httpPost.addHeader("Content-Type","text/xml");
		StringEntity entity = null;
		try {
			entity = new StringEntity(payload);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		httpPost.setEntity(entity);
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
			return response;
	}
}