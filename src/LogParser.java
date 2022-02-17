import java.util.ArrayList;
import java.util.HashMap;

public class LogParser {
    private String[] tablesName = {"CMDM_TD.APP ", "CMDM_TD.APP_SERVICE ", "CMDM_TD.GR_CLIENT_DAILY "};
    private HashMap<String, HashMap<String, Integer>> resultData = new HashMap<>();

    public String[] getTablesName() {
        return tablesName;
    }

    public HashMap<String, HashMap<String, Integer>> parseFileLines(ArrayList<String> processedFileLines) {
        for (String table : getTablesName()) {
            resultData.put(table, parseFileLinesByTable(table, processedFileLines));
        }
        return resultData;
    }

    HashMap<String, Integer> parseFileLinesByTable(String table, ArrayList<String> processedFileLines) {
        HashMap<String, Integer> resultDataByTable = new HashMap<>();

        for (String processedFileLine : processedFileLines) {
            int indexStart = 0;
            int indexEnd = 0;
            ArrayList<String> lineColumns = new ArrayList<>();
            int tableNameLength = table.length();

            if (processedFileLine.indexOf(table,indexStart) > -1) {
                String tableAlias = processedFileLine.substring(processedFileLine.indexOf(table,0) + tableNameLength,
                        processedFileLine.indexOf(table,0) + tableNameLength + "table0".length());

                indexStart = processedFileLine.indexOf(tableAlias + ".", 0) + (tableAlias + ".").length();

                if (processedFileLine.indexOf(" ", indexStart) > processedFileLine.indexOf(")", indexStart)) {
                    if  (processedFileLine.indexOf(")", indexStart) > 0) {
                        indexEnd = processedFileLine.indexOf(")", indexStart);
                    } else {
                        indexEnd = processedFileLine.indexOf(" ", indexStart);
                    }
                } else {
                    indexEnd = processedFileLine.indexOf(" ", indexStart);
                }

                String columnName = "";

                while (indexStart > -1 && indexStart < processedFileLine.length()) {
                    columnName = processedFileLine.substring(indexStart, indexEnd);

                    if (!lineColumns.contains(columnName)) {
                        lineColumns.add(columnName);
                    }

                    if (processedFileLine.indexOf(tableAlias + ".", indexEnd) > -1) {
                        indexStart = processedFileLine.indexOf(tableAlias + ".", indexEnd) + (tableAlias + ".").length();
                    } else {
                        break;
                    }

                    if (processedFileLine.indexOf(" ", indexStart) > processedFileLine.indexOf(")", indexStart)) {
                        if (processedFileLine.indexOf(")", indexStart) > 0) {
                            indexEnd = processedFileLine.indexOf(")", indexStart);
                        } else {
                            indexEnd = processedFileLine.indexOf(" ", indexStart);
                        }
                    } else {
                        indexEnd = processedFileLine.indexOf(" ", indexStart);
                    }

                }

            }

            if (lineColumns.size() > 0) {
                for (String lineColumn : lineColumns) {
                    if (lineColumn.contains("=")) {
                        lineColumn = lineColumn.substring(0, lineColumn.indexOf("="));
                    }

                    if (resultDataByTable.containsKey(lineColumn)) {
                        resultDataByTable.replace(lineColumn, resultDataByTable.get(lineColumn) + 1);
                    } else {
                        resultDataByTable.put(lineColumn, 1);
                    }
                }

            }
        }

        return resultDataByTable;
    }

}
