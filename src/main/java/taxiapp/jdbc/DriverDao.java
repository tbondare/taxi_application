package taxiapp.jdbc;

import java.util.Optional;
import taxiapp.model.Driver;

public interface DriverDao extends GenericDao<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
