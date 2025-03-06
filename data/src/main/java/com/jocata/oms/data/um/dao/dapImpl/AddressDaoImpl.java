package com.jocata.oms.data.um.dao.dapImpl;

import com.jocata.oms.data.um.config.HibernateUtil;
import com.jocata.oms.data.um.dao.AddressDao;
import com.jocata.oms.datamodel.um.entity.AddressEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl implements AddressDao {
    @Override
    public void saveAddress(AddressEntity address) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(address);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
