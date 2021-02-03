package taxiapp.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxiapp.exception.DataProcessingException;
import taxiapp.lib.Dao;
import taxiapp.model.Car;
import taxiapp.model.Driver;
import taxiapp.model.Manufacturer;
import taxiapp.util.ConnectionUtil;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO cars (model, manufacturer_id)"
                + "VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert car " + car, e);
        }
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        Car car = null;
        String query = "SELECT c.car_id, c.model, m.id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.car_id = ? "
                + "AND m.deleted = false AND c.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = parseCarResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id " + id, e);
        }
        if (car != null) {
            car.setDriverList(getDriversByCarId(id));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT c.car_id, c.model, m.id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE m.deleted = false AND c.deleted = false;";
        List<Car> carList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carList.add(parseCarResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
        for (Car car : carList) {
            car.setDriverList(getDriversByCarId(car.getId()));
        }
        return carList;
    }

    @Override
    public Car update(Car car) {
        String query = "UPDATE cars SET model = ?,"
                + "manufacturer_id = ? WHERE car_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.setLong(3, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + car, e);
        }
        deleteRelations(car.getId());
        createRelations(car.getDriverList(), car.getId());
        return car;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE cars SET deleted = true WHERE car_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = "SELECT DISTINCT c.car_id, c.model, m.id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "INNER JOIN relations r ON c.car_id = r.car_id "
                + "INNER JOIN drivers d ON r.driver_id = d.id "
                + "WHERE r.driver_id = ? AND m.deleted = false "
                + "AND c.deleted = false AND d.deleted = false;";
        List<Car> carList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carList.add(parseCarResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars by driver id " + driverId, e);
        }
        for (Car car : carList) {
            car.setDriverList(getDriversByCarId(car.getId()));
        }
        return carList;
    }

    private List<Driver> getDriversByCarId(Long carId) {
        String query = "SELECT d.id, d.name, d.license_number FROM drivers d "
                + "INNER JOIN relations r ON d.id = r.driver_id "
                + "WHERE r.car_id = ? "
                + "AND d.deleted = false;";
        List<Driver> driverList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, carId);
            ResultSet resultSetDrivers = statement.executeQuery();
            while (resultSetDrivers.next()) {
                Long driverId = resultSetDrivers.getObject("d.id", Long.class);
                String driverName = resultSetDrivers.getObject("d.name", String.class);
                String licenseNumber = resultSetDrivers.getObject("d.license_number", String.class);
                driverList.add(new Driver(driverId, driverName, licenseNumber));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't query drivers for car id " + carId, e);
        }
        return driverList;
    }

    private Car parseCarResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("car_id", Long.class);
        String model = resultSet.getObject("model", String.class);
        Long manufacturerId = resultSet.getObject("m.id", Long.class);
        String manufacturerName = resultSet.getObject("m.name", String.class);
        String manufacturerCountry = resultSet.getObject("m.country", String.class);
        return new Car(id, model, new Manufacturer(manufacturerId, manufacturerName,
                manufacturerCountry));
    }

    private boolean deleteRelations(Long carId) {
        String query = "DELETE FROM relations WHERE car_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, carId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete relations", e);
        }
    }

    private void createRelations(List<Driver> driverList, Long carId) {
        String queryRelation = "INSERT INTO relations (driver_id, car_id)"
                + "VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryRelation)) {
            for (Driver driver : driverList) {
                statement.setLong(1, driver.getId());
                statement.setLong(2, carId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create relations", e);
        }
    }
}
