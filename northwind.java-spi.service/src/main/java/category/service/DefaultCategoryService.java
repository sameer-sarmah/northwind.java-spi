package category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import api.ICategoryProvider;
import api.ICategoryService;
import api.Source;
import category.entity.Category;
import category.entity.Product;
import exceptions.CoreException;


public class DefaultCategoryService implements ICategoryService {


	public List<Category> findAll() throws CoreException{
		List<ICategoryProvider> categoriesProvider = findCategoryProviders();
		List<Category> categories = categoriesProvider.stream().map((provider) -> {
			return provider.getCategory();
		}).collect(Collectors.toList());
		return categories;
	}

	@Override
	public List<Product> findAllProductsByCategory(String categoryName) throws CoreException {
		List<ICategoryProvider> categoriesProvider = findCategoryProviders();
		List<ICategoryProvider> categories = categoriesProvider.stream().filter((category) -> {
			return category.getName().equalsIgnoreCase(categoryName);
		}).collect(Collectors.toList());
		List<Product> products = new ArrayList<>();
		if (categories != null && !categories.isEmpty()) {
			products = categories.get(0).getProducts();
		}
		return products;

	}

	private List<ICategoryProvider> findCategoryProviders() {
		ServiceLoader<ICategoryProvider> loader = ServiceLoader.load(ICategoryProvider.class);
		List<ICategoryProvider> categoriesProvider = new ArrayList<>();
		loader.forEach((provider)->{
			categoriesProvider.add(provider);
		});
		return categoriesProvider;
	}

	@Override
	public Source getSource() {
		return Source.HTTP;
	}

}
