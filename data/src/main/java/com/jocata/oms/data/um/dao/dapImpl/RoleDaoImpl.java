package com.jocata.oms.data.um.dao.dapImpl;

import com.jocata.oms.data.um.config.HibernateUtil;
import com.jocata.oms.data.um.dao.RoleDao;
import com.jocata.oms.datamodel.um.entity.RoleEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Override
    public void saveRole(RoleEntity role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
