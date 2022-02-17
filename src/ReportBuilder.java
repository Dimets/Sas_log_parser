import java.util.ArrayList;
import java.util.HashMap;

public class ReportBuilder {
    private HashMap<String, HashMap<String, Integer>>  reportData = new HashMap<>(); // результат для всех файлов таблица и сумма по колонкам
    private HashMap<String, Integer> aggReportData = new HashMap<>(); // результат для всех файлов сумма по колонкам

    public void reportByTables(ArrayList<LogFile> logFiles) {
        String reportFileName = "report_by_tables.csv";
        String reportHeader = "table; column_name; count";
        calculateAllFiles(logFiles);
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeReportByTables(reportFileName, reportHeader, reportData);
    }

    public void reportAll(ArrayList<LogFile> logFiles) {
        String reportFileName = "report_all.csv";
        String reportFileColumns = "column_name; count";
        calculateAllFiles(logFiles);
        aggregateReportData(reportData);
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeReportAll(reportFileName, reportFileColumns, aggReportData);
    }

    private void calculateAllFiles(ArrayList<LogFile> logFiles) {

        for (LogFile logFile : logFiles) { // цикл по файлам
            HashMap<String, HashMap<String, Integer>> parsedData = new HashMap<>(logFile.getParsedData()); // данные из файла по количеству столбцов в разрезе таблиц

            for (String table : parsedData.keySet()) { // цикл по табличкам
                HashMap<String, Integer> columnsParsedData = new HashMap<>(parsedData.get(table));

                if (reportData.containsKey(table)) { // есть ли данные по такой таблице
                    HashMap<String, Integer> columnsReportData = new HashMap<>(reportData.get(table)); //получаем данные из результирующей таблицы
                    for(String column : columnsParsedData.keySet()) { //цикл по столбцам из новых данных
                        if (columnsReportData.containsKey(column)) { //проверяем в результирующей таблице данных по такому столбцу
                            columnsReportData.replace(column, columnsReportData.get(column) + columnsParsedData.get(column)); //обновляем данные по столбцу
                        }  else { //добавляем данные по столбцу
                            columnsReportData.put(column, columnsParsedData.get(column));
                        }
                    }
                    reportData.replace(table, columnsReportData);  //обновляем результирующую таблицу

                } else { //создаем новую запись для таблицы
                    reportData.put(table, parsedData.get(table));
                }
            }

        }
    }

    private void aggregateReportData(HashMap<String, HashMap<String, Integer>>  reportData) {

        for (String table : reportData.keySet() ) {
            for (String column : reportData.get(table).keySet()) {
                if (aggReportData.containsKey(column)) {
                    aggReportData.replace(column, aggReportData.get(column) + reportData.get(table).get(column));
                } else {
                    aggReportData.put(column, reportData.get(table).get(column));
                }
            }
        }
    }

}
