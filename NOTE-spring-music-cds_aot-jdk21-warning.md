

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

   2025-05-26T14:39:39.86+0900 [STG/0] OUT Downloading app package...
   2025-05-26T14:39:39.86+0900 [STG/0] OUT Downloading build artifacts cache...
   2025-05-26T14:39:39.91+0900 [STG/0] OUT Downloaded build artifacts cache (132B)
   2025-05-26T14:39:41.86+0900 [STG/0] OUT Downloaded app package (62.3M)
   2025-05-26T14:39:47.94+0900 [STG/0] OUT -----> Java Buildpack v4.79.0 (offline) | https://github.gwd.broadcom.net/TNZ/java-buildpack#9af10b1
   2025-05-26T14:39:47.96+0900 [STG/0] OUT -----> Downloading Jvmkill Agent 1.17.0_RELEASE from https://java-buildpack.cloudfoundry.org/jvmkill/jammy/x86_64/jvmkill-1.17.0-RELEASE.so (found in cache)
   2025-05-26T14:39:47.96+0900 [STG/0] OUT -----> Downloading Open Jdk JRE 21.0.6_10 from https://storage.googleapis.com/java-buildpack-dependencies/openjdk/jammy/x86_64/bellsoft-jre21.0.6%2B10-linux-amd64.tar.gz (found in cache)
   2025-05-26T14:39:49.42+0900 [STG/0] OUT Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.4s)
   2025-05-26T14:39:49.42+0900 [STG/0] OUT JVM DNS caching disabled in lieu of BOSH DNS caching
   2025-05-26T14:39:49.42+0900 [STG/0] OUT -----> Downloading Open JDK Like Memory Calculator 3.13.0_RELEASE from https://java-buildpack.cloudfoundry.org/memory-calculator/jammy/x86_64/memory-calculator-3.13.0-RELEASE.tar.gz (found in cache)
   2025-05-26T14:39:50.70+0900 [STG/0] OUT Loaded Classes: 29206, Threads: 250
   2025-05-26T14:39:50.72+0900 [STG/0] OUT -----> Downloading Client Certificate Mapper 2.0.1 from https://storage.googleapis.com/java-buildpack-dependencies/client-certificate-mapper/client-certificate-mapper-2.0.1.jar (found in cache)
   2025-05-26T14:39:50.72+0900 [STG/0] OUT -----> Downloading Container Security Provider 1.20.0_RELEASE from https://storage.googleapis.com/java-buildpack-dependencies/container-security-provider/container-security-provider-1.20.0-RELEASE.jar (found in cache)
   2025-05-26T14:39:50.72+0900 [STG/0] OUT -----> Debugging enabled on port 8000
   2025-05-26T14:39:50.72+0900 [STG/0] OUT -----> Downloading Spring Kit 0.4.0 from https://storage.googleapis.com/java-buildpack-dependencies/spring-kit-cli/spring-kit-cli-0.4.0.jar (found in cache)
   2025-05-26T14:39:51.69+0900 [STG/0] OUT -----> Optimizing structure (0.9s)
   2025-05-26T14:39:52.58+0900 [STG/0] OUT -----> Update classpath with root libs and / or java-cfenv (0.8s)
   2025-05-26T14:39:52.58+0900 [STG/0] ERR [SpringBootEfficiency]           INFO  User has enabled AOT at runtime, using aot_enabled=true, but the Spring Boot jar was not built with AOT optimization. The Java Buildpack will run the AOT optimization.
   2025-05-26T14:40:02.72+0900 [STG/0] OUT -----> Performing AOT Optimization (10.1s)
   2025-05-26T14:40:02.72+0900 [STG/0] OUT -----> Spring Boot AOT will be enabled at runtime.
   2025-05-26T14:40:19.34+0900 [STG/0] OUT -----> Performing CDS Training Run (16.6s)
   2025-05-26T14:40:40.84+0900 [STG/0] OUT Exit status 0
   2025-05-26T14:40:40.84+0900 [STG/0] OUT Uploading droplet, build artifacts cache...
   2025-05-26T14:40:40.84+0900 [STG/0] OUT Uploading build artifacts cache...
   2025-05-26T14:40:40.84+0900 [STG/0] OUT Uploading droplet...
   2025-05-26T14:40:40.89+0900 [STG/0] OUT Uploaded build artifacts cache (132B)
   2025-05-26T14:40:44.91+0900 [API/0] OUT Creating droplet for app with guid 2c31ecd4-7779-4d6d-a743-84cca5613807
   2025-05-26T14:40:49.50+0900 [STG/0] OUT Uploaded droplet (159.5M)
   2025-05-26T14:40:49.56+0900 [STG/0] OUT Uploading complete
   2025-05-26T14:40:50.24+0900 [STG/0] OUT Cell 8bab47f5-967b-414d-b599-7e16cd38614d stopping instance 2e27da67-6526-45b9-b617-99decdf55306
   2025-05-26T14:40:50.24+0900 [STG/0] OUT Cell 8bab47f5-967b-414d-b599-7e16cd38614d destroying container for instance 2e27da67-6526-45b9-b617-99decdf55306
   2025-05-26T14:40:50.61+0900 [API/0] OUT Updated app with guid 2c31ecd4-7779-4d6d-a743-84cca5613807 ({:droplet_guid=>"a7568bbb-816a-4519-922b-4a7361fadc56"})
   2025-05-26T14:40:50.67+0900 [API/0] OUT Creating revision for app with guid 2c31ecd4-7779-4d6d-a743-84cca5613807
   2025-05-26T14:40:50.72+0900 [CELL/0] OUT Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 stopping instance 061c086e-af7b-4be9-4392-9da5
   2025-05-26T14:40:51.14+0900 [CELL/0] OUT Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 creating container for instance b270af0a-e682-4b9c-50b2-8f3b
   2025-05-26T14:40:51.21+0900 [STG/0] OUT Cell 8bab47f5-967b-414d-b599-7e16cd38614d successfully destroyed container for instance 2e27da67-6526-45b9-b617-99decdf55306
   2025-05-26T14:40:51.43+0900 [API/0] OUT Restarted app with guid 2c31ecd4-7779-4d6d-a743-84cca5613807
   2025-05-26T14:40:51.49+0900 [CELL/0] OUT Security group rules were updated
   2025-05-26T14:40:51.52+0900 [CELL/0] OUT Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 successfully created container for instance b270af0a-e682-4b9c-50b2-8f3b
   2025-05-26T14:40:52.09+0900 [CELL/0] OUT Downloading droplet...
   2025-05-26T14:40:56.00+0900 [CELL/SSHD/0] OUT Exit status 0
   2025-05-26T14:40:56.02+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:56.023Z  INFO 13 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
   2025-05-26T14:40:56.03+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:56.033Z  INFO 13 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
   2025-05-26T14:40:56.04+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:56.045Z  INFO 13 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
   2025-05-26T14:40:56.42+0900 [APP/PROC/WEB/0] OUT Exit status 143
   2025-05-26T14:40:56.43+0900 [CELL/0] OUT Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 destroying container for instance 061c086e-af7b-4be9-4392-9da5
   2025-05-26T14:40:56.52+0900 [PROXY/0] OUT Exit status 137
   2025-05-26T14:40:57.18+0900 [CELL/0] OUT Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 successfully destroyed container for instance 061c086e-af7b-4be9-4392-9da5
   2025-05-26T14:40:57.90+0900 [CELL/0] OUT Downloaded droplet (159.5M)
   2025-05-26T14:40:57.90+0900 [CELL/0] OUT Starting health monitoring of container
   2025-05-26T14:40:58.17+0900 [APP/PROC/WEB/0] OUT Invoking pre-start scripts.
   2025-05-26T14:40:58.18+0900 [APP/PROC/WEB/0] OUT Invoking start command.
   2025-05-26T14:40:58.19+0900 [APP/PROC/WEB/0] OUT JVM Memory Configuration: -Xmx352393K -Xss1M -XX:ReservedCodeCacheSize=240M -XX:MaxDirectMemorySize=10M -XX:MaxMetaspaceSize=184182K
   2025-05-26T14:40:58.31+0900 [APP/PROC/WEB/0] OUT Listening for transport dt_socket at address: 8000

   ...
   2025-05-26T14:40:58.89+0900 [APP/PROC/WEB/0] OUT .   ____          _            __ _ _
   2025-05-26T14:40:58.89+0900 [APP/PROC/WEB/0] OUT /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
   2025-05-26T14:40:58.89+0900 [APP/PROC/WEB/0] OUT ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
   2025-05-26T14:40:58.89+0900 [APP/PROC/WEB/0] OUT \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
   2025-05-26T14:40:58.89+0900 [APP/PROC/WEB/0] OUT '  |____| .__|_| |_|_| |_\__, | / / / /
   2025-05-26T14:40:58.89+0900 [APP/PROC/WEB/0] OUT =========|_|==============|___/=/_/_/_/
   2025-05-26T14:40:58.89+0900 [APP/PROC/WEB/0] OUT :: Spring Boot ::                (v3.3.0)
   2025-05-26T14:40:59.12+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.122Z  INFO 52 --- [           main] .m.c.SpringApplicationContextInitializer : Found services
   2025-05-26T14:40:59.12+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.126Z  INFO 52 --- [           main] o.c.samples.music.Application            : Starting AOT-processed Application v1.0 using Java 21.0.6 with PID 52 (/home/vcap/app/spring-music-cds.jar started by vcap in /home/vcap/app)
   2025-05-26T14:40:59.12+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.127Z  INFO 52 --- [           main] o.c.samples.music.Application            : The following 1 profile is active: "http2"
   2025-05-26T14:40:59.22+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.226Z DEBUG 52 --- [kground-preinit] o.c.s.CloudFoundryContainerProvider      : KeyManager enabled
   2025-05-26T14:40:59.22+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.226Z DEBUG 52 --- [kground-preinit] o.c.s.CloudFoundryContainerProvider      : TrustManager enabled
   2025-05-26T14:40:59.22+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.227Z DEBUG 52 --- [kground-preinit] o.c.s.CloudFoundryContainerProvider      : Provider loaded
   2025-05-26T14:40:59.81+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.815Z  INFO 52 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
   2025-05-26T14:40:59.82+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.821Z  INFO 52 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
   2025-05-26T14:40:59.82+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.822Z  INFO 52 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.24]
   2025-05-26T14:40:59.84+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.846Z  INFO 52 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
   2025-05-26T14:40:59.84+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:40:59.848Z  INFO 52 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 717 ms
   2025-05-26T14:41:00.15+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:00.158Z  INFO 52 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
   2025-05-26T14:41:00.39+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:00.396Z  INFO 52 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:2d9f01ad-0d78-4722-bf78-d4b9f432755f user=SA
   2025-05-26T14:41:00.39+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:00.397Z  INFO 52 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
   2025-05-26T14:41:00.42+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:00.427Z  INFO 52 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
   2025-05-26T14:41:00.46+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:00.463Z  INFO 52 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.5.2.Final
   2025-05-26T14:41:00.47+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:00.475Z  INFO 52 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
   2025-05-26T14:41:00.82+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:00.819Z  INFO 52 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
   2025-05-26T14:41:01.20+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:01.209Z  INFO 52 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
   2025-05-26T14:41:01.22+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:01.221Z  INFO 52 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
   2025-05-26T14:41:01.55+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:01.557Z  WARN 52 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
   2025-05-26T14:41:01.60+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:01.605Z  INFO 52 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
   2025-05-26T14:41:02.40+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:02.402Z  INFO 52 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 14 endpoints beneath base path '/actuator'
   2025-05-26T14:41:02.47+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:02.478Z  INFO 52 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
   2025-05-26T14:41:02.48+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:41:02.483Z  INFO 52 --- [           main] o.c.samples.music.Application            : Started Application in 3.871 seconds (process running for 4.281)
   2025-05-26T14:41:04.22+0900 [CELL/0] OUT Container became healthy