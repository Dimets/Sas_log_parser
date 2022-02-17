import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LogFile {
    private String filePath;
    private String fileName;
    private int linesCount;
    private ArrayList<String> processedFileLines = new ArrayList<>();
    private HashMap<String, HashMap<String, Integer>> parsedData = new HashMap<>();


    public LogFile(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        linesCount = 0;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<String> getProcessedFileLines() {
        return processedFileLines;
    }

    public HashMap<String, HashMap<String, Integer>> getParsedData() {
        return parsedData;
    }

    public int getLinesCount() {
        return linesCount;
    }


    public void readFile(LogReader logReader) {
        String startPattern = "Create table MATables";
        String endPattern = "quit;";
        String selectLineData = "";

        ArrayList<String> sourceFileLines = new ArrayList<>(Arrays.asList(logReader.
                readFileContentsOrNull(this.getFilePath() + this.getFileName()).split("\\n")));

        linesCount = sourceFileLines.size();

        for (int i = 0; i < sourceFileLines.size(); i++) {
            if (sourceFileLines.get(i).contains(startPattern)) {
                while (!sourceFileLines.get(i).contains(endPattern)) {
                    selectLineData += sourceFileLines.get(i);
                    i++;
                }
                processedFileLines.add(selectLineData);
                selectLineData = "";
            }
        }
    }

    public void parseFile(LogParser logParser) {
        parsedData = logParser.parseFileLines(getProcessedFileLines());
    }



}
