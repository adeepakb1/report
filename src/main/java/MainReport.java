import com.oracle.javafx.jmx.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;


public class MainReport {


    public static String htmlBody =
        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www"
            + ".w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n"
            + "<title>Deeplink Test Report</title>\n" + "<style type=\"text/css\">\n"
            + "table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}\n"
            + "td,th {border:1px solid #009;padding:.25em .5em}\n"
            + ".result th {vertical-align:bottom}\n"
            + ".param th {padding-left:1em;padding-right:1em}\n"
            + ".param td {padding-left:.5em;padding-right:2em}\n"
            + ".stripe td,.stripe th {background-color: #E6EBF9}\n"
            + ".numi,.numi_attn {text-align:right}\n" + ".total td {font-weight:bold}\n"
            + ".passedodd td {background-color: #00e600}\n"
            + ".passedeven td {background-color: #80ff80}\n"
            + ".skippedodd td {background-color: #CCC}\n"
            + ".skippedodd td {background-color: #DDD}\n"
            + ".failedodd td,.numi_attn {background-color:  #ffb3b3}\n"
            + ".failedeven td,.stripe .numi_attn {background-color: #ff8080}\n"
            + ".stacktrace {white-space:pre;font-family:monospace}\n"
            + ".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}\n"
            + "body {background-color: #cccccc99;}\n" + "rect {fill-opacity: 0.0;}\n" + "</style>\n"
            + "</head>\n" + "<body>\n" + " <center><img src=\"https://vignette.wikia.nocookie"
            + ".net/logopedia/images/9/93/Blibli_2015.png/revision/latest?cb=20180808040105\" width=\"220\" "
            + "height=\"60\"></center>\n" + "<center>\n"
            + "<h1>Blibli Android Automation Report</h1>\n" + "</center>\n"
            + "<div id=\"piechart\" align=\"middle\" style=\"vertical-align: top;\"></div>"
            + "<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n"
            + "<script type=\"text/javascript\">\n"
            + "google.charts.load('current', {'packages':['corechart']});\n"
            + "google.charts.setOnLoadCallback(drawChart);\n" + "function drawChart() {\n"
            + "var data = google.visualization.arrayToDataTable([\n" + "['Result', 'Count'],\n"
            + "['Pass', Number({pass_count})],\n" + "['Fail', Number({fail_count})]\n" + "]);\n"
            + "var options = {'title':'Deeplink Result', 'width':550, 'height':400};"
            + "var chart = new google.visualization.PieChart(document.getElementById('piechart'));"
            + "chart.draw(data, options);" + "}" + "</script>"
            + "<center><table cellspacing=\"0\" cellpadding=\"0\" class=\"testOverview\" "
            + "style=\"margin-bottom: 10px;border-collapse: collapse;empty-cells: show;\">\n"
            + "<tr>\n"
            + "<th style=\"border: 1px solid #009;padding: .25em .5em;\">Test Name</th>\n"
            + "<th style=\"border: 1px solid #009;padding: .25em .5em;\">Status</th>\n" + "</tr>\n"
            + "{html_report}" + "</table>\n" + "</center>\n" + "</body>\n" + "</html>";


    public static String htmlPart = "<tr>\n"
        + "<td style=\"text-align: left;padding-right: 2em;border: 1px solid #009;padding: .25em "
        + ".5em;\"><b>{testName}</b></td>\n"
        + "<td class=\"numi\" style=\"border: 1px solid #009;padding: .25em .5em;text-align: right;"
        + "\">{testStatus}</td>\n" + "</tr>";

    static HashMap<String, String> finalResults = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {


        BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream("src/main/resources/index.json")));

        String s = "", json = "";

        while ((s = reader.readLine()) != null) {

            json += s;
        }
        JSONObject mainJson = new JSONObject(json).getJSONObject("results");
        Map<String, Object> results = jsonToMap(mainJson);
        Set<String> keys = results.keySet();

        for (String key : keys) {
            HashMap<String, Object> jsonObject = (HashMap<String, Object>) results.get(key);
            ArrayList<Object> testResults = (ArrayList<Object>) jsonObject.get("testResults");
            System.out.println("");

            for (Object testResult : testResults) {

                ArrayList<Object> result = (ArrayList<Object>) testResult;
                for (int i = 0; i < result.size(); i++) {

                    String methodName = ((HashMap<String, String>) result.get(0)).get("methodName");
                    String status = ((HashMap<String, String>) result.get(1)).get("status");

                    finalResults.put(methodName, status);

                }

            }
        }


        int pass = 0, fail = 0;
        String html = "";
        for (Map.Entry<String, String> entry : finalResults.entrySet()) {
            String test = htmlPart.replace("{testName}", entry.getKey());
            test = test.replace("{testStatus}", entry.getValue());
            if (entry.getValue().contains("PASS") == true) {
                pass++;
            } else {
                fail++;
            }
            html = html + test;
        }

        htmlBody = htmlBody.replace("{html_report}", html);
        htmlBody = htmlBody.replace("{pass_count}", String.valueOf(pass));
        htmlBody = htmlBody.replace("{fail_count}", String.valueOf(fail));

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(
                "/Users/deepakkumarsharma/Downloads/report/src/main/resources/Report.html")));

        bufferedWriter.write(htmlBody);
        bufferedWriter.flush();


    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
