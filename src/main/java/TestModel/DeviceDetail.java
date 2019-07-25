package TestModel;

public class DeviceDetail {
    private String model;
    private String manufacturer;
    private String version;
    private String apiLevel;
    private String deviceId;
    private String isEmulator;

    private MyFeature feature;

    public DeviceDetail() {
    }

    public DeviceDetail(MyFeature feature) {
        this.feature = feature;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(String apiLevel) {
        this.apiLevel = apiLevel;
    }

    public String getEmulator() {
        return isEmulator;
    }

    public void setEmulator(String emulator) {
        isEmulator = emulator;
    }

    public MyFeature getScenario() {
        return feature;
    }

    public void setScenario(MyFeature scenario) {
        this.feature = scenario;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIsEmulator() {
        return isEmulator;
    }

    public void setIsEmulator(String isEmulator) {
        this.isEmulator = isEmulator;
    }

    public MyFeature getFeature() {
        return feature;
    }

    public void setFeature(MyFeature feature) {
        this.feature = feature;
    }
}
