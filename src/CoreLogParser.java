import java.io.File;
import java.util.ArrayList;

public class CoreLogParser {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int totalLines = 0;

        //список файлов для обработки
        ArrayList<LogFile> logFiles = new ArrayList<>();
        String filePath = args[0];
        //String filePath = "E:\\Dimets\\Java\\trash\\Sas_log_parser\\resources/"; //путь к файлу при запуске указывается в командной строке
        File folder = new File(filePath);
        for (File file : folder.listFiles()) {
            System.out.println(file.getName());
            logFiles.add(new LogFile(filePath, file.getName()));
        }


        for (LogFile logFile : logFiles) {
            //Читатель файлов
            LogReader logReader = new LogReader();
            logFile.readFile(logReader);
            totalLines += logFile.getLinesCount();

            //Парсер файлов
            LogParser logParser = new LogParser();
            logFile.parseFile(logParser);
        }

        //Выгрузка результатов
        ReportBuilder reportBuilder = new ReportBuilder();
        reportBuilder.reportByTables(logFiles);
        reportBuilder.reportAll(logFiles);


        long finish = System.currentTimeMillis();
        long elapsed = finish - start;

        System.out.println("Elapsed time, ms: " + elapsed);
        System.out.println("Files processed: " + logFiles.size());
        System.out.println("Lines processed: " + totalLines);

    }


}
