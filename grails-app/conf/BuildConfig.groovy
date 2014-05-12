grails.project.work.dir = 'target'

grails.project.dependency.resolver='maven'
grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        build('org.grails:grails-gdoc-engine:1.0.1') {
            export = false
        }

        test("org.spockframework:spock-grails-support:0.7-groovy-2.0") {
            export = false
        }
    }

    plugins {
        build ':release:3.0.1', ':rest-client-builder:2.0.1', {
            export = false
        }

        runtime(":resources:1.2.8") {
            export = false
        }

    }
}
