import java.util.Set;

public class ResearchArea {
	private String name;
    private Set<ResearchArea> parentAreas;

    // Constructor
    public ResearchArea(String name, Set<ResearchArea> parentAreas) {
        this.name = name;
        this.parentAreas = parentAreas;
    }

    // Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ResearchArea> getParentAreas() {
		return parentAreas;
	}

	public void setParentAreas(Set<ResearchArea> parentAreas) {
		this.parentAreas = parentAreas;
	}

}
