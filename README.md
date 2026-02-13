# Important notes

1. Code accompanies the paper [Development of Oberon-0 using YAJCo](https://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=8327233)
2. Forked from https://git.kpi.fei.tuke.sk/sergej.chodarev/yajco-oberon0
3. requires exactly openjdk-11 (see dockerfile for a running example)

## Docker file

The following dockerfile builds and runs the Oberon0 code

```
FROM ubuntu:24.04

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get upgrade -y && apt-get install -y openjdk-11-jdk git maven && apt-get clean

WORKDIR /

RUN git clone https://github.com/niko-vcc/yajco-oberon0 yajco

WORKDIR /yajco

RUN mvn compile
```

# Original README

Implementation of Oberon-0 using YAJCo
======================================

YAJCo is a tool for the development of software languages based on an annotated language model. The model is represented by Java classes with annotations defining their mapping to concrete syntax. This approach to language definition enables the abstract syntax to be central point of the development process, instead of concrete syntax. This project is a case study of Oberon-0 programming language development. The study is based on the [LTDA Tool Challenge][LDTA] and showcases details of abstract and concrete syntax definition using YAJCo, as well as implementation of name resolution, type checking, model transformation and code generation. The language was implemented in modular fashion to demonstrate language extension mechanisms supported by YAJCo.

[LDTA]: http://ldta.info/tool.html

The project can be built using Maven:

    mvn package
    
This would create the JAR file with the compiler from Oberon-0 to C. You can compile an example program using the following command:

    java -jar target/oberon0-1.0-SNAPSHOT-jar-with-dependencies.jar example.oberon
