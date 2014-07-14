/*
 * Licensed under the GPL License.  You may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package backingbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author Camila.Riveron
 */
@ManagedBean(name = "HibernateSessionBean")
@SessionScoped
public class HibernateSessionBean {

    private Session session;

    public HibernateSessionBean() {
        super();
    }

    public Session getSession() {
        if ((session == null) || (!session.isOpen())) {
            SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
        }
        return session;
    }

    public void closeSession() {
        if ((session != null) && (session.isOpen())) {
            session.close();
        }
    }

    public void resetSesion() {
        if (session != null) {
            SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
        }
    }
}
