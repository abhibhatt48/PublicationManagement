import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PublicationLibrary {
    String username = "abhishekb";
	String password = "B00933993";
	Connection connect = null;
    Statement statement = null;
        

	public Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
	    Statement stmt = conn.createStatement();
	    
	    // Execute your SQL statements here
	    stmt.execute("Use abhishekb");
	    
	    return conn;
    }

	public boolean addPublication(String identifier, Map<String, String> publicationInformation) {
	    try (Connection connection = getConnection()) {
	        String sql = "INSERT INTO Publication (id,title, page_range, volume, issue, month, year, venue_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, identifier);
	        statement.setString(2, publicationInformation.get("title"));
	        statement.setString(3, publicationInformation.get("pageRange"));
	        statement.setString(4, publicationInformation.get("volume"));
	        statement.setString(5, publicationInformation.get("issue"));
	        statement.setString(6, publicationInformation.get("month"));
	        statement.setInt(7, Integer.parseInt(publicationInformation.get("year")));
	        statement.setInt(8, Integer.parseInt(publicationInformation.get("venueId")));

	        int rowsAffected = statement.executeUpdate();

	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
    
    public boolean addAuthor(String identifier, String fullName) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO Author (id, full_name) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, identifier);
            statement.setString(2, fullName);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean addPublicationAuthor(int publicationId, int authorId) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO Publication_Author (publication_id, author_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, publicationId);
            statement.setInt(2, authorId);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addReferences(String identifier, Set<String> references) {
    	try (Connection connection = getConnection()) {
            String sql = "INSERT INTO Reference (citation_string, publication_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            boolean successful = false;

            for (String reference : references) {
                statement.setString(1, reference);
                statement.setString(2, identifier);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    successful = true;
                } else {
                    successful = false;
                    break;
                }
            }

            return successful;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addVenue(String venueName, Map<String, String> venueInformation, HashSet<String> researchAreas) {
        try (Connection connection = getConnection()) {
            int publisherId = Integer.parseInt(venueInformation.getOrDefault("publisher_id", "0"));

            // Check if publisher_id exists in the Publisher table
            String checkPublisherSql = "SELECT COUNT(*) FROM Publisher WHERE id = ?";
            PreparedStatement checkPublisherStatement = connection.prepareStatement(checkPublisherSql);
            checkPublisherStatement.setInt(1, publisherId);
            ResultSet resultSet = checkPublisherStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                System.out.println("Publisher ID not found.");
                return false;
            }

            String sql = "INSERT INTO Venue (name, organization, area_of_research, editor, editor_contact, location, conference_year, publisher_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, venueName);
            statement.setString(2, venueInformation.get("organization"));
            statement.setString(3, String.join(", ", researchAreas));
            statement.setString(4, venueInformation.get("editor"));
            statement.setString(5, venueInformation.get("editor_contact"));
            statement.setString(6, venueInformation.get("location"));
            statement.setInt(7, Integer.parseInt(venueInformation.getOrDefault("conference_year", "0")));
            statement.setInt(8, publisherId);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPublisher(String identifier, Map<String, String> publisherInformation) {
    	try (Connection connection = getConnection()) {
            String sql = "INSERT INTO Publisher (id, name, contact_name, contact_email, location) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, identifier);
            statement.setString(2, publisherInformation.get("name"));
            statement.setString(3, publisherInformation.get("contact_name"));
            statement.setString(4, publisherInformation.get("contact_email"));
            statement.setString(5, publisherInformation.get("location"));
            
            int rowsAffected = statement.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addArea(String researchArea, Set<String> parentArea) {
    	try (Connection connection = getConnection()) {
            String sql = "INSERT INTO Research_Area (name, parent_area_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, researchArea);

            boolean successful = false;

            for (String parent : parentArea) {
                statement.setString(2, parent);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    successful = true;
                } else {
                    successful = false;
                    break;
                }
            }

            return successful;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, String> getPublications(String key) {
    	 Map<String, String> publication = new HashMap<>();
    	    try (Connection connection = getConnection()) {
    	        String sql = "SELECT * FROM Publication WHERE id = ?";
    	        PreparedStatement statement = connection.prepareStatement(sql);
    	        statement.setString(1, key);
    	        ResultSet resultSet = statement.executeQuery();

    	        if (resultSet.next()) {
    	            publication.put("id", resultSet.getString("id"));
    	            publication.put("title", resultSet.getString("title"));
    	            publication.put("page_range", resultSet.getString("page_range"));
    	            publication.put("volume", resultSet.getString("volume"));
    	            publication.put("issue", resultSet.getString("issue"));
    	            publication.put("month", resultSet.getString("month"));
    	            publication.put("year", String.valueOf(resultSet.getInt("year")));
    	            publication.put("venue_id", String.valueOf(resultSet.getInt("venue_id")));
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	    return publication;
    }

    public int authorCitations(String author) {
    	try (Connection connection = getConnection()) {
            String sql = "SELECT COUNT(*) FROM Publication_Author pa1 " +
                         "JOIN Reference r ON r.publication_id = pa1.publication_id " +
                         "JOIN Publication_Author pa2 ON pa2.publication_id = r.citation_string " +
                         "WHERE pa2.author_id = (SELECT id FROM Author WHERE full_name = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Set<String> seminalPapers(String area, int paperCitation, int otherCitations) {
    	try (Connection connection = getConnection()) {
            String sql = "SELECT p.id " +
                         "FROM Publication p " +
                         "JOIN Publication_Research_Area pra ON pra.publication_id = p.id " +
                         "JOIN Research_Area ra ON ra.id = pra.research_area_id " +
                         "WHERE ra.name = ? " +
                         "HAVING COUNT(SELECT * FROM Reference r WHERE r.publication_id = p.id AND r.citation_string IN (SELECT id FROM Publication)) <= ? " +
                         "AND COUNT(SELECT * FROM Reference r WHERE r.citation_string = p.id) >= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, area);
            statement.setInt(2, paperCitation);
            statement.setInt(3, otherCitations);
            ResultSet resultSet = statement.executeQuery();

            Set<String> seminalPapers = new HashSet<>();

            while (resultSet.next()) {
                seminalPapers.add(resultSet.getString("id"));
            }

            return seminalPapers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<String> collaborators(String author, int distance) {
    	Set<String> visitedAuthors = new HashSet<>();
        Set<String> currentLevelAuthors = new HashSet<>(Arrays.asList(author));
        Set<String> nextLevelAuthors = new HashSet<>();

        for (int i = 0; i <= distance; i++) {
            for (String currentAuthor : currentLevelAuthors) {
                try (Connection connection = getConnection()) {
                    String sql = "SELECT a.full_name " +
                                 "FROM Author a " +
                                 "JOIN Publication_Author pa ON pa.author_id = a.id " +
                                 "WHERE pa.publication_id IN " +
                                 "(SELECT publication_id FROM Publication_Author WHERE author_id = (SELECT id FROM Author WHERE full_name = ?))";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, currentAuthor);
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        String collaborator = resultSet.getString("full_name");
                        if (!visitedAuthors.contains(collaborator) && !currentLevelAuthors.contains(collaborator)) {
                            nextLevelAuthors.add(collaborator);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            visitedAuthors.addAll(currentLevelAuthors);
            currentLevelAuthors.clear();
            currentLevelAuthors.addAll(nextLevelAuthors);
            nextLevelAuthors.clear();
        }

        // Remove the author's name from the result set as it's not considered a collaborator
        visitedAuthors.remove(author);
        return visitedAuthors;
    }

    public Set<String> authorResearchAreas(String author, int threshold) {
		return null;
        // Return author research areas
    }
}
