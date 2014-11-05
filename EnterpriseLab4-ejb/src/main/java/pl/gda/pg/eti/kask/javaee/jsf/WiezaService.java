package pl.gda.pg.eti.kask.javaee.jsf;

import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.entities.ObjectFactory;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Swiat;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Wieza;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author psysiu
 * @author maciek
 */
@Stateless
@LocalBean
@Log
public class WiezaService implements Serializable {

  @PersistenceContext
  EntityManager em;
//
//  @Resource
//  UserTransaction userTransaction;

//  private SortedMap<Integer, Wieza> wieze;
//
//  private SortedMap<Integer, Mag> magowie;

  public WiezaService() {
//    wieze = new TreeMap<>();
//    magowie = new TreeMap<>();
//    try {
//      JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
//      Unmarshaller u = jaxbContext.createUnmarshaller();
//      Swiat swiat = (Swiat) u.unmarshal(getClass().getResourceAsStream("/pl/gda/pg/eti/kask/javaee/jsf/xml/wieze.xml"));
//      for (Wieza wieza : swiat.getWieza()) {
//        wieze.put(wieza.getId(), wieza);
//        for (Mag mag : wieza.getMag()) {
//          // setting tower to which belongs mag
//          mag.setWieza(wieza, false);
//          magowie.put(mag.getId(), mag);
//        }
//      }
//
//    } catch (JAXBException ex) {
//      log.log(Level.WARNING, ex.getMessage(), ex);
//    }
  }

  private List<Mag> asList(Mag... magowie) {
    return findAllMagowie();
  }

  public List<Wieza> findAllWieze() {
    return em.createNamedQuery("Wieza.findAll").getResultList();
  }

  public Wieza findWieza(int id) {
    return em.find(Wieza.class, id);
  }

  public void removeWieza(Wieza wieza) {
//    try {
//      userTransaction.begin();

      wieza = em.merge(wieza);
      em.remove(wieza);

//      userTransaction.commit();
//    } catch (Exception e) {
//      e.printStackTrace();
//
//      try {
//        userTransaction.rollback();
//      } catch (SystemException e1) {
//        e1.printStackTrace();
//      }
//    }

  }

  public void saveWieza(Wieza wieza) {
//    try {
//      userTransaction.begin();
      if (wieza.getId() > 0) {
        em.merge(wieza);
      } else {
        em.persist(wieza);
      }
//      userTransaction.commit();
//    } catch (Exception e) {
//      e.printStackTrace();
//
//      try {
//        userTransaction.rollback();
//      } catch (SystemException e1) {
//        e1.printStackTrace();
//      }
//    }
  }
  
  public void trenujMagow(int incr) {
    em.createNamedQuery("Mag.increaseManaForAll").setParameter("incr", incr).
        executeUpdate();
  }

  public List<Mag> findAllMagowie() {
    return em.createNamedQuery("Mag.findAll").getResultList();
  }

  public Mag findMag(int id) {
    return em.find(Mag.class, id);
  }

  public void removeMag(Mag mag) {
//    try {
//      userTransaction.begin();

      mag = em.merge(mag);
      em.remove(mag);
//
//      userTransaction.commit();
//    } catch (Exception e) {
//      e.printStackTrace();
//
//      try {
//        userTransaction.rollback();
//      } catch (SystemException e1) {
//        e1.printStackTrace();
//      }
//    }
  }

  public void saveMag(Mag mag) {
//    try {
//      userTransaction.begin();
      if (mag.getId() > 0) {
        em.merge(mag);
      } else {
        em.persist(mag);
      }
//      userTransaction.commit();
//    } catch (Exception e) {
//      e.printStackTrace();
//
//      try {
//        userTransaction.rollback();
//      } catch (SystemException e1) {
//        e1.printStackTrace();
//      }
//    }
  }

  public void marshalSwiat(OutputStream out) {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
      Marshaller m = jaxbContext.createMarshaller();
      Swiat swiat = new Swiat();
      swiat.getWieza().addAll(findAllWieze());
      m.marshal(swiat, out);
    } catch (JAXBException ex) {
      log.log(Level.WARNING, ex.getMessage(), ex);
    }
  }
}
