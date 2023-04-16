
public class Publisher {
	private String id;
    private String contactName;
    private String contactEmail;
    private String location;

    // Constructor
    public Publisher(String id, String contactName, String contactEmail, String location) {
        this.id = id;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.location = location;
    }

    // Getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
