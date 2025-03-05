package com.jocata.oms.data.um.dao.dapImpl;

import com.jocata.oms.data.um.config.HibernateUtil;
import com.jocata.oms.data.um.dao.UserDao;
import com.jocata.oms.datamodel.um.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public void saveUser(UserEntity user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            e.printStackTrace();
        }
    }


    @Override
    @Transactional
    public UserEntity getUserById(Integer userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(UserEntity.class, userId);
        }
    }

    @Override
    @Transactional
    public UserEntity getUserByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM UserEntity WHERE email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    @Override
    @Transactional
    public List<UserEntity> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from UserEntity", UserEntity.class).list();
        }
    }

    @Override
    public UserEntity findUserByEmailAndPassword(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM UserEntity WHERE email = :email AND passwordHash = :password", UserEntity.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        }
    }




    @Override
    public void updateUser(UserEntity user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Integer userId, Boolean flag) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserEntity user = session.get(UserEntity.class, userId);

            if (user != null) {
                if (flag) {
                    user.setDeletedAt(LocalDateTime.now());
                    session.merge(user);
                } else {
                    session.remove(user);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


/*    @Override
    public void deleteUser(Integer userId  , Boolean flag) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserEntity user = session.get(UserEntity.class, userId);
            if (user != null) session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }*/
}