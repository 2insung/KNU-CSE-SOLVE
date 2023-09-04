#!/bin/bash
echo "Current directory: $(pwd)" >> ${HOME}/deploy.log

# 1. env variable
source ./projectVariable.sh 2>> ${HOME}/deploy-error.log
source ./prodVariable.sh 2>> ${HOME}/deploy-error.log
echo ${DATASOURCE_URL_PROD} >> ${HOME}/deploy.log
echo "1. env variable setting complete" >> ${HOME}/deploy.log


# 2. cron delete
touch crontab_delete
crontab crontab_delete
rm crontab_delete
echo "2. cron delete complete" >> ${HOME}/deploy.log

# 3. server checking
if [ -n "${PROJECT_PID}" ]; then
        kill -9 ${PROJECT_PID}
        echo "3. project kill complete" >> ${HOME}/deploy.log
else
        sudo yum -y update 1>/dev/null
        echo "3-1. yum update complete" >> ${HOME}/deploy.log

        sudo yum -y install java-11-amazon-corretto.x86_64
        echo "3-2. jdk install complete" >> ${HOME}/deploy.log

        sudo timedatectl set-timezone Asia/Seoul
        echo "3-3. timezone setting complete" >> ${HOME}/deploy.log
fi


# 4. gradlew +x
chmod u+x ${HOME}/${PROJECT_NAME}/gradlew
echo "4. gradlew u+x complete" >> ${HOME}/deploy.log

# 5. build
cd ${HOME}/${PROJECT_NAME}
./gradlew clean build
echo "5. gradlew build complete" >> ${HOME}/deploy.log


# 6. start jar
nohup java -jar -Dspring.profiles.active=prod ${JAR_PATH} 1>${HOME}/log.out 2>${HOME}/err.out &
echo "6. start server complete" >> ${HOME}/deploy.log

# 7. cron registration
touch crontab_new
echo "* * * * * ${HOME}/${PROJECT_NAME}/scripts/check-and-restart.sh" 1>>crontab_new
# register the others..
crontab crontab_new
rm crontab_new
echo "7. registration complete" >> ${HOME}/deploy.log