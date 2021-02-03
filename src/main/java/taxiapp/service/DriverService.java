package taxiapp.service;

import java.util.Optional;
import taxiapp.model.Driver;

public interface DriverService extends GenericService<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
