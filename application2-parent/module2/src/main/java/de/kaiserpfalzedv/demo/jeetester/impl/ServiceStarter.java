package de.kaiserpfalzedv.demo.jeetester.impl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import de.kaiserpfalzedv.demo.jeetester.api.Service2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-28
 */
@Singleton
@Startup
public class ServiceStarter {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceStarter.class);

    @EJB(beanInterface = Service2.class, lookup = "java:ejb/service2")
    private Service2 bean;

    @PostConstruct
    public void init() {
        //noinspection EjbThisExpressionInspection
        LOG.info("Starting the beans: {}", this);

        LOG.info("Quartz data: {}", System.getProperty("quartz-ds"));

        bean.call(toString());
    }
}
