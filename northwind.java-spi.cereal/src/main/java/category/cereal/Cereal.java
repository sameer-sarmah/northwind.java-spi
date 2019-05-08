package category.cereal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import api.ICategoryProvider;
import api.IHttpClient;
import category.entity.Category;
import category.entity.Product;
import exceptions.CoreException;
import util.HttpMethod;

public class Cereal implements ICategoryProvider{
    private final String id="3";
    private final String name="Grains/Cereals";
    
	public Category getCategory() {
		Category beverage=new Category(id, name, "Breads, crackers, pasta, and cereal");
		return beverage;
	}

	public List<Product> getProducts() {
		String url="https://services.odata.org/Northwind/Northwind.svc/Products";
        String jsonResponse="";
        Map<String, String> queryParams=new HashMap<String, String>();
        queryParams.put("$format", "json");
        queryParams.put("$filter", "CategoryID eq 3");
        try {
        	IHttpClient httpClient = findCategoryServiceProviders();
        	Type typeToken = new TypeToken<List<Product>>() { }.getType();
            jsonResponse = httpClient.request(url, HttpMethod.GET,
            		 Collections.<String, String>emptyMap(),queryParams,null);
            JsonObject response =new Gson().fromJson(jsonResponse, JsonObject.class);
            jsonResponse = response.get("value").toString();
            return new Gson().fromJson(jsonResponse, typeToken);
        } catch (CoreException e) {
            e.printStackTrace();
        }
		return null;
	}


	public String getName() {
		return name;
	}

	private IHttpClient findCategoryServiceProviders() {
		ServiceLoader<IHttpClient> loader = ServiceLoader.load(IHttpClient.class);
		List<IHttpClient> httpClientProvider = new ArrayList<>();
		loader.forEach((provider)->{
			httpClientProvider.add(provider);
		});
		if(!httpClientProvider.isEmpty()) {
			return httpClientProvider.get(0);
		}
		return null;
	}
	
	

}
