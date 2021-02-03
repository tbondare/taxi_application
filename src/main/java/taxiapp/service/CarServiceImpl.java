package taxiapp.service;

import java.util.List;
import taxiapp.jdbc.CarDao;
import taxiapp.jdbc.DriverDao;
import taxiapp.lib.Inject;
import taxiapp.lib.Service;
import taxiapp.model.Car;
import taxiapp.model.Driver;

@Service
public class CarServiceImpl implements CarService {

    @Inject
    DriverDao driverDao;

    @Inject
    CarDao carDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.get(id).get();
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        car.getDriverList().add(driver);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        car.getDriverList().remove(driver);
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return carDao.getAllByDriver(driverId);
    }
}
