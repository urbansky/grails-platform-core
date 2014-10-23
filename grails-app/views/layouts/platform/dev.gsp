<!DOCTYPE html>
<html lang="en">
<head>
    <title><g:layoutTitle default="Grails"/></title>
    <plugin:isAvailable name="resources">
        <r:require module="plugin.platformCore.tools"/>
        <r:layoutResources/>
    </plugin:isAvailable>
    <plugin:isNotAvailable name="resources">
        <plugin:isAvailable name="asset-pipeline">
            <asset:stylesheet src="platformTools.css"/>
            <asset:javascript src="platformTools.js"/>
        </plugin:isAvailable>
    </plugin:isNotAvailable>
    <g:layoutHead/>
</head>
<body>
    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <g:link class="brand" controller="platformTools">Grails Platform Core</g:link>
                <nav:primary class="nav" scope="dev"/>
            </div>
        </div>
    </div>

    <div class="container">
        <g:layoutBody/>
    </div>

    <plugin:isAvailable name="resources">
        <r:layoutResources/>
    </plugin:isAvailable>

    <plugin:isAvailable name="asset-pipeline">
        <asset:deferredScripts/>
    </plugin:isAvailable>
</body>
</html>