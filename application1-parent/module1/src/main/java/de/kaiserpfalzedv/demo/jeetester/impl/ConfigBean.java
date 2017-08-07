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

package de.kaiserpfalzedv.demo.jeetester.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PreRemove;

import de.kaiserpfalzedv.demo.jeetester.api.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-30
 */
@Local(Config.class)
@Stateless
public class ConfigBean implements Config {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigBean.class);

    private InitialContext ctx;


    @PostConstruct
    @PostActivate
    public void init() {
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new IllegalStateException("Can't connect to initial context: " + e.getMessage(), e);
        }
    }

    @PreRemove
    @PrePassivate
    public void close() {
        if (ctx != null) {
            try {
                ctx.close();
                ctx = null;
            } catch (NamingException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            }
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> get(String key, Class<T> clasz) {
        try {
            T result = (T) ctx.lookup(key);

            if (!clasz.isAssignableFrom(result.getClass())) {
                return Optional.empty();
            }

            return Optional.ofNullable((T) ctx.lookup(key));
        } catch (NamingException e) {
            LOG.error("Can't read '{}' of type '{}' ({}): {}",
                      key, clasz.getSimpleName(), e.getClass().getSimpleName(), e.getMessage()
            );

            return Optional.empty();
        }
    }
}
