package category.entity;

public class Category {

	private String CategoryID;
    private String CategoryName;
    private String Description;
    
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
    
	

}
