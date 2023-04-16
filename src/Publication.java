import java.util.List;

public class Publication {
	private String id;
    private String title;
    private String publicationType;
    private String journalName;
    private String conferenceName;
    private String pageRange;
    private Integer volume;
    private Integer issue;
    private Integer month;
    private Integer year;
    private List<Author> authors;
    private List<Publication> references;
    
 // Constructor
    public Publication(String id, 
    		String title, 
    		String publicationType, 
    		String journalName, 
    		String conferenceName,          
    		String pageRange, 
    		Integer volume, 
    		Integer issue, 
    		Integer month, 
    		Integer year, 
    		List<Author> authors,                       
    		List<Publication> references) {
        this.id = id;
        this.title = title;
        this.publicationType = publicationType;
        this.journalName = journalName;
        this.conferenceName = conferenceName;
        this.pageRange = pageRange;
        this.volume = volume;
        this.issue = issue;
        this.month = month;
        this.year = year;
        this.authors = authors;
        this.references = references;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getPageRange() {
        return pageRange;
    }

    public void setPageRange(String pageRange) {
        this.pageRange = pageRange;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Publication> getReferences() {
        return references;
    }

    public void setReferences(List<Publication> references) {
        this.references = references;
    }
}
