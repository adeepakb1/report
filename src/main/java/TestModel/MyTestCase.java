package TestModel;

public class MyTestCase {

    private String testName;
    private String  status;
    private String duration;


    public MyTestCase(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override public String toString() {
        return "MyTestCase{" + "testName='" + testName + '\'' + ", status='" + status + '\''
            + ", duration='" + duration + '\'' + '}';
    }
}
