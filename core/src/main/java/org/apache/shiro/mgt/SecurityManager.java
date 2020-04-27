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
package org.apache.shiro.mgt;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;


/**
 * A {@code SecurityManager} executes all security operations for <em>all</em> Subjects (aka users) across a
 * single application.
 * <p/>
 * The interface itself primarily exists as a convenience - it extends the {@link org.apache.shiro.authc.Authenticator},
 * {@link Authorizer}, and {@link SessionManager} interfaces, thereby consolidating
 * these behaviors into a single point of reference.  For most Shiro usages, this simplifies configuration and
 * tends to be a more convenient approach than referencing {@code Authenticator}, {@code Authorizer}, and
 * {@code SessionManager} instances separately;  instead one only needs to interact with a single
 * {@code SecurityManager} instance.
 * <p/>
 * In addition to the above three interfaces, this interface provides a number of methods supporting
 * {@link Subject} behavior. A {@link org.apache.shiro.subject.Subject Subject} executes
 * authentication, authorization, and session operations for a <em>single</em> user, and as such can only be
 * managed by {@code A SecurityManager} which is aware of all three functions.  The three parent interfaces on the
 * other hand do not 'know' about {@code Subject}s to ensure a clean separation of concerns.
 * <p/>
 * <b>Usage Note</b>: In actuality the large majority of application programmers won't interact with a SecurityManager
 * very often, if at all.  <em>Most</em> application programmers only care about security operations for the currently
 * executing user, usually attained by calling
 * {@link org.apache.shiro.SecurityUtils#getSubject() SecurityUtils.getSubject()}.
 * <p/>
 * Framework developers on the other hand might find working with an actual SecurityManager useful.
 *
 * @see org.apache.shiro.mgt.DefaultSecurityManager
 * @since 0.2
 */
public interface SecurityManager extends Authenticator, Authorizer, SessionManager {

    /**
    * @Description: 登录
    * @Param: [subject, authenticationToken]
    * @return: org.apache.shiro.subject.Subject
    * @Author: FredJie
    * @Date: 2020/4/27
    */
    Subject login(Subject subject, AuthenticationToken authenticationToken) throws AuthenticationException;

    /**
    * @Description: 登出
    * @Param: [subject]
    * @return: void
    * @Author: FredJie
    * @Date: 2020/4/27
    */
    void logout(Subject subject);

    /**
    * @Description: 创建Subject
    * @Param: [context]
    * @return: org.apache.shiro.subject.Subject
    * @Author: FredJie
    * @Date: 2020/4/27
    */
    Subject createSubject(SubjectContext context);

}
