import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogReader {
    public String readFileContentsOrNull(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Unable to read file");
            return null;
        }
    }
}
