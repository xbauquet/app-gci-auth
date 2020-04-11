FROM openjdk:14-alpine AS builder

MAINTAINER Xavier Bauquet <xavier.bauquet@gmail.com>

RUN apk add --no-cache binutils

RUN mkdir -p /home/workdir/libs/
COPY build/libs/*.jar /home/workdir/libs/
WORKDIR /home/workdir/
RUN jpackage -n app --input libs --main-jar app-gci-auth-1.0.jar -t app-image

# ---
FROM alpine:3.9.5

MAINTAINER Xavier Bauquet <xavier.bauquet@gmail.com>

RUN mkdir -p /home/workdir/app/
COPY --from=builder /home/workdir/app/ /home/workdir/app/
WORKDIR /home/workdir/app/bin/

CMD ["./app"]
