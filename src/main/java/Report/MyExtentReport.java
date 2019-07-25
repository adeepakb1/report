package Report;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;


public class MyExtentReport {

    public static void main(String [] args){
        ExtentReports extent = new ExtentReports();
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter("/Users/deepakkumarsharma/Downloads/report/src/main/resources/GeneratedReport/report1.html");
        extent.attachReporter(extentHtmlReporter);
        File configFile = new File("/Users/deepakkumarsharma/Downloads/report/src/main/resources/GeneratedReport/extent-config.xml");



        //extentHtmlReporter.config().setReportName("automation remort");

        ExtentTest extentTest = extent.createTest("My first Test");
        extentTest.pass("is pass");
        ExtentTest node = extentTest.createNode("My new Node");
        ExtentTest test = extent.createTest("My Second Test");
        ExtentTest node2 = test.createNode("Second Name");
        node2.fail("is fail");
        node2.assignAuthor("Deepak");
        node.pass("is pass");
        extent.flush();


    }
}
