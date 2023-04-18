import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;

public class PaperConversion {
    private final Connection conn;

    public PaperConversion(String url, String user, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
	    
	    // Execute your SQL statements here
	    stmt.execute("Use abhishekb");
    }

    public String getAbbreviatedAuthors(List<String> authors) {
        StringBuilder abbreviatedAuthors = new StringBuilder();
        for (String author : authors) {
            String[] names = author.split(" ");
            String lastName = names[names.length - 1];
            for (int i = 0; i < names.length - 1; i++) {
                abbreviatedAuthors.append(names[i].charAt(0)).append(". ");
            }
            abbreviatedAuthors.append(lastName).append(", ");
        }
        abbreviatedAuthors.setLength(abbreviatedAuthors.length() - 2);
        return abbreviatedAuthors.toString();
    }

    public String getIEEEReference(String key) throws SQLException {
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
        String citationString = rs.getString(1);
        String title = rs.getString(2);
        int year = rs.getInt(3);
        String venue = rs.getString(4);
        String volume = rs.getString(5);
        String issue = rs.getString(6);
        String pageRange = rs.getString(7);

        query = "SELECT a.full_name" +
                " FROM Publication_Author pa" +
                " JOIN Author a ON pa.author_id = a.id" +
                " WHERE pa.publication_id = (SELECT r.publication_id FROM Reference r WHERE r.citation_string = ?)";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, key);
        rs = stmt.executeQuery();
        List<String> authors = new ArrayList<>();
        while (rs.next()) {
            authors.add(rs.getString(1));
        }

        String abbreviatedAuthors = getAbbreviatedAuthors(authors);
        return abbreviatedAuthors + ", \"" + title + ",\" " + venue + ", vol. " + volume + ", no. " + issue + ", pp. " + pageRange + ", " + year + ".";
    }

    public String replaceCitations(String text, Map<String, Integer> citationMap) {
        for (Map.Entry<String, Integer> entry : citationMap.entrySet()) {
            String citeKey = entry.getKey();
            int refNumber = entry.getValue();
            text = text.replaceAll("\\\\cite\\{" + citeKey + "\\}", "[" + refNumber + "]");
        }
        return text;
    }

    public void main(String inputFile, String outputFile) throws IOException, SQLException {
        String text = Files.readString(Path.of(inputFile));

        Pattern pattern = Pattern.compile("\\\\cite\\{([A-Za-z]+_[0-9]{4})\\}");
        Matcher matcher = pattern.matcher(text);
        Set<String> citationKeys = new HashSet<>();
        while (matcher.find()) {
            String[] keys = matcher.group(1).split(", ");
            Collections.addAll(citationKeys, keys);
        }

        Map<String, Integer> citationMap = new HashMap<>();
        int index = 1;
        for (String key : citationKeys) {
            String ieeeReference = getIEEEReference(key);
            if (ieeeReference != null) {
                citationMap.put(key, index++);
                System.out.println("[" + (index - 1) + "] " + ieeeReference);
            }
        }

        String replacedText = replaceCitations(text, citationMap);

        Files.writeString(Path.of(outputFile), replacedText);
    }

    public static void main(String[] args) {
        String inputFile = "C:\\Users\\HP\\Documents\\GitHub\\Final_project\\abhishekb\\src\\input.txt";
        String outputFile = "output_file.txt";

        try {
            PaperConversion pc = new PaperConversion("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", "abhishekb", "B00933993");
            pc.main(inputFile, outputFile);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
