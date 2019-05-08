package api;

import java.util.List;

import category.entity.Category;
import category.entity.Product;
import exceptions.CoreException;

public interface ICategoryService {

	List<Category> findAll() throws CoreException;
	List<Product> findAllProductsByCategory(String categoryName) throws CoreException;
	Source getSource();
}
