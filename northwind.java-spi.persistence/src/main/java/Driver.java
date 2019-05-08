import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import api.ICategoryService;
import api.Source;
import client.ApacheHttpClient;
import exceptions.CoreException;
import northwind.entity.Category;
import northwind.entity.Product;
import northwind.repo.NorthwindDAO;
import northwind.service.DatabaseNorthwindService;
import util.HttpMethod;

public class Driver {
 
    public static void main(String[] args) {
    	new Driver().dbCategoryProviderSPI();
//    	ICategoryService service= new DatabaseNorthwindService();
//    	try {
//			List<category.entity.Product> products = service.findAllProductsByCategory("Beverages");
//			System.out.println(new Gson().toJson(products));
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
    }
    
	private Optional<ICategoryService> findCategoryServiceProviders(Predicate<ICategoryService> predicate) {
		ServiceLoader<ICategoryService> loader = ServiceLoader.load(ICategoryService.class);
		List<ICategoryService> categoryServiceProvider = new ArrayList<>();
		loader.forEach((provider) -> {
			categoryServiceProvider.add(provider);
		});
		List<ICategoryService> httpService = categoryServiceProvider.stream()
				.filter(predicate).collect(Collectors.toList());
		return httpService.stream().findFirst();
	}

	public void dbCategoryProviderSPI() {
		Predicate<ICategoryService> predicate =(ICategoryService service) -> service.getSource().equals(Source.DB);
		ICategoryService service = findCategoryServiceProviders(predicate).get();
		try {
			List<category.entity.Product> products = service.findAllProductsByCategory("Grains/Cereals");
			products.stream().forEach((product) -> {
				System.out.println(product.getProductName());
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
    
    private static void populate() {
    	ApacheHttpClient client = new ApacheHttpClient();
    	List<Category> categories = getCategories(client);
    	for(Category category : categories) {
    		List<Product> products = getProducts(client,category);	
    		category.setProducts(products);
    	}

    	NorthwindDAO dao=NorthwindDAO.getInstance();
    	dao.saveCategories(categories);
    	System.out.println("############inserted#########");
    }
    
	private static List<Product> getProducts(ApacheHttpClient httpClient,Category category ) {
		List<Product> products = new ArrayList<>();
		String url="https://services.odata.org/Northwind/Northwind.svc/Products";
        String jsonResponse="";
        Map<String, String> queryParams=new HashMap<String, String>();
        queryParams.put("$format", "json");
        queryParams.put("$filter", "CategoryID eq "+category.getCategoryID()+"");
        try {
        	Type typeToken = new TypeToken<List<Product>>() { }.getType();
            jsonResponse = httpClient.request(url, HttpMethod.GET,
            		 Collections.<String, String>emptyMap(),queryParams,null);
            JsonObject response =new Gson().fromJson(jsonResponse, JsonObject.class);
            JsonArray productsJsonArray = (JsonArray)response.get("value");
            Iterator<JsonElement> iterator=productsJsonArray.iterator();
            while(iterator.hasNext()) {
            	JsonObject object = (JsonObject)iterator.next();
            	String categoryID = getValue(object, "CategoryID");
            	String productID = getValue(object, "ProductID");
            	String productName = getValue(object, "ProductName");
            	String quantityPerUnit = getValue(object, "QuantityPerUnit");
            	String unitPrice = getValue(object, "UnitPrice");
            	products.add(new Product(productID, productName, category, quantityPerUnit, unitPrice));
            }
            return products;
        } catch (CoreException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	private static List<Category> getCategories(ApacheHttpClient httpClient) {
		List<Category> categoriesList = new ArrayList<>();
		String url="https://services.odata.org/Northwind/Northwind.svc/Categories";
        String jsonResponse="";
        Map<String, String> queryParams=new HashMap<String, String>();
        queryParams.put("$format", "json");
        queryParams.put("$top", "5");
        try {
        	Type typeToken = new TypeToken<List<Category>>() { }.getType();
            jsonResponse = httpClient.request(url, HttpMethod.GET,
            		 Collections.<String, String>emptyMap(),queryParams,null);
            JsonObject response =new Gson().fromJson(jsonResponse, JsonObject.class);
            JsonArray categories = (JsonArray)response.get("value");
            Iterator<JsonElement> iterator=categories.iterator();
            while(iterator.hasNext()) {
            	JsonObject object = (JsonObject)iterator.next();
            	String categoryID = getValue(object, "CategoryID");
            	String categoryName = getValue(object, "CategoryName");
            	String description = getValue(object, "Description");
            	categoriesList.add(new Category(categoryID, categoryName, description));
            }
            return categoriesList;
        } catch (CoreException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	private static String getValue(JsonObject object,String keyName){
    	JsonElement element = object.get(keyName);
    	return element.getAsString(); 
	}
}