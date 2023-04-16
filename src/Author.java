
public class Author {
	private Integer id;
    private String fullName;
    
 // Constructor
    public Author(Integer id, 
    		String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
