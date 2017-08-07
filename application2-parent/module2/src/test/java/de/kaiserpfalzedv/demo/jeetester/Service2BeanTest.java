package de.kaiserpfalzedv.demo.jeetester;

import java.util.UUID;

import javax.naming.NamingException;

import de.kaiserpfalzedv.demo.jeetester.api.Service1;
import de.kaiserpfalzedv.demo.jeetester.api.Service2;
import de.kaiserpfalzedv.demo.jeetester.impl.Service2Bean;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-27
 */
public class Service2BeanTest {
    private static final Logger LOG = LoggerFactory.getLogger(Service2BeanTest.class);

    private Service1 bean1;
    private Service2 cut;

    @Before
    public void setUp() {
        bean1 = new Service1() {
            @Override
            public String call(String id) {
                return id;
            }

            @Override
            public String printJndi(String startNode) throws NamingException {
                return "--nothing--";
            }
        };
        cut = new Service2Bean(bean1);
    }


    @Test
    public void returnIdWhenCalledWithString() {
        String id = UUID.randomUUID().toString();

        String result = cut.call(id);

        assertEquals("The id should have been returned!", id, result);
    }
}
