

## spring-music-cds (built jar on jdk 21, deployed on tas 6.0.13)

#### build.gradle

plugins {
    id 'org.springframework.boot' version '3.3.0'

java {
    sourceCompatibility = '17'
}


...
tasks.named("bootBuildImage") {
    builder = "paketobuildpacks/builder-jammy-base:latest"
	environment["BP_JVM_CDS_ENABLED"] = "true"
	environment["BP_SPRING_AOT_ENABLED"] = "false"
}



#### manifest.yml

---
applications:
- name: spring-music-cds
  memory: 1G
  random-route: false
  path: build/libs/spring-music-1.0.jar
  env:
    JBP_CONFIG_DEBUG: '{enabled: true}'
    LOGGING_LEVEL_ORG_CLOUDFOUNDRY_SECURITY: "DEBUG"
    JBP_CONFIG_SPRING_AUTO_RECONFIGURATION: '{enabled: false}'
    SPRING_PROFILES_ACTIVE: http2
    JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 17.+ } }'
    JBP_CONFIG_SPRING_BOOT_EFFICIENCY: '{ aot_enabled: false, cds_enabled: true }'

#### cf push

   Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 creating container for instance 548c76cf-9a27-4f47-905b-f45c8b1bedb4
   Security group rules were updated
   Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 successfully created container for instance 548c76cf-9a27-4f47-905b-f45c8b1bedb4
   Downloading app package...
   Downloaded app package (62.3M)
   -----> Java Buildpack v4.79.0 (offline) | https://github.gwd.broadcom.net/TNZ/java-buildpack#9af10b1
   -----> Downloading Jvmkill Agent 1.17.0_RELEASE from https://java-buildpack.cloudfoundry.org/jvmkill/jammy/x86_64/jvmkill-1.17.0-RELEASE.so (found in cache)
   -----> Downloading Open Jdk JRE 17.0.14_10 from https://storage.googleapis.com/java-buildpack-dependencies/openjdk/jammy/x86_64/bellsoft-jre17.0.14%2B10-linux-amd64.tar.gz (found in cache)
   Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.2s)
   JVM DNS caching disabled in lieu of BOSH DNS caching
   -----> Downloading Open JDK Like Memory Calculator 3.13.0_RELEASE from https://java-buildpack.cloudfoundry.org/memory-calculator/jammy/x86_64/memory-calculator-3.13.0-RELEASE.tar.gz (found in cache)
   Loaded Classes: 29206, Threads: 250
   -----> Downloading Client Certificate Mapper 2.0.1 from https://storage.googleapis.com/java-buildpack-dependencies/client-certificate-mapper/client-certificate-mapper-2.0.1.jar (found in cache)
   -----> Downloading Container Security Provider 1.20.0_RELEASE from https://storage.googleapis.com/java-buildpack-dependencies/container-security-provider/container-security-provider-1.20.0-RELEASE.jar (found in cache)
   -----> Debugging enabled on port 8000
   -----> Downloading Spring Kit 0.4.0 from https://storage.googleapis.com/java-buildpack-dependencies/spring-kit-cli/spring-kit-cli-0.4.0.jar (found in cache)
   -----> Optimizing structure (0.9s)
   -----> Update classpath with root libs and / or java-cfenv (0.8s)
   -----> Performing CDS Training Run (19.0s)
...

  2025-05-26T14:08:07.71+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:08:07.709Z  INFO 36 --- [           main] o.c.samples.music.Application            : Started Application in 4.4 seconds (process running for 4.843)


