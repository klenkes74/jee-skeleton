package de.kaiserpfalzedv.demo.jeetester;

import java.util.UUID;

import de.kaiserpfalzedv.demo.jeetester.api.Service1;
import de.kaiserpfalzedv.demo.jeetester.impl.Service1Bean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-27
 */
public class Service1Test {
    private static final Logger LOG = LoggerFactory.getLogger(Service1Test.class);

    private Service1 cut;

    @Before
    public void setUp() {
        cut = new Service1Bean();
    }

    @Test
    public void returnIdWhenCalledWithString() {
        String id = UUID.randomUUID().toString();

        String result = cut.call(id);

        Assert.assertEquals("The id should have been returned!", id, result);
    }
}
