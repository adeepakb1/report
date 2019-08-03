import TestModel.DeviceDetail;
import TestModel.MyFeature;
import TestModel.MyTestCase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
/*import com.aventstack.extentreports.configuration.Config;
import com.aventstack.extentreports.configuration.ConfigLoader;
import com.aventstack.extentreports.configuration.ConfigMap;*/
//import com.aventstack.extentreports.externalconfig.ConfigLoader;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.oracle.javafx.jmx.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;


public class MainReport {


    static ArrayList<MyFeature> myFeatures = new ArrayList<MyFeature>();
    static String featureName;
    static MyFeature myFeature ;
    static ExtentHtmlReporter extentHtmlReporter;
    static Long timeStart;
    static MyFeature myFeatureSame;


    public static void main(String[] args) throws IOException {


        BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream("src/main/resources/index.json")));

        String s = "", json = "";

        while ((s = reader.readLine()) != null) {
            json += s;
        }
        JSONObject mainJson = new JSONObject(json).getJSONObject("results");
        timeStart = (Long) new JSONObject(json).get("started");
        Map<String, Object> results = jsonToMap(mainJson);
        Set<String> keys = results.keySet();

        for (String key : keys) {
            HashMap<String, Object> jsonObject = (HashMap<String, Object>) results.get(key);
            ArrayList<Object> testResults = (ArrayList<Object>) jsonObject.get("testResults");
            DeviceDetail deviceDetails =
                giveDeviceDetails((HashMap<String, String>) jsonObject.get("deviceDetails"));
            deviceDetails.setDeviceId(key);


            for (Object testResult : testResults) {
                ArrayList<Object> result = (ArrayList<Object>) testResult;

                    featureName = ((HashMap<String, String>) result.get(0)).get("className");
                    myFeature= new MyFeature(featureName);
                    String methodName = ((HashMap<String, String>) result.get(0)).get("methodName");
                    MyTestCase myTestCase = new MyTestCase(methodName);

                    myTestCase.setStatus(((HashMap<String, String>) result.get(1)).get("status"));
                    myTestCase.setDuration(
                        String.valueOf(((HashMap<String, String>) result.get(1)).get("duration")));
                    if (myFeatures.contains(myFeature)) {
                        sameFeature(myFeature);
                        ArrayList<MyTestCase> myTestCases = myFeatureSame.getTestCases();
                        myTestCases.add(myTestCase);
                        myFeatureSame.setTestCases(myTestCases);
                        myFeatureSame.setDeviceDetail(deviceDetails);
                    } else {
                        myFeature.setFeatureName(featureName);
                        ArrayList<MyTestCase> myTestCases = new ArrayList<MyTestCase>();
                        myTestCases.add(myTestCase);
                        myFeature.setTestCases(myTestCases);
                        myFeature.setDeviceDetail(deviceDetails);
                        myFeatures.add(myFeature);

                }
            }
        }

        ExtentReports extent = createReport();

        for (MyFeature myFeature : myFeatures) {
            ExtentTest newTest = extent.createTest(myFeature.getFeatureName());
           newTest.getModel().setStartTime(getTime());
           newTest.pass("sdsd");
            DeviceDetail deviceDetail = myFeature.getDeviceDetail();
            extent.setSystemInfo("Device: " + deviceDetail.getManufacturer()+" "+ deviceDetail.getModel(),"Version: " + deviceDetail.getVersion() + "Api Level: " + deviceDetail
                .getApiLevel());

            newTest.assignCategory(myFeature.getDeviceDetail().getDeviceId());

            newTest.info("Device: " + deviceDetail.getManufacturer() + " " + deviceDetail.getModel()
                + "\n Version: " + deviceDetail.getVersion() + "\n Api Level: " + deviceDetail
                .getApiLevel());
            for (MyTestCase myTestCase : myFeature.getTestCases()) {
                ExtentTest newTestNode = newTest.createNode(myTestCase.getTestName());

                if (myTestCase.getStatus().equalsIgnoreCase("PASS")) {
                    newTestNode.pass("Duration: " + myTestCase.getDuration());
                } else {
                    newTestNode.fail("Duration: " + myTestCase.getDuration());
                }
            }
        }

        extent.flush();
    }

    private static MyFeature sameFeature(MyFeature o) {
        for(MyFeature my :myFeatures){
            if(my.getFeatureName().equalsIgnoreCase(o.getFeatureName())){
                myFeatureSame = my;
            }
        }
        return myFeatureSame;
    }

    private static Date getTime() {
        return new Date(timeStart);

    }

    private static ExtentReports createReport() {

        ExtentReports extent = new ExtentReports();

        extentHtmlReporter = new ExtentHtmlReporter(
            "/Users/deepakkumarsharma/Downloads/report/src/main/resources/GeneratedReport/report1.html");
        extent.attachReporter(extentHtmlReporter);
        File configFile = new File(
            "/Users/deepakkumarsharma/Downloads/report/src/main/resources/GeneratedReport/extent-config.xml");
        //ConfigLoader configLoader = new ConfigLoader(configFile);
        /*ConfigMap configMap = configLoader.getConfigurationHash();
        List<Config> list = configMap.getConfigList();
        extentHtmlReporter.loadConfig(
            "/Users/deepakkumarsharma/Downloads/report/src/main/resources/GeneratedReport/extent-config.xml");
        List<Config> list1 = extentHtmlReporter.getConfigContext().getConfigList();*/

        //extentHtmlReporter.config().setReportName("automation remort");
        extentHtmlReporter.config().setReportName("Android Automation Report");

        return extent;
    }

    private static DeviceDetail giveDeviceDetails(HashMap<String, String> deviceDetails) {

        DeviceDetail deviceDetail = new DeviceDetail();
        deviceDetail.setApiLevel(String.valueOf(deviceDetails.get("apiLevel")));
        deviceDetail.setEmulator(String.valueOf(deviceDetails.get("isEmulator")));
        deviceDetail.setModel(deviceDetails.get("model"));
        deviceDetail.setManufacturer(deviceDetails.get("manufacturer"));
        deviceDetail.setVersion(deviceDetails.get("version"));

        return deviceDetail;
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
