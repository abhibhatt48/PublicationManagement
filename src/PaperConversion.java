import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;

public class PaperConversion {
    private final Connection conn;

    // Constructor: Connects to the database using the provided URL, user and password
    public PaperConversion(String url, String user, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
	    
	    // Execute your SQL statements here
	    stmt.execute("Use abhishekb");
    }

    // Generates a string of abbreviated author names in the format "A. B. Lastname, C. D. Lastname, ..."
    public String getAbbreviatedAuthors(List<String> authors) {
        StringBuilder abbreviatedAuthors = new StringBuilder();
        
        // Iterate through the list of author names
        for (String author : authors) {
            String[] names = author.split(" ");
            String lastName = names[names.length - 1];
            
            // Iterate through the first and middle names (all elements except the last)
            for (int i = 0; i < names.length - 1; i++) {
                abbreviatedAuthors.append(names[i].charAt(0)).append(". ");
            }
            abbreviatedAuthors.append(lastName).append(", ");
        }
        abbreviatedAuthors.setLength(abbreviatedAuthors.length() - 2);
        return abbreviatedAuthors.toString();
    }

    public String getIEEEReference(String key) throws SQLException {
    	
    	// SQL query to get the main publication details using the citation key
        String query = "SELECT r.citation_string, p.title, p.year, v.name, p.volume, p.issue, p.page_range" +
                " FROM Reference r" +
                " JOIN Publication p ON r.publication_id = p.id" +
                " JOIN Venue v ON p.venue_id = v.id" +
                " WHERE r.citation_string = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        // Extract publication details from the result set
        String citationString = rs.getString(1);
        String title = rs.getString(2);
        int year = rs.getInt(3);
        String venue = rs.getString(4);
        String volume = rs.getString(5);
        String issue = rs.getString(6);
        String pageRange = rs.getString(7);
        
        // SQL query to get the authors for the publication with the given citation key
        query = "SELECT a.full_name" +
                " FROM Publication_Author pa" +
                " JOIN Author a ON pa.author_id = a.id" +
                " WHERE pa.publication_id = (SELECT r.publication_id FROM Reference r WHERE r.citation_string = ?)";
        
        stmt = conn.prepareStatement(query);
        stmt.setString(1, key);
        rs = stmt.executeQuery();
        // Retrieve the authors and store them in a list
        List<String> authors = new ArrayList<>();
        while (rs.next()) {
            authors.add(rs.getString(1));
        }

        String abbreviatedAuthors = getAbbreviatedAuthors(authors);
        // Build and return the IEEE reference string
        return abbreviatedAuthors + ", \"" + title + ",\" " + venue + ", vol. " + volume + ", no. " + issue + ", pp. " + pageRange + ", " + year + ".";
    }
    
    // Replaces citation commands in the given text with their corresponding reference numbers from the citationMap
    public String replaceCitations(String text, Map<String, Integer> citationMap) {
    	
    	// Iterate through the entries in the citationMap
        for (Map.Entry<String, Integer> entry : citationMap.entrySet()) {
            String citeKey = entry.getKey();
            int refNumber = entry.getValue();
            
            // Replace all occurrences of the citation key in the text with the reference number in brackets
            text = text.replaceAll("\\\\cite\\{" + citeKey + "\\}", "[" + refNumber + "]");
        }
        return text;
    }

    public void main(String inputFile, String outputFile) throws IOException, SQLException {
        String text = Files.readString(Path.of(inputFile));

        // Define a regular expression pattern to match citation keys in the text
        Pattern pattern = Pattern.compile("\\\\cite\\{([A-Za-z]+_[0-9]{4})\\}");
        Matcher matcher = pattern.matcher(text);
        Set<String> citationKeys = new HashSet<>();
        
        // Iterate over each citation key found in the text and add it to the citationKeys set
        while (matcher.find()) {
            String[] keys = matcher.group(1).split(", ");
            Collections.addAll(citationKeys, keys);
        }

        // Initialize a map to store citation keys and their corresponding index in the reference list
        Map<String, Integer> citationMap = new HashMap<>();
        List<String> ieeeReferences = new ArrayList<>();
        int index = 1;
        
        // Iterate over each citation key in the citationKeys set
        for (String key : citationKeys) {
            String ieeeReference = getIEEEReference(key);
            if (ieeeReference != null) {
                citationMap.put(key, index++);
                ieeeReferences.add("[" + (index - 1) + "] " + ieeeReference);
            }
        }

        String replacedText = replaceCitations(text, citationMap);

        StringBuilder sb = new StringBuilder(replacedText);
        sb.append("\n\n");
        for (String ref : ieeeReferences) {
            sb.append(ref).append("\n");
        }
        
        // Write the citation map and IEEE reference list to the output file
        Files.writeString(Path.of(outputFile), sb.toString());       
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Define a fixed path where input and output files will be stored
        String fixedPath = "C:\\Users\\HP\\Documents\\GitHub\\Final_project\\abhishekb\\src\\";

        // Prompt the user to enter the name of the input file and read the input from the console
        System.out.print("Enter input file name: ");
        String inputFileName = scanner.nextLine();
        
        // Create a string variable containing the full path to the input file
        String inputFile = fixedPath + inputFileName;

        System.out.print("Enter output file name: ");
        String outputFileName = scanner.nextLine();
        String outputFile = fixedPath + outputFileName;

        // Initialize a PaperConversion object with the necessary database connection parameters
        try {
            PaperConversion pc = new PaperConversion("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", "abhishekb", "B00933993");
            pc.main(inputFile, outputFile);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
