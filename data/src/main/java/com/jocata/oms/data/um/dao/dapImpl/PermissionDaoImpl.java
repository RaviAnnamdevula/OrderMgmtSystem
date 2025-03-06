package com.jocata.oms.data.um.dao.dapImpl;

import com.jocata.oms.data.um.config.HibernateUtil;
import com.jocata.oms.data.um.dao.PermissionDao;
import com.jocata.oms.datamodel.um.entity.PermissionEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionDaoImpl implements PermissionDao {

    @Override
    public void savePermission(PermissionEntity permission) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(permission);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
