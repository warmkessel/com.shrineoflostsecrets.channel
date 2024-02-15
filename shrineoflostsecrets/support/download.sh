#!/bin/bash

rm -rf com.shrineoflostsecrets.channel/
git clone git@github.com:warmkessel/com.shrineoflostsecrets.channel.git
cd com.shrineoflostsecrets.channel/shrineoflostsecrets/
sed -i 's|<packaging>war</packaging>|<packaging>jar</packaging>|g' pom.xml
mvn clean install
cd