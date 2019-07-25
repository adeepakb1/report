package TestModel;

import java.util.ArrayList;
import java.util.Objects;



public class MyFeature {

    private String featureName;
    private ArrayList<MyTestCase> testCases;
    private DeviceDetail deviceDetail;



    public MyFeature(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public ArrayList<MyTestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(ArrayList<MyTestCase> testCases) {
        this.testCases = testCases;
    }

    public DeviceDetail getDeviceDetail() {
        return deviceDetail;
    }

    public void setDeviceDetail(DeviceDetail deviceDetail) {
        this.deviceDetail = deviceDetail;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MyFeature))
            return false;
        MyFeature myFeature = (MyFeature) o;
        return Objects.equals(getFeatureName(), myFeature.getFeatureName());
    }

    @Override public int hashCode() {
        return Objects.hash(getFeatureName());
    }
}
