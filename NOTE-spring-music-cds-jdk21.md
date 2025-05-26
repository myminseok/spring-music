

## spring-music-cds (built jar on jdk 21, deployed on tas 6.0.13)

#### build.gradle

plugins {
    id 'org.springframework.boot' version '3.3.0'

java {
    sourceCompatibility = '21'
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

025-05-26T14:34:49.25+0900 [STG/0] OUT Downloaded build artifacts cache (132B)
   2025-05-26T14:34:51.22+0900 [STG/0] OUT Downloaded app package (62.3M)
   2025-05-26T14:34:57.36+0900 [STG/0] OUT -----> Java Buildpack v4.79.0 (offline) | https://github.gwd.broadcom.net/TNZ/java-buildpack#9af10b1
   2025-05-26T14:34:57.38+0900 [STG/0] OUT -----> Downloading Jvmkill Agent 1.17.0_RELEASE from https://java-buildpack.cloudfoundry.org/jvmkill/jammy/x86_64/jvmkill-1.17.0-RELEASE.so (found in cache)
   2025-05-26T14:34:57.38+0900 [STG/0] OUT -----> Downloading Open Jdk JRE 21.0.6_10 from https://storage.googleapis.com/java-buildpack-dependencies/openjdk/jammy/x86_64/bellsoft-jre21.0.6%2B10-linux-amd64.tar.gz (found in cache)
   2025-05-26T14:34:58.85+0900 [STG/0] OUT Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.4s)
   2025-05-26T14:34:58.85+0900 [STG/0] OUT JVM DNS caching disabled in lieu of BOSH DNS caching
   2025-05-26T14:34:58.85+0900 [STG/0] OUT -----> Downloading Open JDK Like Memory Calculator 3.13.0_RELEASE from https://java-buildpack.cloudfoundry.org/memory-calculator/jammy/x86_64/memory-calculator-3.13.0-RELEASE.tar.gz (found in cache)
   2025-05-26T14:35:00.13+0900 [STG/0] OUT Loaded Classes: 29206, Threads: 250
   2025-05-26T14:35:00.14+0900 [STG/0] OUT -----> Downloading Client Certificate Mapper 2.0.1 from https://storage.googleapis.com/java-buildpack-dependencies/client-certificate-mapper/client-certificate-mapper-2.0.1.jar (found in cache)
   2025-05-26T14:35:00.15+0900 [STG/0] OUT -----> Downloading Container Security Provider 1.20.0_RELEASE from https://storage.googleapis.com/java-buildpack-dependencies/container-security-provider/container-security-provider-1.20.0-RELEASE.jar (found in cache)
   2025-05-26T14:35:00.15+0900 [STG/0] OUT -----> Debugging enabled on port 8000
   2025-05-26T14:35:00.15+0900 [STG/0] OUT -----> Downloading Spring Kit 0.4.0 from https://storage.googleapis.com/java-buildpack-dependencies/spring-kit-cli/spring-kit-cli-0.4.0.jar (found in cache)
   2025-05-26T14:35:01.15+0900 [STG/0] OUT -----> Optimizing structure (0.9s)
   2025-05-26T14:35:01.97+0900 [STG/0] OUT -----> Update classpath with root libs and / or java-cfenv (0.8s)
   2025-05-26T14:35:19.43+0900 [STG/0] OUT -----> Performing CDS Training Run (17.4s)
   2025-05-26T14:35:40.96+0900 [STG/0] OUT Exit status 0
   2025-05-26T14:35:40.96+0900 [STG/0] OUT Uploading droplet,

   ...

   2025-05-26T14:36:07.55+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:36:07.551Z  INFO 13 --- [           main] o.c.samples.music.Application            : Started Application in 4.467 seconds (process running for 4.908)

