#!/bin/bash

DATASOURCE_URL_PROD=${{secrets.DATASOURCE_URL_PROD}}
DATASOURCE_USERNAME_PROD=${{secrets.DATASOURCE_USERNAME_PROD}}
DATASOURCE_PASSWORD_PROD=${{secrets.DATASOURCE_PASSWORD_PROD}}
OAUTH_CLIENT_ID_PROD=${{secrets.OAUTH_CLIENT_ID_PROD}}
OAUTH_CLIENT_SECRET_PROD=${{secrets.OAUTH_CLIENT_SECRET_PROD}}
MAIL_USERNAME_PROD=${{secrets.MAIL_USERNAME_PROD}}
MAIL_PASSWORD_PROD=${{secrets.MAIL_PASSWORD_PROD}}
REDIS_HOST_PROD=${{secrets.REDIS_HOST_PROD}}
REDIS_PORT_PROD=${{secrets.REDIS_PORT_PROD}}

export DATASOURCE_URL_PROD
export DATASOURCE_USERNAME_PROD
export DATASOURCE_PASSWORD_PROD
export OAUTH_CLIENT_ID_PROD
export OAUTH_CLIENT_SECRET_PROD
export MAIL_USERNAME_PROD
export MAIL_PASSWORD_PROD
export REDIS_HOST_PROD
export REDIS_PORT_PROD
