import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CsvWriter {
    private static final String COMMA_DELIMITER = ";";
    private static final String LINE_SEPARATOR = "\n";


    //File header
    //private static final String HEADER = "columnName;count";

    public void writeReportByTables(String reportFileName, String reportHeader, HashMap<String, HashMap<String, Integer>>  reportData) {
        //Delimiters which has to be in the CSV file

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(reportFileName);

            //Adding the header
            fileWriter.append(reportHeader);
            //New Line after the header
            fileWriter.append(LINE_SEPARATOR);


            for (String table : reportData.keySet()) {
                for (String column : reportData.get(table).keySet()) {
                    fileWriter.append(table);
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(column);
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(reportData.get(table).get(column)));
                    fileWriter.append(LINE_SEPARATOR);
                }
            }

            System.out.println("Write to CSV file " + reportFileName + " Succeeded!!!");
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ie) {
                System.out.println("Error occurred while closing the fileWriter");
                ie.printStackTrace();
            }
        }
    }

    public void writeReportAll(String reportFileName, String reportHeader, HashMap<String, Integer>  aggReportData) {
        //Delimiters which has to be in the CSV file

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(reportFileName);

            //Adding the header
            fileWriter.append(reportHeader);
            //New Line after the header
            fileWriter.append(LINE_SEPARATOR);


            for (String column : aggReportData.keySet()) {
                    fileWriter.append(column);
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(aggReportData.get(column)));
                    fileWriter.append(LINE_SEPARATOR);
                }


            System.out.println("Write to CSV file " + reportFileName + " Succeeded!!!");
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ie) {
                System.out.println("Error occurred while closing the fileWriter");
                ie.printStackTrace();
            }
        }
    }



}