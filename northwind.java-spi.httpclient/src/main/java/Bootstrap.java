import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import api.IHttpClient;
import client.ApacheHttpClient;
import exceptions.CoreException;
import util.HttpMethod;



public class Bootstrap {
	private static final String service="https://services.odata.org/Northwind/Northwind.svc/Products";
    public static void main(String[] args) {
    	IHttpClient httpClient=new ApacheHttpClient();
        Map<String, String> queryParams=new HashMap<>();
        queryParams.put("$format", "json");
        queryParams.put("$filter", "CategoryID eq 1");

        //queryParams.put("$top", "10");
        executeHttpClient(httpClient,service ,HttpMethod.GET ,queryParams);
        
    }
    
    private static void executeHttpClient(IHttpClient httpClient,String service,HttpMethod method ,Map<String, String> queryParams) {
        try {
        	String jsonResponse = httpClient.request(service,method,
            		 Collections.<String, String>emptyMap(),queryParams,null);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(jsonResponse);
            String prettyJsonString = gson.toJson(je);
            System.out.println(prettyJsonString);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
