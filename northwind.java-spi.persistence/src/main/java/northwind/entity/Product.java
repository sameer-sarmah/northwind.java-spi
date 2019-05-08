package northwind.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	@Id 
	private String productID;
	private String productName;
	
	@JoinColumn(name="categoryID")
	@ManyToOne()
	private Category categoryID;
	private String quantityPerUnit;
	private String unitPrice;

	public Product(String productID, String productName, Category categoryID, String quantityPerUnit, String unitPrice) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.categoryID = categoryID;
		this.quantityPerUnit = quantityPerUnit;
		this.unitPrice = unitPrice;
	}

	
	
	public Product() {
		super();
	}



	public String getProductID() {
		return productID;
	}



	public void setProductID(String productID) {
		this.productID = productID;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public Category getCategoryID() {
		return categoryID;
	}



	public void setCategoryID(Category categoryID) {
		this.categoryID = categoryID;
	}



	public String getQuantityPerUnit() {
		return quantityPerUnit;
	}



	public void setQuantityPerUnit(String quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}



	public String getUnitPrice() {
		return unitPrice;
	}



	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}	
	
}
