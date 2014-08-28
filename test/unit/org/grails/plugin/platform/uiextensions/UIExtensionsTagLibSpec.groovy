package org.grails.plugin.platform.uiextensions

import grails.test.mixin.TestFor
import org.grails.plugin.platform.UiExtensionsTagLib
import org.springframework.context.MessageSource
import org.springframework.context.support.StaticMessageSource
import spock.lang.Shared
import spock.lang.Specification

/**
 * Spock tests for UiExtensionsTagLib
 *
 * @author Dmitry Divin
 */
@TestFor(UiExtensionsTagLib)
class UIExtensionsTagLibSpec extends Specification {
    @Shared def messageSource = new StaticMessageSource()

    def setupSpec() {
        messageSource.useCodeAsDefaultMessage = true
        //i18n demo data
        messageSource.addMessage("plugin.test.test.message", Locale.ENGLISH, "test msg")
        messageSource.addMessage("plugin.test.empty.message", Locale.ENGLISH, "")
    }

    def setup() {
        mockMessageTag(tagLib, messageSource)
        //set default i18n scope
        tagLib.pageScope['plugin.platformCore.ui.text.scope'] = "plugin.test"
    }

    void "test resolve message"() {
        expect:
        tagLib.text(code: "unknown.message") == "plugin.test.unknown.message"
        tagLib.text(code: "test.message") == "test msg"
        tagLib.text(code: "empty.message") == ""
    }

    static void mockMessageTag(artefact, MessageSource messageSource) {
        artefact.metaClass.g = [message: { attrs ->
            messageSource.getMessage(attrs.code, attrs.args as Object[], Locale.ENGLISH)
        }]
    }
}
