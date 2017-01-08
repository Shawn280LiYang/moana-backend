package lab.io.rush.dao.datanucleus;

import java.util.List;

/**
 * Created by liyang on 17/1/2.
 */
public interface DataNucleusDao {
    <T> T find(Class<T> entityClass, Object primaryKey);

    void remove(Object entity);

    void persist(Object entity);

    <T> T merge(T entity);

    <T> List<T> querySelect(String query);

}
