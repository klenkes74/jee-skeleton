package de.kaiserpfalzedv.demo.jeetester;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.kaiserpfalzedv.demo.jeetester.api.Service1;
import de.kaiserpfalzedv.demo.jeetester.impl.JndiWalker;
import de.kaiserpfalzedv.demo.jeetester.impl.Service1Bean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-29
 */
@RunWith(Arquillian.class)
public class Service1CT {
    private static final Logger LOG = LoggerFactory.getLogger(Service1CT.class);

    @Deployment
    public static Archive<JavaArchive> deploy() {
        return ShrinkWrap
                .create(JavaArchive.class, "test.jar")
                .addClass(Service1.class)
                .addClass(Service1Bean.class)
                .addClass(JndiWalker.class);
    }

    @Test
    public void emptyInContainerTest() throws NamingException {
        InitialContext ctx = new InitialContext();

        Service1 sut = (Service1) ctx.lookup("java:module/Service1Bean");

        String result = sut.printJndi("java:module");
        LOG.info("Result: {}", result);

        assertTrue(result.contains("/Service1Bean"));
    }
}