import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        PublicationLibrary library = new PublicationLibrary();
        // Create a new Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Publication Library!");

        // Use a while loop to display the main menu until the user chooses to exit
        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Add an author");
            System.out.println("2. Add a publisher");
            System.out.println("3. Add a venue");
            System.out.println("4. Add a publication");
            System.out.println("5. Add a research area");
            System.out.println("6. Add an author to a publication");
            System.out.println("7. Add a refrences to a publication"); 
            System.out.println("8. Display all publications");
            System.out.println("9. Display all aurther's citations");
            System.out.println("10. Seminal paper refrences");
            System.out.println("11. Research collaborators");
            System.out.println("12. Exit");
            System.out.print("Enter the number of your choice: ");

            // Read the user's choice from the input
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            // Check the user's choice and execute the corresponding action
            if (choice == 1) {
            	System.out.print("Enter author's identifier: ");
                String identifierString = scanner.nextLine();
                int identifier = 0;
                try {
                    identifier = Integer.parseInt(identifierString);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Identifier must be an integer.");
                    return;
                }
                String fullName;
                while (true) {
                    System.out.print("Enter author's full name: ");
                    fullName = scanner.nextLine();
                    if (fullName != null && !fullName.trim().isEmpty()) {
                        break;
                    }
                    System.out.println("Invalid input. Name can't be null or empty.");
                }
                 // Add the author to the library and print a success message if successful
                 boolean success = library.addAuthor(identifierString, fullName);
                 if(success) {
                 System.out.println("Author added successfully.");
                 }else {
                	 System.out.println("Failed to add author.");
                 }
            } else if (choice == 2) {
            	System.out.print("Enter pulisher's identifier: ");
                String identifierString = scanner.nextLine();
                int identifier = 0;
                try {
                    identifier = Integer.parseInt(identifierString);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Identifier must be an integer.");
                    return;
                }
                Map<String, String> publisherInformation = new HashMap<>();
                
                System.out.print("Enter publisher's name: ");
                publisherInformation.put("name", scanner.nextLine());
                System.out.print("Enter contact name: ");
                publisherInformation.put("contact_name", scanner.nextLine());
                String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                while (true) {
                    System.out.print("Enter contact email: ");
                    String contactEmail = scanner.nextLine();
                    if (Pattern.matches(emailPattern, contactEmail)) {
                        publisherInformation.put("contact_email", contactEmail);
                        break;
                    } else {
                        System.out.println("Invalid email. Please enter a valid email address.");
                    }
                }

                System.out.print("Enter publisher's location: ");
                publisherInformation.put("location", scanner.nextLine());
                
                // Add the publisher to the library and print a success message if successful
                boolean success =library.addPublisher(identifierString, publisherInformation);
                if(success) {
                System.out.println("Publisher added successfully.");
                } else {
                	System.out.println("Failed to add publisher.");                	
                }
                
            }else if (choice == 3) {
                System.out.print("Enter venue's name: ");
                String venueName = scanner.nextLine();

                Map<String, String> venueInformation = new HashMap<>();
                System.out.print("Enter organization: ");
                venueInformation.put("organization", scanner.nextLine());
                System.out.print("Enter area of research: ");
                venueInformation.put("area_of_research", scanner.nextLine());
                System.out.print("Enter editor's name: ");
                venueInformation.put("editor", scanner.nextLine());
                String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                while (true) {
                    System.out.print("Enter editor's email: ");
                    String editorContact = scanner.nextLine();
                    if (Pattern.matches(emailPattern, editorContact)) {
                        venueInformation.put("editor_contact", editorContact);
                        break;
                    } else {
                        System.out.println("Invalid email. Please enter a valid email address.");
                    }
                }
                System.out.print("Enter venue's location: ");
                venueInformation.put("location", scanner.nextLine());

                int conferenceYear = 0;
                while (true) {
                    System.out.print("Enter conference year: ");
                    String input = scanner.nextLine();
                    if (input.isEmpty()) {
                        break;
                    }
                    try {
                        conferenceYear = Integer.parseInt(input);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric value.");
                    }
                }
                venueInformation.put("conference_year", Integer.toString(conferenceYear));

                int publisherId = 0;
                while (true) {
                    System.out.print("Enter publisher ID: ");
                    String input = scanner.nextLine();
                    if (input.isEmpty()) {
                        break;
                    }
                    try {
                        publisherId = Integer.parseInt(input);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric value.");
                    }
                }
                venueInformation.put("publisher_id", Integer.toString(publisherId));

                HashSet<String> researchAreas = new HashSet<>();
                while (true) {
                    System.out.print("Enter research area (or leave blank to finish): ");
                    String researchArea = scanner.nextLine();
                    if (researchArea.isEmpty()) {
                        break;
                    }
                    researchAreas.add(researchArea);
                }
                
                // Add the Venue to the library and print a success message if successful
                boolean success = library.addVenue(venueName, venueInformation, researchAreas);
                if (success) {
                    System.out.println("Venue added successfully.");
                } else {
                    System.out.println("Failed to add venue.");
                }                            
            }else if (choice == 4) {
                System.out.print("Enter publication's identifier: ");               
                String identifierString = scanner.nextLine();
                int identifier = 0;
                try {
                    identifier = Integer.parseInt(identifierString);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Identifier must be an integer.");
                    return;
                }
                Map<String, String> publicationInformation = new HashMap<>();

                System.out.print("Enter publication's title: ");
                publicationInformation.put("title", scanner.nextLine());
                System.out.print("Enter page range: ");
                publicationInformation.put("pageRange", scanner.nextLine());
                System.out.print("Enter volume: ");
                publicationInformation.put("volume", scanner.nextLine());
                System.out.print("Enter issue: ");
                publicationInformation.put("issue", scanner.nextLine());
                System.out.print("Enter publication month: ");
                publicationInformation.put("month", scanner.nextLine());
                System.out.print("Enter publication year: ");
                publicationInformation.put("year", scanner.nextLine());
                System.out.print("Enter venue ID: ");
                int venueId = 0;
                while (true) {
                    String venueIdString = scanner.nextLine();
                    try {
                        venueId = Integer.parseInt(venueIdString);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Venue ID must be an integer.");
                    }
                }
                publicationInformation.put("venueId", Integer.toString(venueId));

                // Add the publication to the library and print a success message if successful
                boolean success = library.addPublication(identifierString, publicationInformation);
                if (success) {
                    System.out.println("Publication added successfully.");
                } else {
                    System.out.println("Failed to add publication.");
                }
            }else if (choice == 5) {
                System.out.print("Enter research area's name: ");
                String researchAreaName = scanner.nextLine();

                Set<String> parentAreaIds = new HashSet<>();

                while (true) {
                    System.out.print("Enter parent area ID (or leave blank for no parent): ");
                    String parentAreaId = scanner.nextLine();
                    if (parentAreaId.isEmpty()) {
                        break;
                    }
                    parentAreaIds.add(parentAreaId);
                }

                // Add the research area and relation with parent area to the library and print a success message if successful
                boolean success = library.addArea(researchAreaName, parentAreaIds);
                if (success) {
                    System.out.println("Research area added successfully.");
                } else {
                    System.out.println("Failed to add research area.");
                }
            }else if (choice == 6) {
                System.out.print("Enter publication ID: ");
                int publicationId = scanner.nextInt();
                System.out.print("Enter author ID: ");
                int authorId = scanner.nextInt();
                scanner.nextLine(); // Clear newline character

                // Add the relation between publication and author to the library and print a success message if successful
                boolean success = library.addPublicationAuthor(publicationId, authorId);
                if (success) {
                    System.out.println("Author added to publication successfully.");
                } else {
                    System.out.println("Failed to add author to publication.");
                }
            }else if (choice == 7) {
                System.out.print("Enter publication identifier: ");
                String publicationId  = scanner.nextLine();
                int identifier = 0;
                try {
                    identifier = Integer.parseInt(publicationId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Identifier must be an integer.");
                    return;
                }
                Set<String> references = new HashSet<>();
                while (true) {
                    System.out.print("Enter reference (e.x: lastName_2023): ");
                    String reference = scanner.nextLine();
                    if (reference.isEmpty()) {
                        break;
                    }
                    references.add(reference);
                }

                // Add the reference to the library and print a success message if successful
                boolean success = library.addReferences(publicationId, references);
                if (success) {
                    System.out.println("References added successfully.");
                } else {
                    System.out.println("Failed to add references.");
                }
            }else if (choice == 8) {
            	//  Enter an publication id to get a specific data.
            	System.out.print("Enter publication ID: ");        	
            	String publicationId  = scanner.nextLine();
                int identifier = 0;
                try {
                    identifier = Integer.parseInt(publicationId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Identifier must be an integer.");
                    return;
                }
            	
            	Map<String, String> publication = library.getPublications(publicationId);
            	if (publication.isEmpty()) {
            	    System.out.println("Publication not found.");
            	} else {
            	    System.out.println("Publication found: ");
            	    System.out.println("ID: " + publication.get("id"));
            	    System.out.println("Title: " + publication.get("title"));
            	    System.out.println("Page Range: " + publication.get("page_range"));
            	    System.out.println("Volume: " + publication.get("volume"));
            	    System.out.println("Issue: " + publication.get("issue"));
            	    System.out.println("Month: " + publication.get("month"));
            	    System.out.println("Year: " + publication.get("year"));
            	    System.out.println("Venue ID: " + publication.get("venue_id"));
            	}                
            }else if (choice == 9) {
                System.out.print("Enter author's name: ");
                String authorName = scanner.nextLine();
                int citationCount = library.authorCitations(authorName);
                System.out.println(authorName + " has " + citationCount + " citations.");
            }else if (choice == 10) {
                System.out.print("Enter area of research: ");
                String area = scanner.nextLine();

                int paperCitation = 0;
                while (true) {
                    System.out.print("Enter minimum number of citations for seminal papers: ");
                    String input = scanner.nextLine();
                    if (input.isEmpty()) {
                        break;
                    }
                    try {
                        paperCitation = Integer.parseInt(input);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric value.");
                    }
                }

                int otherCitations = 0;
                while (true) {
                    System.out.print("Enter minimum number of other citations: ");
                    String input = scanner.nextLine();
                    if (input.isEmpty()) {
                        break;
                    }
                    try {
                        otherCitations = Integer.parseInt(input);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric value.");
                    }
                }

                Set<String> seminalPapers = library.seminalPapers(area, paperCitation, otherCitations);

                if (seminalPapers != null && seminalPapers.size() > 0) {
                    System.out.println("Seminal papers for " + area + ":");
                    for (String paperId : seminalPapers) {
                        System.out.println(paperId);
                    }
                } else {
                    System.out.println("No seminal papers found for " + area + ".");
                }
            }else if (choice == 11) {
                System.out.print("Enter author's name: ");
                String author = scanner.nextLine();
                System.out.print("Enter distance: ");
                int distance = scanner.nextInt();
                scanner.nextLine(); // Clear newline character

                Set<String> collaborators = library.collaborators(author, distance);
                System.out.println("Collaborators:");
                for (String collaborator : collaborators) {
                    System.out.println(collaborator);
                }
            }else if (choice == 12) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
