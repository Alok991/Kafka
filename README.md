# Kafka Demo
This is a small demo of Apache Kafka distributed streaming platform

## Getting started
This Repo contains 3 intelliJ idea projects (maven) namely Producer, ConsumerEnglish and ConsumerMath

### Explanation
#### Producer
This is a producer publishing 2 topic messages namely "MathTopic" and "EnglishTopic"

#### ConsumerEnglish
This is Consumer which subscribes to "EnglishTopic" and do some dummy processing

#### ConsumerMath
Similarly It subscribes to "MathTopic" and do the dummy calculations

### Kafka cluster Architecture

The architecure contains 2 Brokers running on the same machine (distinguished by port instead of IP) along with a zookeeper

## Running

0. Download latest Kafka framework, extract and cd into it
1. Download and make 3 separate IntelliJ idea projects
2. Start the Zookeeper

```
./bin/zookeeper-server-start.sh ./config/zookeeper.properties
```
3. Make two kafka broker

Broker-0

```
./bin/kafka-server-start.sh ./config/server.properties
```

Broker-1

```
cp ./config/server.properties ./config/server-1.properties
```

Now open this file 

```
nano ./config/server-1.properties
```

and change these 3 parameters

```
broker.id=1
listeners=PLAINTEXT://:9093 (and uncomment it too)
log.dirs=/tmp/kafka-logs-1
```

Start Broker-1

```
./bin/kafka-server-start.sh ./config/server-1.properties
```

4. Make 2 Topics [OPTIONAL]

```
./bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic EnglishTopic --partitions 2 --replication-factor 3
```

```
./bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic MathTopic --partitions 2 --replication-factor 3
```

5. Now build and run the Consumer projects

6. Build and Run the Producer projects

