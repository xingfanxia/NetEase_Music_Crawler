# deploy.sh

#!/bin/bash

SERVER="162.243.157.104"
JAR="build/libs/netEase-Crawler-0.1.jar"

echo "Building $JAR..."
./gradle build

echo "Upload $JAR to server $SERVER..."
scp $JAR root@$162.243.157.104:/root/netEase-Crawler/

python deploy.py
