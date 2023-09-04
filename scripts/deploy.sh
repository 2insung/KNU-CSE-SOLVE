#!/bin/bash

# 1. env variable
source ~/.bash_profile
echo "1. env variable setting complete"


# 2. cron delete
touch crontab_delete
crontab crontab_delete
rm crontab_delete
echo "2. cron delete complete"

# 3. server checking
if [ -n "${PROJECT_PID}" ]; then
        kill -9 ${PROJECT_PID}
        echo "3. project kill complete"
else
        sudo yum -y update 1>/dev/null
        echo "3-1. yum update complete"

        sudo yum -y install java-11-amazon-corretto.x86_64
        echo "3-2. jdk install complete"

        sudo timedatectl set-timezone Asia/Seoul
        echo "3-3. timezone setting complete"
fi


# 4. start jar
nohup java -jar -Dspring.profiles.active=prod ${JAR_PATH} 1>${HOME}/log.out 2>${HOME}/err.out &
echo "4. start server complete"

# 5. cron registration
touch crontab_new
echo "* * * * * ${HOME}/check-and-restart.sh" 1>>crontab_new
# register the others..
crontab crontab_new
rm crontab_new
echo "5. registration complete"