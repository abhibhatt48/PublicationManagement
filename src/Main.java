import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        PublicationLibrary library = new PublicationLibrary();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Publication Library!");

        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Add an author");
            System.out.println("2. Add a publisher");
            System.out.println("3. Add a venue");
            System.out.println("4. Add a publication");
            System.out.println("5. Exit");
            System.out.print("Enter the number of your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            if (choice == 1) {
            	 System.out.print("Enter author's identifier: ");
                 String identifier = scanner.nextLine();
                 System.out.print("Enter author's full name: ");
                 String fullName = scanner.nextLine();
                 library.addAuthor(identifier, fullName);
                 System.out.println("Author added successfully.");
                 
            } else if (choice == 2) {
            	System.out.print("Enter publisher's identifier: ");
                String identifier = scanner.nextLine();
                Map<String, String> publisherInformation = new HashMap<>();
                
                System.out.print("Enter publisher's name: ");
                publisherInformation.put("name", scanner.nextLine());
                System.out.print("Enter contact name: ");
                publisherInformation.put("contact_name", scanner.nextLine());
                System.out.print("Enter contact email: ");
                publisherInformation.put("contact_email", scanner.nextLine());
                System.out.print("Enter publisher's location: ");
                publisherInformation.put("location", scanner.nextLine());
                library.addPublisher(identifier, publisherInformation);
                System.out.println("Publisher added successfully.");
                
            } else if (choice == 3) {
            	System.out.print("Enter venue's name: ");
                String venueName = scanner.nextLine();
                
                Map<String, String> venueInformation = new HashMap<>();
                System.out.print("Enter organization: ");
                venueInformation.put("organization", scanner.nextLine());
                System.out.print("Enter area of research: ");
                venueInformation.put("area_of_research", scanner.nextLine());
                System.out.print("Enter editor's name: ");
                venueInformation.put("editor", scanner.nextLine());
                System.out.print("Enter editor's contact: ");
                venueInformation.put("editor_contact", scanner.nextLine());
                System.out.print("Enter venue's location: ");
                venueInformation.put("location", scanner.nextLine());
                System.out.print("Enter conference year: ");
                int conferenceYear = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character
                System.out.print("Enter publisher ID: ");
                String publisherId = scanner.nextLine();
                Set<String> researchAreas = new HashSet<>();
                while (true) {
                    System.out.print("Enter research area (or leave blank to finish): ");
                    String researchArea = scanner.nextLine();
                    if (researchArea.isBlank()) {
                        break;
                    }
                    researchAreas.add(researchArea);
                }
                library.addVenue(venueName, venueInformation, researchAreas);
                System.out.println("Venue added successfully.");
                
            } else if (choice == 4) {
            	System.out.print("Enter publication's identifier: ");
                String identifier = scanner.nextLine();
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
                publicationInformation.put("venueId", scanner.nextLine());                 
                
                library.addPublisher(identifier, publicationInformation );
                System.out.println("Publication added successfully.");
            } else if (choice == 5) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
