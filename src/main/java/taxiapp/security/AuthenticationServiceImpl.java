package taxiapp.security;

import taxiapp.exception.AuthenticationException;
import taxiapp.lib.Inject;
import taxiapp.lib.Service;
import taxiapp.model.Driver;
import taxiapp.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Driver driver = driverService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect username or password"));
        if (driver.getPassword().equals(password)) {
            return driver;
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
