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