## What this app does?
Reads CVS and sends kafka messages to "test" topic

Uses simple kafka messaging
No cloud stream has been used in this example

## Kafka installation steps

```
brew install kafka
```

This installs zookeeper and kafka

Stop both zookeepr and kafka. Edit kafka config to uncomment and update below properties. Save the file

```
brew services stop kafka
brew services stop zookeeper
```

```
listeners=PLAINTEXT://localhost:9092
advertised.listeners=PLAINTEXT://localhost:9092
```

```
brew services start zookeeper
brew services start kafka
```

```
brew services
```

kafka             started deus /Users/deus/Library/LaunchAgents/homebrew.mxcl.kafka.plist
zookeeper         started deus /Users/deus/Library/LaunchAgents/homebrew.mxcl.zookeeper.plist

## Highlights of this new version

Enabled spring cloud messaging with kafka binder.
Key advantage is the code looks really simple compared to the one with kafka producer factory configuration. 

* Create an interface and annotate method with @Output to create a MessageChannel
* This interface can be injected, meaning a proxy gets instantiated by the spring cloud and makes the object available on the fly
* Using the interface method that returns MessageChannel we can then call sendMessage on it to send the message
* Another thing I noticed was is that the content type is specificed as application/json in the application.yml, by this the message is sent as json out of box
* The interface that we create has to be used to enable a binding.
  This is the config that makes available a proxy instance on the fly.

  ```
  @EnableBinding(CsvStreams.class)
  public class CsvStreamsConfig {
  
  }
  ```
* A service class just serves the purpose to be able to send the message
  * the sendMessage method accepts a Message. The message builder accepts a payload and header.
  
  ```
  MessageChannel messageChannel = csvStreams.outboundCsvMessage();
  messageChannel.send(MessageBuilder
          .withPayload(employee)
          .setHeader("Content-Type", "application/json")
          .build()
  );
  ```
  * application.yml kafka binder config
  ```
  spring:
    cloud:
      stream:
        kafka:
          binder:
            auto-create-topics: true # tell kafka to create topic
            brokers:
              - localhost:9092 # list of kafka brokers
        bindings.susbcriptionorders: # this can be any name. this name has to be used on @Output channel
          destination: fs.checkout.subscription.orders # this is name of topic
          contentType: "application/json" # this specifies what type of content is produced by the producer 
  ```
