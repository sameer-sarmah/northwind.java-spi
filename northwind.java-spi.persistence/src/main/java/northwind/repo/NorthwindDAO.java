package northwind.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import northwind.entity.Category;
import northwind.entity.Product;

public class NorthwindDAO {
	
	private static final String PERSISTENCE_UNIT_NAME = "northwind";
	private static NorthwindDAO dao ;
	private static EntityManagerFactory factory;
	
	private NorthwindDAO() {}
	
	public static NorthwindDAO getInstance() {
		if(dao == null || factory == null) {
			dao = new NorthwindDAO();
			try {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return dao;
	}
	
	public List<Category> findAllCategories(){
		EntityManager manager = factory.createEntityManager();
		TypedQuery<Category> query = manager.createQuery("select c from Category c",Category.class);
		List<Category> categories = query.getResultList();
		manager.close();
		return categories;
	}

	public Product findProductById(String id) {
		EntityManager manager = factory.createEntityManager();
		TypedQuery<Product> query = manager.createQuery("select p from Product p where p.ProductID = :id",Product.class);
		query.setParameter("id", id);
		Product product = query.getSingleResult();	
		manager.close();
		return product;
	}
	
	public Category findCategoryById(String id) {
		EntityManager manager = factory.createEntityManager();
		TypedQuery<Category> query = manager.createQuery("select c from Category c where c.CategoryID = :id",Category.class);
		query.setParameter("id", id);
		Category category = query.getSingleResult();	
		manager.close();
		return category;
	}
	
	public List<Product> findAllProductsByCategory(String categoryName){
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("select c.products from Category c where c.CategoryName = :name");
		query.setParameter("name", categoryName);
		List<Product> products = query.getResultList();
		manager.close();
		return products;
	}
	
	public void saveCategory(Category category) {
		try {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(category);
		manager.getTransaction().commit();
		manager.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void saveCategories(List<Category> categories) {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		for(Category category : categories) {
			manager.persist(category);
		}
		manager.getTransaction().commit();
		manager.close();
	}
	
}
