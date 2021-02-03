package taxiapp.model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private Long id;
    private String model;
    private Manufacturer manufacturer;
    private List<Driver> driverList;

    public Car(String madel, Manufacturer manufacturer) {
        this.model = madel;
        this.manufacturer = manufacturer;
        this.driverList = new ArrayList<>();
    }

    public Car(Long id, String madel, Manufacturer manufacturer) {
        this.id = id;
        this.model = madel;
        this.manufacturer = manufacturer;
        this.driverList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    @Override
    public String toString() {
        return "id = " + id
                + ", model: " + model
                + ", manufacturer - " + manufacturer
                + ", driveList: " + driverList.toString();
    }
}
