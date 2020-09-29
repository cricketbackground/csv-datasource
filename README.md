## What this app does?
Reads CVS and produces kafka messages

Uses simple kafka messaging
No cloud stream has been used in this example


update kafka config /usr/local/etc/kafka/server.properties
listeners=PLAINTEXT://localhost:9092
advertised.listeners=PLAINTEXT://localhost:9092