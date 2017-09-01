FROM python:3.6.2-alpine3.6

ARG USERNAME
ARG PASSWORD
ARG IDENTITY_DOMAIN
ARG PSM_USERNAME
ARG PSM_PASSWORD
ARG PSM_REGION
ARG PSM_OUTPUT

WORKDIR "/oracle-cloud-psm-cli/"

RUN apk add --update curl && \
    rm -rf /var/cache/apk/*

RUN curl -X GET -u $USERNAME:$PASSWORD -H X-ID-TENANT-NAME:$IDENTITY_DOMAIN https://psm.us.oraclecloud.com/paas/core/api/v1.1/cli/$IDENTITY_DOMAIN/client -o psmcli.zip && \
	pip3 install -U psmcli.zip && \
	echo -e "$PSM_USERNAME\n$PSM_PASSWORD\n$PSM_PASSWORD\n$IDENTITY_DOMAIN\n$PSM_REGION\n$PSM_OUTPUT" | psm setup

COPY accs-hello-world/target/accs-hello-world.zip .
COPY accs-hello-world/deployment.json .

CMD ["/bin/sh"]