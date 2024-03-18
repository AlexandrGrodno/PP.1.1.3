package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {

        try( Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50),age INT )")
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();

        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users ")
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();

        }
    }



    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name,lastName,age));
            session.getTransaction().commit();
            System.out.printf("User  с именем %s добавлен в таблицу \n",name);
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete  User where id = :id");
            query.setParameter("id", (int)id);
            query.executeUpdate();
            session.getTransaction().commit();

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listUser;
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            listUser = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
//            CriteriaBuilder critBuild = session.getCriteriaBuilder();
//            CriteriaQuery critQuery = critBuild.createQuery(User.class);
//            Root<User> root = critQuery.from(User.class);
//            listUser = session.createQuery(critQuery).getResultList();
        }
        listUser.stream().forEach(System.out::println);
        return listUser;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete  User " ).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
