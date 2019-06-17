FROM ubuntu:latest
ADD . /beeline/
RUN apt-get update && apt-get install -y \
    clojure \
    leiningen \
    gcc \
    zlib1g-dev \
    wget

RUN wget https://github.com/oracle/graal/releases/download/vm-19.0.2/graalvm-ce-linux-amd64-19.0.2.tar.gz
RUN tar -xvf graalvm-ce-linux-amd64-19.0.2.tar.gz
ENV PATH="/graalvm-ce-19.0.2/bin:${PATH}"
RUN gu install native-image
ENV JAVA_OPTS="-Xms1024m -Xmx12g"
WORKDIR /beeline
RUN lein native-image

