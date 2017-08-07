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

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NotContextException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-07-30
 */
public class JndiWalker {
    private static final Logger LOG = LoggerFactory.getLogger(JndiWalker.class);


    public String printJndi(final InitialContext ctx, final String startNode) throws NamingException {
        StringBuffer sb = new StringBuffer();

        sb.append(startNode).append("/");
        loopLevel(sb, ctx, startNode, 0);

        return sb.toString();
    }

    private void loopLevel(
            StringBuffer sb, InitialContext initialContext, String name,
            int level
    ) throws NamingException {
        NamingEnumeration<NameClassPair> ne;
        try {
            ne = initialContext.list(name);
        } catch (NotContextException e) {
            return;
        }


        while (ne.hasMoreElements()) {
            NameClassPair ncp = ne.nextElement();

            sb.append("\n");

            for (int i = 0; i < level; i++) {
                sb.append("| ");
            }

            sb.append("+-- /")
              .append(ncp.getName())
              .append(" (type: ").append(ncp.getClassName()).append(")")
              .append(": ").append(initialContext.lookup(name + "/" + ncp.getName()));


            if (level <= 5 && !"org.jboss.as.security.plugins.SecurityDomainContext".equals(ncp.getClassName()))
                loopLevel(sb, initialContext, name + "/" + ncp.getName(), level + 1);
        }
    }

}
