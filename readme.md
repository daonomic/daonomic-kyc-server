# simple KYC storage compatible with daonomic-client
KYC storage is based on files (all files are stored in provided directory, /var/kyc by default)

## Compile and run

```
mvn clean package -DskipTests
java -jar target/kyc-server-boot.jar
```

## General Usage

By default http server will run at 8080 port. If you want to run on other port, you have to specify httpPort java system variable
Example of sending httpPort and dataPath:

```shell
java -DhttpPort=8081 -DdataPath=/home/kyc -jar target/kyc-server-boot.jar
```
