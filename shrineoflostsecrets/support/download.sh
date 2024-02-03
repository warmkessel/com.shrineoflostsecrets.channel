#!/bin/bash

rm -rf com.shrineoflostsecrets.channel/
git clone git@github.com:warmkessel/com.shrineoflostsecrets.channel.git
cd com.shrineoflostsecrets.channel/shrineoflostsecrets/
mv pom.xml pom.xml.orig
mv pom2.xml pom.xml
mvn clean install
mv pom.xml pom.xml2
mv pom.xml.orig pom.xom
cd