package taxiapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import taxiapp.exception.DataProcessingException;
import taxiapp.lib.Injector;
import taxiapp.model.Car;
import taxiapp.model.Driver;
import taxiapp.model.Manufacturer;
import taxiapp.service.CarService;
import taxiapp.service.DriverService;
import taxiapp.service.ManufacturerService;
import taxiapp.util.ConnectionUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("taxiapp");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        String queryDeleteManufacturers = "DELETE FROM manufacturers;";
        String queryDeleteCars = "DELETE FROM cars;";
        String queryDeleteDrivers = "DELETE FROM drivers;";
        String queryDeleteRelations = "DELETE FROM relations;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statementDelete = connection.prepareStatement(queryDeleteRelations);
            statementDelete.executeUpdate();
            statementDelete = connection.prepareStatement(queryDeleteCars);
            statementDelete.executeUpdate();
            statementDelete = connection.prepareStatement(queryDeleteManufacturers);
            statementDelete.executeUpdate();
            statementDelete = connection.prepareStatement(queryDeleteDrivers);
            statementDelete.executeUpdate();

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete", e);
        }

        Manufacturer manufacturerReno = new Manufacturer("Renault", "France");
        Manufacturer manufacturerBmw = new Manufacturer("BMW", "Germany");
        manufacturerService.create(manufacturerReno);
        manufacturerService.create(manufacturerBmw);
        System.out.println(manufacturerService.get(1L));
        System.out.println(manufacturerService.getAll().toString());
        Manufacturer manufacturerInfinity = new Manufacturer("Infinity", "USA");
        manufacturerInfinity.setId(2L);
        System.out.println(manufacturerService.update(manufacturerInfinity));
        System.out.println(manufacturerService.get(2L));
        System.out.println(manufacturerService.delete(1L));
        System.out.println(manufacturerService.getAll().toString());

        DriverService driverService = (DriverService)
                injector.getInstance(DriverService.class);
        Driver driver1 = new Driver("Alex", "licenseNumber-27");
        Driver driver2 = new Driver("Antonio", "licenseNumber-35");
        driverService.create(driver1);
        driverService.create(driver2);
        System.out.println(driverService.get(1L));
        System.out.println(driverService.getAll().toString());
        Driver driver3 = new Driver("Sancho", "licenseNumber-389");
        driver3.setId(2L);
        System.out.println(driverService.update(driver3));
        System.out.println(driverService.get(2L));
        System.out.println(driverService.delete(1L));
        System.out.println(driverService.getAll().toString());

        CarService carService = (CarService)
                injector.getInstance(CarService.class);
        Car reno = new Car("5a", manufacturerReno);
        Car bmw = new Car("x6", manufacturerBmw);
        carService.create(reno);
        carService.create(bmw);
        System.out.println(carService.get(1L));
        System.out.println(carService.getAll().toString());
        Car infinity = new Car("extra", manufacturerInfinity);
        infinity.setId(2L);
        System.out.println(carService.update(infinity));
        System.out.println(carService.get(2L));
        System.out.println(carService.delete(1L));
        System.out.println(carService.getAll().toString());
        System.out.println(carService.getAllByDriver(driver3.getId()));
    }
}
