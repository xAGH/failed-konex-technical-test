package konex.innovation.medicine_administration.services;

import java.util.List;

public interface CrudService<T> {

    public List<T> list();

    public T findById(Long id);

    public T save(T entity);

    public T delete(T entity);

}
