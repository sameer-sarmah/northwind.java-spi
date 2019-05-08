package api;

import java.util.List;

import category.entity.Category;
import category.entity.Product;

public interface ICategoryProvider {
	String getName();
   Category getCategory();
   List<Product> getProducts();
   
}
