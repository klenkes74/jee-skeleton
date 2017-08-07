package de.kaiserpfalzedv.demo.jeetester;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.kaiserpfalzedv.demo.jeetester.api.Service1;
import de.kaiserpfalzedv.demo.jeetester.api.Service2;
import de.kaiserpfalzedv.demo.jeetester.impl.JndiWalker;
import de.kaiserpfalzedv.demo.jeetester.impl.Service1Bean;
import de.kaiserpfalzedv.demo.jeetester.impl.Service2Bean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-29
 */
@RunWith(Arquillian.class)
public class Service2CT {
    private static final Logger LOG = LoggerFactory.getLogger(Service2CT.class);

    @Deployment
    public static Archive<EnterpriseArchive> deploy() {

        Archive<JavaArchive> module1 = ShrinkWrap
                .create(JavaArchive.class, "module1.jar")
                .addClass(Service1.class)
                .addClass(Service1Bean.class)
                .addClass(JndiWalker.class);

        Archive<JavaArchive> module2 = ShrinkWrap
                .create(JavaArchive.class, "module2.jar")
                .addClass(Service1.class)
                .addClass(Service2.class)
                .addClass(Service2Bean.class);

        Archive<JavaArchive> lib = ShrinkWrap
                .create(JavaArchive.class, "test-lib.jar")
                .addClass(Service2CT.class);

        return ShrinkWrap
                .create(EnterpriseArchive.class, "test.ear")
                .addAsModule(module1)
                .addAsModule(module2)
                .addAsLibraries(lib);
    }

    @Test
    public void emptyInContainerTest() throws NamingException {
        InitialContext ctx = new InitialContext();

        Service2 sut = (Service2) ctx.lookup("java:app/module2/Service2Bean");

        String result = sut.call("teststring");
        LOG.info("Result: {}", result);

        assertEquals("teststring", result);
    }
}