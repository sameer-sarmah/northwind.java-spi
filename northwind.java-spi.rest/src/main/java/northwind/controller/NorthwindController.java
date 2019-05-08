package northwind.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import api.ICategoryService;
import api.Source;
import category.entity.Category;
import exceptions.CoreException;

@Path("/northwind")
public class NorthwindController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Category hello() throws CoreException {
		Category beverage=new Category("1", "Beverages", "Soft drinks, coffees, teas, beers, and ales");
		return beverage;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/categories")
	public String getCategories() throws CoreException {
		Predicate<ICategoryService> predicate =(ICategoryService service) -> service.getSource().equals(Source.HTTP);
		ICategoryService categoryService = findCategoryServiceProviders(predicate).get();
		Gson gson = new Gson();
		return gson.toJson(categoryService.findAll());
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/products/{name}")
	public String getProductsByCategory(@PathParam("name") String name) throws CoreException {
		Predicate<ICategoryService> predicate =(ICategoryService service) -> service.getSource().equals(Source.HTTP);
		ICategoryService categoryService = findCategoryServiceProviders(predicate).get();
		Gson gson = new Gson();
		return gson.toJson(categoryService.findAllProductsByCategory(name));
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

	

}
