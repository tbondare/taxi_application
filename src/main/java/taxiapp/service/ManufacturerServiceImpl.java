package taxiapp.service;

import java.util.List;
import taxiapp.jdbc.ManufacturerDao;
import taxiapp.lib.Inject;
import taxiapp.lib.Service;
import taxiapp.model.Manufacturer;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Inject
    private ManufacturerDao manufacturerDaoJdbc;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return manufacturerDaoJdbc.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long manufacturerId) {
        return manufacturerDaoJdbc.get(manufacturerId).get();
    }

    @Override
    public List<Manufacturer> getAll() {
        return manufacturerDaoJdbc.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return manufacturerDaoJdbc.update(manufacturer);
    }

    @Override
    public boolean delete(Long manufacturerId) {
        return manufacturerDaoJdbc.delete(manufacturerId);
    }
}
