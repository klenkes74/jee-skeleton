package de.kaiserpfalzedv.demo.jeetester.api;

import javax.naming.NamingException;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-27
 */
public interface Service1 {
    String call(String id);

    String printJndi(String startNode) throws NamingException;
}
