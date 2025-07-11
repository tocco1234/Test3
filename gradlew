#!/bin/bash
##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Locate java
if [ -n "$JAVA_HOME" ] ; then
    JAVA_EXE="$JAVA_HOME/bin/java"
else
    JAVA_EXE="$(which java)"
fi

if [ ! -x "$JAVA_EXE" ] ; then
    echo "ERROR: JAVA_HOME is not set and no 'java' found in PATH." >&2
    exit 1
fi

# Execute Gradle wrapper
exec "$JAVA_EXE" -jar "$(dirname "$0")/gradle/wrapper/gradle-wrapper.jar" "$@"
