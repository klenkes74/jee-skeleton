package de.kaiserpfalzedv.demo.jeetester.impl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PreRemove;

import de.kaiserpfalzedv.demo.jeetester.api.Service1;
import de.kaiserpfalzedv.demo.jeetester.api.Service2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-27
 */
@Singleton
@Startup
@Remote
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Service2Bean implements Service2 {
    private static final Logger LOG = LoggerFactory.getLogger(Service2Bean.class);


    @EJB(beanInterface = Service1.class)
    private Service1 otherBean;


    public Service2Bean() {}

    public Service2Bean(final Service1 bean1) {
        this.otherBean = bean1;
    }

    @PostConstruct
    public void init() {
        //noinspection EjbThisExpressionInspection
        LOG.trace("***** Created: {}", this);
    }

    @PreRemove
    public void close() {
        //noinspection EjbThisExpressionInspection
        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public String call(final String id) {
        //noinspection EjbThisExpressionInspection
        LOG.info("{} called with id: {}", this, id);

        otherBean.call(id);

        return id;
    }
}
