
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class JobApplicationDAO {

    // 1. Add an application to MySQL
    public boolean addApplication(JobApplication job) {

        String sql = "INSERT INTO job_applications " +
                     "(company_name, job_role, location, status, application_date) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection =
                    DatabaseConnection.getConnection();

             PreparedStatement statement =
                    connection.prepareStatement(sql)) {

            statement.setString(1, job.getCompanyName());
            statement.setString(2, job.getJobRole());
            statement.setString(3, job.getLocation());
            statement.setString(4, job.getStatus());
            statement.setDate(
                5, Date.valueOf(job.getApplicationDate())
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            // MySQL error code 1062 means duplicate entry
            if (e.getErrorCode() == 1062) {
                System.out.println(
                    "Duplicate application! This company and job role already exist."
                );
                return false;
            }

            throw new RuntimeException(
                "Unable to save application: " + e.getMessage(), e
            );
        }
    }

    // 2. Retrieve all applications
    public ArrayList<JobApplication> getAllApplications() {

        ArrayList<JobApplication> applications =
                new ArrayList<>();

        String sql =
                "SELECT * FROM job_applications ORDER BY id ASC";

        try (Connection connection =
                    DatabaseConnection.getConnection();

             PreparedStatement statement =
                    connection.prepareStatement(sql);

             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                int id = result.getInt("id");

                String companyName =
                        result.getString("company_name");

                String jobRole =
                        result.getString("job_role");

                String location =
                        result.getString("location");

                String status =
                        result.getString("status");

                LocalDate applicationDate =
                        result.getDate("application_date").toLocalDate();

                JobApplication job = new JobApplication(
                    companyName,
                    jobRole,
                    location,
                    status,
                    applicationDate
                );

                job.setId(id);
                applications.add(job);
            }

            return applications;

        } catch (SQLException e) {

            throw new RuntimeException(
                "Unable to retrieve applications: " + e.getMessage(), e
            );
        }
    }

    // 3. Update status using application ID
    public boolean updateStatus(int id, String newStatus) {

        String sql =
                "UPDATE job_applications SET status = ? WHERE id = ?";

        try (Connection connection =
                    DatabaseConnection.getConnection();

             PreparedStatement statement =
                    connection.prepareStatement(sql)) {

            statement.setString(1, newStatus);
            statement.setInt(2, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                "Unable to update application: " + e.getMessage(), e
            );
        }
    }

    // 4. Delete using application ID
    public boolean deleteApplication(int id) {

        String sql =
                "DELETE FROM job_applications WHERE id = ?";

        try (Connection connection =
                    DatabaseConnection.getConnection();

             PreparedStatement statement =
                    connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                "Unable to delete application: " + e.getMessage(), e
            );
        }
    }
}
