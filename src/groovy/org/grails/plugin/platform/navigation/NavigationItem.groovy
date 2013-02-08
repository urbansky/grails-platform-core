/* Copyright 2011-2013 the original author or authors:
 *
 *    Marc Palmer (marc@grailsrocks.com)
 *    Stéphane Maldini (smaldini@vmware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.plugin.platform.navigation

import org.grails.plugin.platform.util.ViewCallbackDelegate

/**
 * Immutable encapsulation of an item in the navigation structure
 * Instances of this are shared globally and available to requests so
 * this must be immutable and threadsafe
 */
class NavigationItem extends NavigationScope {
    private Integer order

    private String titleDefault

    private Map linkArgs
    private List actionAliases
    private String titleMessageCode

    private boolean visible = true
    private Closure visibleClosure

    private boolean enabled = true
    private Closure enabledClosure

    private Map data

    NavigationItem(Map args) {
        super(args)
        order = args.order
        data = args.data != null ? args.data : Collections.EMPTY_MAP
        linkArgs = args.linkArgs.asImmutable()
        actionAliases = args.actionAliases
        titleMessageCode = args.titleMessageCode
        titleDefault = args.titleDefault
        if (args.visible == null) {
            args.visible = true
        }
        visible = args.visible instanceof Closure ? false : args.visible
        visibleClosure = args.visible instanceof Closure ? args.visible : null
        if (args.enabled == null) {
            args.enabled = true
        }
        enabled = args.enabled instanceof Closure ? false : args.enabled
        enabledClosure = args.enabled instanceof Closure ? args.enabled : null
    }

    boolean inScope(String scopeName) {
        getRootScope().name == scopeName
    }

    boolean inScope(NavigationScope scope) {
        getRootScope().name == scope.name
    }

    /**
     * Get any application-supplied data that was declared for this item
     * Used for info like icon-names, alt text and so on - custom rendering usage
     */
    Map getData() {
        data
    }

    List getActionAliases() {
        actionAliases
    }

    Integer getOrder() {
        order
    }

    void setOrder(Integer v) {
        order = v
    }

    boolean getLeafNode() {
        leafNode
    }

    String getTitleMessageCode() {
        if (!titleMessageCode) {
            def safeId = id.replaceAll(NODE_PATH_SEPARATOR, '.')
            titleMessageCode = "nav.${safeId}" // captures original id, so i18n continues to work even if moved in hierarchy
        }
        titleMessageCode
    }

    String getTitleDefault() {
        titleDefault
    }

    Closure getVisibleClosure() {
        visibleClosure
    }

    Closure getEnabledClosure() {
        enabledClosure
    }

    boolean isVisible(context) {
        if (visibleClosure != null) {
            return invokeCallback(visibleClosure, context)
        } else {
            return visible
        }
    }

    boolean isEnabled(context) {
        if (enabledClosure != null) {
            return invokeCallback(enabledClosure, context)
        } else {
            return enabled
        }
    }

    protected invokeCallback(Closure c, context) {
        def delegate = new ViewCallbackDelegate(context.grailsApplication, context.pageScope, context)

        Closure cloneOfClosure = c.clone()
        cloneOfClosure.delegate = delegate
        cloneOfClosure.resolveStrategy = Closure.DELEGATE_FIRST
        return cloneOfClosure()
    }
}