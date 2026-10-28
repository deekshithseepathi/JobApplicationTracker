
import java.time.LocalDate;

public class JobApplication {

    private int id;
    private String companyName;
    private String jobRole;
    private String location;
    private String status;
    private LocalDate applicationDate;

    public JobApplication(String companyName, String jobRole,
                          String location, String status,
                          LocalDate applicationDate) {

        this.companyName = companyName;
        this.jobRole = jobRole;
        this.location = location;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void displayDetails() {
        System.out.println("ID: " + id);
        System.out.println("Company: " + companyName);
        System.out.println("Job Role: " + jobRole);
        System.out.println("Location: " + location);
        System.out.println("Status: " + status);
        System.out.println("Application Date: " + applicationDate);
    }
}
