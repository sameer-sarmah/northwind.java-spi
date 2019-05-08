package northwind.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity()
@Table(name="category")
public class Category {
	@Id
	private String CategoryID;
	private String CategoryName;
	private String Description;
	
    @OneToMany(mappedBy="categoryID",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Product> products;
    
	public Category(String categoryID, String categoryName, String description) {
		super();
		CategoryID = categoryID;
		CategoryName = categoryName;
		Description = description;
	}

	public Category() {
		super();
	}

	public String getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
