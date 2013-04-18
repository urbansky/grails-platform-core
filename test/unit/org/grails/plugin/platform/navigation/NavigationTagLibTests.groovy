package org.grails.plugin.platform.navigation

import grails.test.mixin.TestFor

import org.grails.plugin.platform.NavigationTagLib

@TestFor(NavigationTagLib)
class NavigationTagLibTests {

	void testIsVisibleClosureHasCorrectContext() {
        boolean isVisibleCalled = false
		def testNode = new NavigationItem([
            name:'home',
			linkArgs:[action:'home'],
			visible: { ->
				assert item
				isVisibleCalled = true
		}])
        def parentNode = new NavigationItem([
            linkArgs:[controller:'test'],
            children:[testNode]
        ])

        def mockNav = [:]
        mockNav.nodesForPath = { String path ->
 			[parentNode]
 		}
        mockNav.nodeForId = { String id ->
            parentNode
        }
        mockNav.getDefaultScope = { request -> 'app' }

        tagLib.grailsNavigation = mockNav

		def output = applyTemplate("<nav:menu path='app'/>", [:]);

		assert isVisibleCalled
	}
}