package com.prism.UIPerf.framework;

import java.nio.charset.Charset;
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
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;

class Prism_API_Framework{
	
	private HttpClient httpClient = HttpClients.createDefault();
	Prism_API_Framework(String url, String userName, String password) {
	}
	
	private final String getAuthHeader(String userName, String password) {
		String auth = userName + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		return "Basic " + new String(encodedAuth);
	}
	
	void createTeamPurposeDirectories(String dirToCreate) {
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
	
	String getNodeAssemblyVersion(String URL, String userName, String password) {
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
				}  catch (IOException e) {
					e.printStackTrace();
				}
	            Document doc = null;
				try {
					doc = db.parse(inputStream);
				}  catch (Exception e) {
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