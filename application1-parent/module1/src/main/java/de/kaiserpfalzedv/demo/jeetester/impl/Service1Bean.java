package de.kaiserpfalzedv.demo.jeetester.impl;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PreRemove;

import de.kaiserpfalzedv.demo.jeetester.api.Service1;
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
@TransactionManagement(TransactionManagementType.BEAN)
public class Service1Bean implements Service1 {
    private static final Logger LOG = LoggerFactory.getLogger(Service1Bean.class);

    private InitialContext ctx = null;


    @PostConstruct
    public void init() {
        //noinspection EjbThisExpressionInspection
        LOG.trace("***** Created: {}", this);

        try {
            ctx = new InitialContext();

            printJndi("java:");

            printJndi("java:jboss");

            printJndi("java:global");

            printJndi("java:app");

            printJndi("java:module");

            LOG.info("Starting application: {}", ctx.lookup("java:app/AppName"));
        } catch (NamingException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }
    }

    @PreRemove
    public void close() {
        try {
            if (ctx != null) {
                ctx.close();
            }
        } catch (NamingException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }
        //noinspection EjbThisExpressionInspection
        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public String call(final String id) {
        //noinspection EjbThisExpressionInspection
        LOG.info("{} called with id: {}", this, id);

        return id;
    }

    @Override
    public String printJndi(String startNode) throws NamingException {
        JndiWalker walker = new JndiWalker();

        String result = walker.printJndi(ctx, startNode);
        LOG.info("The JNDI tree starting with '{}':\n{}\n-- -- -- -- --", startNode, result);

        return result;
    }
}
