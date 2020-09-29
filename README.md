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

