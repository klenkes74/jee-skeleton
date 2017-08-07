/*
 *    Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.kaiserpfalzedv.demo.jeetester;

import java.util.Optional;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.kaiserpfalzedv.demo.jeetester.api.Config;
import de.kaiserpfalzedv.demo.jeetester.impl.ConfigBean;
import de.kaiserpfalzedv.demo.jeetester.impl.JndiWalker;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-30
 */
public class ConfigTest {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigTest.class);

    private Config sut;

    @BeforeClass
    public static void listJndi() throws NamingException {
        MDC.put("test-class", ConfigTest.class.getSimpleName());
        MDC.put("test", "jndiWalk");

        InitialContext ctx = new InitialContext();

        JndiWalker walker = new JndiWalker();
        LOG.debug("JNDI walk for '{}':\n{}\n-- -- -- -- --", "java:app/env", walker.printJndi(ctx, "java:app/env"));
        LOG.debug("JNDI walk for '{}':\n{}\n-- -- -- -- --", "java:module/env", walker.printJndi(ctx, "java:module/env"));

        ctx.close();
        MDC.remove("test");
    }

    @Before
    public void setUp() {
        MDC.put("test-class", ConfigTest.class.getSimpleName());
        MDC.put("test", "setUp");

        sut = new ConfigBean();
        ((ConfigBean) sut).init();
    }

    @After
    public void tearDown() {
        ((ConfigBean) sut).close();

        MDC.remove("test");
        MDC.remove("test-class");
    }


    @Test
    public void readAppImplicitString() {
        MDC.put("test", "readAppImplicitString");
        Optional<String> result = sut.get("java:app/env/test_implicit_string", String.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertTrue(result.isPresent());
        assertEquals("this property", result.get());
    }

    @Test
    public void readAppExplicitString() {
        MDC.put("test", "readAppExplicitString");
        Optional<String> result = sut.get("java:app/env/test_explicit_string", String.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertTrue(result.isPresent());
        assertEquals("5", result.get());
    }

    @Test
    public void readAppNumeric() {
        MDC.put("test", "readAppNumeric");
        Optional<Integer> result = sut.get("java:app/env/test_numeric", Integer.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertTrue(result.isPresent());
        assertEquals(5L, result.get().longValue());
    }

    @Test
    public void readAppBoolean() {
        MDC.put("test", "readAppBoolean");
        Optional<Boolean> result = sut.get("java:app/env/test_boolean", Boolean.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertTrue(result.isPresent());
        assertEquals(true, result.get());
    }


    @Test
    public void readModuleImplicitString() {
        MDC.put("test", "readModuleImplicitString");
        Optional<String> result = sut.get("java:module/env/test_implicit_string", String.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertTrue(result.isPresent());
        assertEquals("module-env", result.get());
    }

    @Test
    public void readModuleExplicitString() {
        MDC.put("test", "readModuleExplicitString");
        Optional<String> result = sut.get("java:module/env/test_explicit_string", String.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertTrue(result.isPresent());
        assertEquals("4", result.get());
    }

    @Test
    public void readModuleNumeric() {
        MDC.put("test", "readModuleNumeric");
        Optional<Integer> result = sut.get("java:module/env/test_numeric", Integer.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertTrue(result.isPresent());
        assertEquals(4L, result.get().longValue());
    }

    @Test
    public void readModuleBoolean() {
        MDC.put("test", "readModuleBoolean");
        Optional<Boolean> result = sut.get("java:module/env/test_boolean", Boolean.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertTrue(result.isPresent());
        assertEquals(false, result.get());
    }

    @Test
    public void readModuleBooleanAsString() {
        MDC.put("test", "readModuleBooleanAsString");
        Optional<String> result = sut.get("java:module/env/test_boolean", String.class);
        result.ifPresent(d -> LOG.debug("Result: {}", d));

        assertFalse(result.isPresent());
    }
}
