package taxiapp.service;

import java.util.List;

public interface GenericService<T, I> {
    T create(T car);

    T get(I id);

    List<T> getAll();

    T update(T car);

    boolean delete(I id);
}
