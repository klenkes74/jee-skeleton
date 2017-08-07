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
