package de.kaiserpfalzedv.demo.jeetester.api;

import java.util.Optional;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-30
 */
public interface Config {
    /**
     * Returns the configuration by looking the key up in JNDI.
     *
     * @param key   The JNDI name of the data to retrieve.
     * @param clasz The class type of the data to be returned.
     * @param <T>   The type of the data to be returned.
     *
     * @return An optional of the given type. If the key does not exist in JNDI or can not be cast to the given type
     * the optionial will be empty.
     */
    <T> Optional<T> get(final String key, Class<T> clasz);
}
