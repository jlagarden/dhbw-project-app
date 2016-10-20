#!/bin/bash

java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar  -o project-app/tmp -d 1 -amqp tcp://127.0.0.1:61616 -kafka localhost:9092 -topic prod --fastforward
