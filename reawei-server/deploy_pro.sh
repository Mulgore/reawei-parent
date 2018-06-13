#!/usr/bin/env bash
mvn clean package -Dmaven.test.skip -Ppro
scp -r target/root.war agentserver@192.168.31.168:/home/agentserver/builds/
