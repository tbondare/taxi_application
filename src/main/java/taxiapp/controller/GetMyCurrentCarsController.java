package taxiapp.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxiapp.lib.Injector;
import taxiapp.model.Car;
import taxiapp.service.CarService;

public class GetMyCurrentCarsController extends HttpServlet {
    private static final String DRIVER_ID = "driverId";
    private static final Injector injector = Injector.getInstance("taxiapp");
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long log = (Long) req.getSession().getAttribute(DRIVER_ID);
        List<Car> carList = carService.getAllByDriver(log);
        req.setAttribute("cars", carList);
        req.getRequestDispatcher("/WEB-INF/views/cars/display.jsp").forward(req, resp);
    }
}
