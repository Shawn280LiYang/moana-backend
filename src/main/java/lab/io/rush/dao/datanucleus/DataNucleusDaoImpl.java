package lab.io.rush.dao.datanucleus;

import org.datanucleus.util.NucleusLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyang on 17/1/2.
 */
@Repository
public class DataNucleusDaoImpl implements DataNucleusDao {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;
    private EntityTransaction tx;

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {

        em = emf.createEntityManager();
        tx = em.getTransaction();

        T result = null;
        try
        {
            tx.begin();

            System.out.println("Executing find() on " + entityClass);

            result =  em.find(entityClass,primaryKey);

            System.out.println("Retrieved result is " + result);

            tx.commit();
        }
        catch (Exception e)
        {
            NucleusLogger.GENERAL.error(">> Exception performing find() on data", e);
            System.err.println("Error performing find() on data : " + e.getMessage());
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            em.close(); // This will detach all current managed objects
        }
        return result;
    }

    @Override
    public void persist(Object entity) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        try
        {
            tx.begin();

            em.persist(entity);

            tx.commit();
            System.out.println(entity + " have been persisted");
        }
        catch (Exception e)
        {
            NucleusLogger.GENERAL.error(">> Exception persisting data", e);
            System.err.println("Error persisting data : " + e.getMessage());
            return;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            em.close();
        }
        // 配合关闭dataNucleus 二级缓存
        // emf.getCache().evictAll();
    }

    @Override
    public <T> T merge(T entity) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        T result = null;
        try
        {
            tx.begin();

            result = em.merge(entity);

            tx.commit();
            System.out.println(entity + " have been merged");
            System.out.println("\n\nentity after merge: "+ result+"\n");
        }
        catch (Exception e)
        {
            NucleusLogger.GENERAL.error(">> Exception merging data", e);
            System.err.println("Error merging data : " + e.getMessage());
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            em.close();
        }
        // 配合关闭dataNucleus 二级缓存
        // emf.getCache().evictAll();
        return result;
    }

    @Override
    public <T> List<T> querySelect(String query) {
        em = emf.createEntityManager();
        tx = em.getTransaction();

        List<T> result = null;
        try
        {
            tx.begin();
            System.out.println("Executing Query: " + query);
            Query q = em.createQuery(query);
            result = q.getResultList();
            tx.commit();
        }
        catch (Exception e)
        {
            NucleusLogger.GENERAL.error(">> Exception querying data", e);
            System.err.println("Error querying data : " + e.getMessage());
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            em.close();
        }
        return result;
    }

    @Override
    public void remove(Object entity) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        try
        {
            tx.begin();

            System.out.println("Deleting an entity from persistence: " + entity);

            em.remove(entity);

            tx.commit();
        }
        catch (Exception e)
        {
            NucleusLogger.GENERAL.error(">> Exception in bulk delete of data", e);
            System.err.println("Error in bulk delete of data : " + e.getMessage());
            return;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            em.close();
        }
        emf.close();
    }
}