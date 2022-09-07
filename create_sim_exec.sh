#!/bin/bash
gradle clean fatJar
cd build/libs
echo "#! /usr/bin/env java -jar" > cam_datastax_gatling-all
cat cam_datastax_gatling-all.jar >> cam_datastax_gatling-all
chmod +x cam_datastax_gatling-all
