

## spring-music-cds (built jar on jdk 21, deployed on tas 6.0.13)

#### build.gradle

plugins {
    id 'org.springframework.boot' version '3.3.0'

java {
    sourceCompatibility = '17'
}

...
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17


#### manifest.yml
---
applications:
- name: spring-music-orig
  memory: 1G
  random-route: false
  path: build/libs/spring-music-1.0.jar
  env:
    JBP_CONFIG_SPRING_AUTO_RECONFIGURATION: '{enabled: false}'
    SPRING_PROFILES_ACTIVE: http2
    JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 17.+ } }
#### cf push


cf d spring-music-orig

Downloaded app package (62.3M)
   -----> Java Buildpack v4.79.0 (offline) | https://github.gwd.broadcom.net/TNZ/java-buildpack#9af10b1
   -----> Downloading Jvmkill Agent 1.17.0_RELEASE from https://java-buildpack.cloudfoundry.org/jvmkill/jammy/x86_64/jvmkill-1.17.0-RELEASE.so (found in cache)
   -----> Downloading Open Jdk JRE 21.0.6_10 from https://storage.googleapis.com/java-buildpack-dependencies/openjdk/jammy/x86_64/bellsoft-jre21.0.6%2B10-linux-amd64.tar.gz (found in cache)
   Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.4s)
   JVM DNS caching disabled in lieu of BOSH DNS caching
   -----> Downloading Open JDK Like Memory Calculator 3.13.0_RELEASE from https://java-buildpack.cloudfoundry.org/memory-calculator/jammy/x86_64/memory-calculator-3.13.0-RELEASE.tar.gz (found in cache)
   Loaded Classes: 29206, Threads: 250
   -----> Downloading Client Certificate Mapper 2.0.1 from https://storage.googleapis.com/java-buildpack-dependencies/client-certificate-mapper/client-certificate-mapper-2.0.1.jar (found in cache)
   -----> Downloading Container Security Provider 1.20.0_RELEASE from https://storage.googleapis.com/java-buildpack-dependencies/container-security-provider/container-security-provider-1.20.0-RELEASE.jar (found in cache)
   Exit status 0


      2025-05-26T14:23:27.09+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:23:27.096Z  INFO 31 --- [           main] o.c.samples.music.Application            : Started Application in 7.122 seconds (process running for 7.78)
   2025-05-26T14:23:27.34+0900 [CELL/0] OUT Container became healthy