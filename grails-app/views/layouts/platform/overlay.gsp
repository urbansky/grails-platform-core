<!DOCTYPE html>
<html lang="en">
<head>
    <title><g:layoutTitle default="Grails"/></title>
    <plugin:isAvailable name="resources">
        <r:layoutResources/>
    </plugin:isAvailable>
    <g:layoutHead/>
</head>
<body>
    <g:layoutBody/>

    <plugin:isAvailable name="resources">
        <r:layoutResources/>
    </plugin:isAvailable>

    <plugin:isAvailable name="asset-pipeline">
        <asset:deferredScripts/>
    </plugin:isAvailable>
</body>
</html>