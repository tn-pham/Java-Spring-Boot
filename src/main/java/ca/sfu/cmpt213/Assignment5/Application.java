package ca.sfu.cmpt213.Assignment5;

import ca.sfu.cmpt213.Assignment5.model.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Main application, Spring Boot server
 */

@SpringBootApplication
public class Application {

    public static final int ERROR_EXIT_CODE = -1;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        //Database database = Database.getDatabase();
        //writeToDumpFile(database);
    }

    //for part 1
    private static void writeToDumpFile(Database database) {
        File targetFile = new File("dump/output_dump.txt");

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(targetFile, false))){
            writer.println(database.toString());
            writer.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error. Cannot write to file");
            System.exit(ERROR_EXIT_CODE);
        }
    }
}
