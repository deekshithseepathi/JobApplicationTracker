
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class JobApplicationTracker {

    private final Scanner sc = new Scanner(System.in);
    private final JobApplicationDAO dao = new JobApplicationDAO();

    public void start() {

        boolean isRunning = true;

        while (isRunning) {

            System.out.println("\n========== JOB APPLICATION TRACKER ==========");
            System.out.println("1. Add Job Application");
            System.out.println("2. View All Applications");
            System.out.println("3. Search Applications");
            System.out.println("4. Update Application Status");
            System.out.println("5. Delete Job Application");
            System.out.println("6. View Dashboard");
            System.out.println("7. Exit");
            System.out.println("==============================================");

            int choice = readNumber("Enter your choice: ");

            switch (choice) {
                case 1:
                    addApplication();
                    break;
                case 2:
                    viewApplications();
                    break;
                case 3:
                    searchApplications();
                    break;
                case 4:
                    updateApplicationStatus();
                    break;
                case 5:
                    deleteApplication();
                    break;
                case 6:
                    displayDashboard();
                    break;
                case 7:
                    isRunning = false;
                    System.out.println("Exiting Job Application Tracker.");
                    break;
                default:
                    System.out.println("Invalid choice. Choose 1 to 7.");
            }
        }

        sc.close();
    }

    // Read a valid integer
    private int readNumber(String message) {

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Reject empty text
    private String readNonEmpty(String message) {

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    // Read a valid application status
    private String readStatus() {

        while (true) {

            System.out.println("\nChoose status:");
            System.out.println("1. Applied");
            System.out.println("2. Online Assessment");
            System.out.println("3. Interview");
            System.out.println("4. Selected");
            System.out.println("5. Rejected");

            int choice = readNumber("Enter status number: ");

            switch (choice) {
                case 1:
                    return "Applied";
                case 2:
                    return "Online Assessment";
                case 3:
                    return "Interview";
                case 4:
                    return "Selected";
                case 5:
                    return "Rejected";
                default:
                    System.out.println("Invalid status. Choose 1 to 5.");
            }
        }
    }

    // Display database errors without crashing the menu
    private void handleDatabaseError(RuntimeException e) {
        System.out.println("\nDatabase operation failed.");
        System.out.println(e.getMessage());
        System.out.println(
            "Check that MySQL is running and your connection settings are correct."
        );
    }

    // 1. Add application
    public void addApplication() {

        String companyName = readNonEmpty("Enter company name: ");
        String jobRole = readNonEmpty("Enter job role: ");
        String location = readNonEmpty("Enter location: ");
        String status = readStatus();

        JobApplication job = new JobApplication(
            companyName,
            jobRole,
            location,
            status,
            LocalDate.now()
        );

        try {
            boolean saved = dao.addApplication(job);

            if (saved) {
                System.out.println(
                    "Application saved successfully! Date: "
                    + job.getApplicationDate()
                );
            } else {
                System.out.println("Application was not saved.");
            }

        } catch (RuntimeException e) {
            handleDatabaseError(e);
        }
    }

    // 2. View all applications
    public void viewApplications() {

        try {
            ArrayList<JobApplication> applications =
                    dao.getAllApplications();

            if (applications.isEmpty()) {
                System.out.println("No job applications found.");
                return;
            }

            System.out.println("\n===== ALL JOB APPLICATIONS =====");

            for (JobApplication application : applications) {
                System.out.println();
                application.displayDetails();
                System.out.println("-------------------------");
            }

        } catch (RuntimeException e) {
            handleDatabaseError(e);
        }
    }

    // 3. Search by company name or job role
    public void searchApplications() {

        try {
            ArrayList<JobApplication> applications =
                    dao.getAllApplications();

            if (applications.isEmpty()) {
                System.out.println("No applications available to search.");
                return;
            }

            String searchText =
                    readNonEmpty("Enter company name or job role: ")
                    .toLowerCase();

            boolean found = false;

            for (JobApplication application : applications) {

                boolean matches =
                    application.getCompanyName()
                        .toLowerCase().contains(searchText)
                    || application.getJobRole()
                        .toLowerCase().contains(searchText);

                if (matches) {
                    System.out.println("\nMatch found:");
                    application.displayDetails();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matching applications found.");
            }

        } catch (RuntimeException e) {
            handleDatabaseError(e);
        }
    }

    // 4. Update status using ID
    public void updateApplicationStatus() {

        try {
            ArrayList<JobApplication> applications =
                    dao.getAllApplications();

            if (applications.isEmpty()) {
                System.out.println("No applications available to update.");
                return;
            }

            System.out.println("\n===== APPLICATIONS =====");

            for (JobApplication application : applications) {
                application.displayDetails();
                System.out.println("-------------------------");
            }

            int id = readNumber("Enter application ID to update: ");

            JobApplication jobToUpdate = null;

            for (JobApplication application : applications) {
                if (application.getId() == id) {
                    jobToUpdate = application;
                    break;
                }
            }

            if (jobToUpdate == null) {
                System.out.println("Application ID not found.");
                return;
            }

            System.out.println("\nCurrent application:");
            jobToUpdate.displayDetails();

            String newStatus = readStatus();

            boolean updated = dao.updateStatus(id, newStatus);

            if (updated) {
                jobToUpdate.setStatus(newStatus);
                System.out.println("Status updated successfully!");
                jobToUpdate.displayDetails();
            } else {
                System.out.println("No application was updated.");
            }

        } catch (RuntimeException e) {
            handleDatabaseError(e);
        }
    }

    // 5. Delete using ID
    public void deleteApplication() {

        try {
            ArrayList<JobApplication> applications =
                    dao.getAllApplications();

            if (applications.isEmpty()) {
                System.out.println("No applications available to delete.");
                return;
            }

            System.out.println("\n===== APPLICATIONS =====");

            for (JobApplication application : applications) {
                application.displayDetails();
                System.out.println("-------------------------");
            }

            int id = readNumber("Enter application ID to delete: ");

            JobApplication jobToDelete = null;

            for (JobApplication application : applications) {
                if (application.getId() == id) {
                    jobToDelete = application;
                    break;
                }
            }

            if (jobToDelete == null) {
                System.out.println("Application ID not found.");
                return;
            }

            System.out.println("\nApplication to delete:");
            jobToDelete.displayDetails();

            System.out.print("Confirm deletion? (yes/no): ");
            String confirmation = sc.nextLine().trim();

            if (confirmation.equalsIgnoreCase("yes")) {

                boolean deleted = dao.deleteApplication(id);

                if (deleted) {
                    System.out.println("Application deleted successfully.");
                } else {
                    System.out.println("No application was deleted.");
                }

            } else {
                System.out.println("Deletion cancelled.");
            }

        } catch (RuntimeException e) {
            handleDatabaseError(e);
        }
    }

    // 6. Dashboard
    public void displayDashboard() {

        try {
            ArrayList<JobApplication> applications =
                    dao.getAllApplications();

            int total = applications.size();
            int applied = 0;
            int assessment = 0;
            int interview = 0;
            int selected = 0;
            int rejected = 0;

            for (JobApplication application : applications) {

                switch (application.getStatus()) {
                    case "Applied":
                        applied++;
                        break;
                    case "Online Assessment":
                        assessment++;
                        break;
                    case "Interview":
                        interview++;
                        break;
                    case "Selected":
                        selected++;
                        break;
                    case "Rejected":
                        rejected++;
                        break;
                }
            }

            System.out.println("\n======= JOB SEARCH DASHBOARD =======");
            System.out.println("Total Applications: " + total);
            System.out.println("Applied: " + applied);
            System.out.println("Online Assessment: " + assessment);
            System.out.println("Interview: " + interview);
            System.out.println("Selected: " + selected);
            System.out.println("Rejected: " + rejected);
            System.out.println("====================================");

        } catch (RuntimeException e) {
            handleDatabaseError(e);
        }
    }
}
