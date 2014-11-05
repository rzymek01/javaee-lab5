/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.entities.User;

/**
 *
 * @author psysiu
 */
@Stateless
@LocalBean
@Log
public class UserService {

    @PersistenceContext
    EntityManager em;

//    public User findUser(String login) {
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery<User> query = builder.createQuery(User.class);
//        return em.createQuery(query.where(query.from(User.class).get(User_.login).in(login))).getSingleResult();
//    }

    public List<User> findAllUsers() {
        return em.createNamedQuery("User.findAll").getResultList();
    }
}
