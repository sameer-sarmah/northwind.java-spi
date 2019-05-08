package northwind.service;

import java.util.List;
import java.util.stream.Collectors;

import api.ICategoryService;
import api.Source;
import category.entity.Category;
import category.entity.Product;
import exceptions.CoreException;
import northwind.repo.NorthwindDAO;


public class DatabaseNorthwindService implements ICategoryService {


	public List<Category> findAll() throws CoreException{
		NorthwindDAO dao=NorthwindDAO.getInstance();
		List<northwind.entity.Category> categories = dao.findAllCategories();
		return categories.stream().map(this::covert).collect(Collectors.toList());
	}

	@Override
	public List<Product> findAllProductsByCategory(String categoryName) throws CoreException {
		NorthwindDAO dao=NorthwindDAO.getInstance();
		List<northwind.entity.Product> products = dao.findAllProductsByCategory(categoryName);
		return products.stream().map(this::convert).collect(Collectors.toList());

	}

   private Category covert(northwind.entity.Category category) {
	   Category dto = new Category(category.getCategoryID(), category.getCategoryName(), category.getDescription());
	   return dto;
   }
   
   private Product convert(northwind.entity.Product product) {
	   Product dto = new Product(product.getProductID(), product.getProductName(), product.getCategoryID().getCategoryID(), product.getQuantityPerUnit(), product.getUnitPrice());
	   return dto;
   }

	@Override
	public Source getSource() {
		return Source.DB;
	}

}
