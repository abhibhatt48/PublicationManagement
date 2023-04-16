import java.util.Set;

public class Venue {
	private String name;
    private String venueType;
    private String publisherId;
    private String editor;
    private String editorContact;
    private String location;
    private Set<ResearchArea> researchAreas;
    
    // Constructor
	public Venue(String name, 
			String venueType, 
			String publisherId, 
			String editor,
            String editorContact, 
            String location, 
            Set<ResearchArea> researchAreas) {
   this.name = name;
   this.venueType = venueType;
   this.publisherId = publisherId;
   this.editor = editor;
   this.editorContact = editorContact;
   this.location = location;
   this.researchAreas = researchAreas;
}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVenueType() {
		return venueType;
	}

	public void setVenueType(String venueType) {
		this.venueType = venueType;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getEditorContact() {
		return editorContact;
	}

	public void setEditorContact(String editorContact) {
		this.editorContact = editorContact;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<ResearchArea> getResearchAreas() {
		return researchAreas;
	}

	public void setResearchAreas(Set<ResearchArea> researchAreas) {
		this.researchAreas = researchAreas;
	}
	

}
