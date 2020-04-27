/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;


/**
* @Description: 核心功能是获取SecurityManager以及Subject
* @Author: FredJie
* @Date: 2020/4/27
*/
public abstract class SecurityUtils {

    /**
    * @Description: 因为使用static定义SecurityManager，所以SecurityManager对象在应用中时单一存在的；
    * @Author: FredJie
    * @Date: 2020/4/27
    */
    private static SecurityManager securityManager;

    /**
    * @Description: 获取Subject对象
    * @Author: FredJie
    * @Date: 2020/4/27
    */
    public static Subject getSubject() {
        //1.先从ThreadContext中获取Subject对象
        Subject subject = ThreadContext.getSubject();
        //2.如果不存在，则创建新的Subject，再存放到ThreadContext中，以便下次可以获取。
        if (subject == null) {
            //通过 Subject.Builder类提供的buildSubject()方法来创建Subject
            subject = (new Subject.Builder()).buildSubject();
            ThreadContext.bind(subject);
        }
        return subject;
    }

    /**
     * Sets a VM (static) singleton SecurityManager, specifically for transparent use in the
     * {@link #getSubject() getSubject()} implementation.
     * <p/>
     * <b>This method call exists mainly for framework development support.  Application developers should rarely,
     * if ever, need to call this method.</b>
     * <p/>
     * The Shiro development team prefers that SecurityManager instances are non-static application singletons
     * and <em>not</em> VM static singletons.  Application singletons that do not use static memory require some sort
     * of application configuration framework to maintain the application-wide SecurityManager instance for you
     * (for example, Spring or EJB3 environments) such that the object reference does not need to be static.
     * <p/>
     * In these environments, Shiro acquires Subject data based on the currently executing Thread via its own
     * framework integration code, and this is the preferred way to use Shiro.
     * <p/>
     * However in some environments, such as a standalone desktop application or Applets that do not use Spring or
     * EJB or similar config frameworks, a VM-singleton might make more sense (although the former is still preferred).
     * In these environments, setting the SecurityManager via this method will automatically enable the
     * {@link #getSubject() getSubject()} call to function with little configuration.
     * <p/>
     * For example, in these environments, this will work:
     * <pre>
     * DefaultSecurityManager securityManager = new {@link org.apache.shiro.mgt.DefaultSecurityManager DefaultSecurityManager}();
     * securityManager.setRealms( ... ); //one or more Realms
     * <b>SecurityUtils.setSecurityManager( securityManager );</b></pre>
     * <p/>
     * And then anywhere in the application code, the following call will return the application's Subject:
     * <pre>
     * Subject currentUser = SecurityUtils.getSubject();</pre>
     *
     * @param securityManager the securityManager instance to set as a VM static singleton.
     */
    public static void setSecurityManager(SecurityManager securityManager) {
        SecurityUtils.securityManager = securityManager;
    }

   /***
   * @Description: 获取SecurityManager对象
   * @Param: []
   * @return: org.apache.shiro.mgt.SecurityManager
   * @Author: FredJie
   * @Date: 2020/4/27
   */
    public static SecurityManager getSecurityManager() throws UnavailableSecurityManagerException {
        //1.从ThreadContext中获取SecurityManager
        SecurityManager securityManager = ThreadContext.getSecurityManager();
        //2.如果没有，则从SecurityUtils属性securityManager中获取
        if (securityManager == null) {
            securityManager = SecurityUtils.securityManager;
        }
        //3.一定要存在一个SecurityManager实例对象，否则抛异常。
        if (securityManager == null) {
            String msg = "No SecurityManager accessible to the calling code, either bound to the " +
                    ThreadContext.class.getName() + " or as a vm static singleton.  This is an invalid application " +
                    "configuration.";
            throw new UnavailableSecurityManagerException(msg);
        }
        return securityManager;
    }
}
