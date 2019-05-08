package category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import api.ICategoryService;
import api.Source;
import category.entity.Product;
import exceptions.CoreException;

public class Driver {

	public static void main(String[] args) {
		new Driver().httpCategoryProviderSPI();
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

	public void httpCategoryProviderSPI() {
		Predicate<ICategoryService> predicate =(ICategoryService service) -> service.getSource().equals(Source.HTTP);
		ICategoryService service = findCategoryServiceProviders(predicate).get();
		try {
			List<Product> products = service.findAllProductsByCategory("Grains/Cereals");
			products.stream().forEach((product) -> {
				System.out.println(product.getProductName());
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
