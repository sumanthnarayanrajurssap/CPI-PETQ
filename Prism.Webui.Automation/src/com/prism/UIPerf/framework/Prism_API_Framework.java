package com.prism.UIPerf.framework;

import java.nio.charset.Charset;
import org.apache.http.client.methods.HttpRequestBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public 
	class 
		Prism_API_Framework{
	
	private HttpClient httpClient = HttpClients.createDefault();
	protected String URL;
	protected String USERNAME;
	protected String PASSWORD;
	protected String USER_AGENT="Mozilla/5.0";
	public String mplResponse;
	boolean flag = false;

	public Prism_API_Framework(String url, String userName, String password) {
		this.URL = url;
		this.USERNAME = userName;
		this.PASSWORD = password;
		setProxyInHTTPClient(httpClient);
	}
	
	private void setProxyInHTTPClient(HttpClient httpClient) {
		if (!URL.contains("127.0.0.1")) {
			HttpHost proxy = new HttpHost("proxy", 8080);
			httpClient = HttpClientBuilder.create().useSystemProperties().setProxy(proxy).build();
		}
	}

	public Prism_API_Framework() {
		if(URL!=null) {
			setProxyInHTTPClient(httpClient);
		}
	}
	public HttpClient getHttpClient() {
		if (httpClient == null)
			httpClient = HttpClients.createDefault();

		return httpClient;
	}
	
	public void setUSER_AGENT(String userAgent) {
		this.USER_AGENT = userAgent;
	}

	public void setURL(String url) {
		this.URL = url;
	}

	public void setUserName(String userName) {
		this.USERNAME = userName;
	}

	public void setPassword(String password) {
		this.PASSWORD = password;
	}

	private String fetchCSRFToken() {
		return fetchCSRFToken(URL, USERNAME, PASSWORD);
	}
	
	private String fetchCSRFToken(String URL, String USERNAME, String PASSWORD) {
		HttpGet request = new HttpGet(URL);
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("Authorization", getAuthHeader(USERNAME, PASSWORD));
		request.addHeader("x-csrf-token", "fetch");
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String token = response.getFirstHeader("x-csrf-token").getValue();
		if (response.getEntity() != null) {
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return token;
	}
	
	public final String getAuthHeader(String userName, String password) {
		String auth = userName + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		return "Basic " + new String(encodedAuth);
	}
	
	public void addRequestHeader(HttpRequestBase request) {
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("x-csrf-token", fetchCSRFToken());
		request.addHeader("Accept", "*/*");
	}
	
	public void addRequestHeader(HttpRequestBase request, String URL) {
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("x-csrf-token", fetchCSRFToken(URL, USERNAME, PASSWORD));
		request.addHeader("Accept", "*/*");
	}
	
	public String getResposeBody(HttpResponse response) {
		String responseBody = null;
		try {
			responseBody = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (response.getEntity() != null) {
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseBody;
	}
	
	public String getDataFromURL(String url) {
		HttpGet request = new HttpGet(url);
		try {
			addRequestHeader(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getResposeBody(response);
	}
	
	public JsonElement convertStringTOJson(String data) {
		final JsonParser jsonParser = new JsonParser();
				JsonElement element = jsonParser.parse(data);
				return element;
	}
	
	public String getProcessID(String providerSubAccountID, String tmnName, String userName, String password) {
		String processIDURL = "https://api.int.sap.eu2.hana.ondemand.com/monitoring/v1/accounts/"+providerSubAccountID+"/apps/"+tmnName+"/state";
		HttpGet request = new HttpGet(processIDURL);
		request.addHeader("Authorization", getAuthHeader(userName, password));
			HttpResponse response = null;
			try {
				response = httpClient.execute(request);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String body = getResposeBody(response);
			body =body.replace("[", "");
			body = body.replace("]", "");
			JSONObject jsonObject = new JSONObject(body);
			return jsonObject.getJSONObject("processes").getString("process");
	}
	
	public void createTeamPurposeDirectories(String dirToCreate) {
		 try {
				Process p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "%CD%\\Resources\\UIPerformance\\BatchFiles\\createTeamPurposeIfNotExist.bat "+dirToCreate});
				p.waitFor();
				BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
	         String line; 
	         while((line = reader.readLine()) != null) { 
	             System.out.println(line);
	         }
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public String getNodeAssemblyVersion(String URL, String userName, String password) {
		if(userName.contentEquals("P000306")) {
			userName = "P1369096596";
			password = "Abcd1234";
		}
		String xPath;
		HttpResponse response = null;
	
		String nodeClusterURL = URL.replace("/itspaces","")+"/Operations/com.sap.it.nm.commands.node.GetNodesCommand";
	    response = getResponse(userName, password, nodeClusterURL);
	    xPath = "/com.sap.it.nm.commands.node.GetNodesCommandResponse/nodes/@id";
		String nodeAssemblyID = null;
		try {
			nodeAssemblyID = parseResponse(xPath,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    String nodeAssemblyURL =  URL.replace("/itspaces","")+"/Operations/com.sap.it.op.srv.commands.dashboard.NodeProcessStatisticCommand?nodeId="+nodeAssemblyID;
	    response = getResponse(userName, password, nodeAssemblyURL);
	    xPath = "/com.sap.it.op.srv.commands.dashboard.NodeProcessStatisticResponse/info/version/text()";
	    String nodeAssemblyVersion = null;
		try {
			nodeAssemblyVersion = parseResponse(xPath,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return nodeAssemblyVersion;
	}
	
	private HttpResponse getResponse(String userName, String password, String nodeAssemblyURL) {
		HttpGet request;
		request = new HttpGet(nodeAssemblyURL);
		request.addHeader("Authorization", getAuthHeader(userName, password));
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return response;
	}
	
	private String parseResponse(String xPath,HttpResponse responseBody) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
        String id = null;
	        if (responseBody != null) {
	            InputStream inputStream = null;
				try {
					inputStream = responseBody.getEntity().getContent();
				} catch (UnsupportedOperationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            Document doc = null;
				try {
					doc = db.parse(inputStream);
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            doc.getDocumentElement().normalize();
	        XPathFactory xpf = XPathFactory.newInstance();
	        XPath xpath = xpf.newXPath();
	        
	        try {
				id = (String) xpath.evaluate(xPath, doc, XPathConstants.STRING);
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
	        }
		return id;
	}
}