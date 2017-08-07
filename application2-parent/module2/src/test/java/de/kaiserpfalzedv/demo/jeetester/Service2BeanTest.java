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
