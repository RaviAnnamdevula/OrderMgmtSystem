package com.jocata.oms.data.um.dao.dapImpl;

import com.jocata.oms.data.um.config.HibernateUtil;
import com.jocata.oms.data.um.dao.RefreshTokenDao;
import com.jocata.oms.datamodel.um.entity.RefreshTokenEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenDaoImpl implements RefreshTokenDao {

    @Override
    public void saveRefreshToken(RefreshTokenEntity refreshToken) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(refreshToken);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
