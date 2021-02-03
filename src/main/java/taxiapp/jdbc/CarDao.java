package taxiapp.jdbc;

import java.util.List;
import taxiapp.model.Car;

public interface CarDao extends GenericDao<Car, Long> {
    List<Car> getAllByDriver(Long driverId);
}
