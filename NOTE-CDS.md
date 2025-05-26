
## gradle build failure
```
./gradlew clean assemble

FAILURE: Build failed with an exception.
```
=>  update ./spring-music/gradle/wrapper/gradle-wrapper.properties
```
...
distributionUrl=https\://services.gradle.org/distributions/gradle-8.13-bin.zip
```

## CDS

The feature is supported for Spring Boot 3.3.0+ applications, and takes advantage of the JVM feature Class Data Sharing (CDS) and Spring framework support.

https://techdocs.broadcom.com/us/en/vmware-tanzu/platform/tanzu-platform-for-cloud-foundry/6-0/tpcf/java.html
https://docs.spring.io/spring-boot/how-to/class-data-sharing.html#howto.class-data-sharing.training-run-configuration
https://docs.spring.io/spring-framework/reference/integration/cds.html
https://spring.io/blog/2024/08/29/spring-boot-cds-support-and-project-leyden-anticipation#data-points


Project Leyden + AOT: https://www.youtube.com/watch?v=7S6M8H2hz6w&t=3472s

 
git clone https://github.com/cloudfoundry-samples/spring-music.git


java -version                                                                          
openjdk version "21.0.6" 2025-01-21


java -jar ./build/libs/spring-music-1.0.jar  
process running for 3.059)

java -XX:ArchiveClassesAtExit=spring-music-1.0.jsa -Dspring.context.exit=onRefresh -jar  ./build/libs/spring-music-1.0.jar 

java -XX:SharedArchiveFile=spring-music-1.0.jsa -jar ./build/libs/spring-music-1.0.jar 
process running for 2.381)


cf set-env <app> JBP_CONFIG_SPRING_BOOT_EFFICIENCY '{ aot_enabled: true, cds_enabled: true }'

https://github.com/sdeleuze/spring-boot-cds-demo

