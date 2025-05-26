

## spring-music-cds (built jar on jdk 21, deployed on tas 6.0.13)

#### build.gradle

plugins {
    id 'org.springframework.boot' version '3.3.0' // for CDS
    id 'org.springframework.boot.aot' version '3.3.0' // Add this line
}

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

   2025-05-26T14:47:48.74+0900 [STG/0] OUT Downloading app package...
   2025-05-26T14:47:48.74+0900 [STG/0] OUT Downloading build artifacts cache...
   2025-05-26T14:47:48.75+0900 [STG/0] OUT Downloaded build artifacts cache (132B)
   2025-05-26T14:47:50.86+0900 [STG/0] OUT Downloaded app package (62.7M)
   2025-05-26T14:47:57.19+0900 [STG/0] OUT -----> Java Buildpack v4.79.0 (offline) | https://github.gwd.broadcom.net/TNZ/java-buildpack#9af10b1
   2025-05-26T14:47:57.21+0900 [STG/0] OUT -----> Downloading Jvmkill Agent 1.17.0_RELEASE from https://java-buildpack.cloudfoundry.org/jvmkill/jammy/x86_64/jvmkill-1.17.0-RELEASE.so (found in cache)
   2025-05-26T14:47:57.22+0900 [STG/0] OUT -----> Downloading Open Jdk JRE 21.0.6_10 from https://storage.googleapis.com/java-buildpack-dependencies/openjdk/jammy/x86_64/bellsoft-jre21.0.6%2B10-linux-amd64.tar.gz (found in cache)
   2025-05-26T14:47:58.67+0900 [STG/0] OUT Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.4s)
   2025-05-26T14:47:58.67+0900 [STG/0] OUT JVM DNS caching disabled in lieu of BOSH DNS caching
   2025-05-26T14:47:58.67+0900 [STG/0] OUT -----> Downloading Open JDK Like Memory Calculator 3.13.0_RELEASE from https://java-buildpack.cloudfoundry.org/memory-calculator/jammy/x86_64/memory-calculator-3.13.0-RELEASE.tar.gz (found in cache)
   2025-05-26T14:48:00.03+0900 [STG/0] OUT Loaded Classes: 29286, Threads: 250
   2025-05-26T14:48:00.05+0900 [STG/0] OUT -----> Downloading Client Certificate Mapper 2.0.1 from https://storage.googleapis.com/java-buildpack-dependencies/client-certificate-mapper/client-certificate-mapper-2.0.1.jar (found in cache)
   2025-05-26T14:48:00.05+0900 [STG/0] OUT -----> Downloading Container Security Provider 1.20.0_RELEASE from https://storage.googleapis.com/java-buildpack-dependencies/container-security-provider/container-security-provider-1.20.0-RELEASE.jar (found in cache)
   2025-05-26T14:48:00.06+0900 [STG/0] OUT -----> Debugging enabled on port 8000
   2025-05-26T14:48:00.06+0900 [STG/0] OUT -----> Downloading Spring Kit 0.4.0 from https://storage.googleapis.com/java-buildpack-dependencies/spring-kit-cli/spring-kit-cli-0.4.0.jar (found in cache)
   2025-05-26T14:48:01.15+0900 [STG/0] OUT -----> Optimizing structure (1.0s)
   2025-05-26T14:48:02.00+0900 [STG/0] OUT -----> Update classpath with root libs and / or java-cfenv (0.8s)
   2025-05-26T14:48:02.00+0900 [STG/0] OUT -----> Spring Boot AOT will be enabled at runtime.
   2025-05-26T14:48:18.83+0900 [STG/0] OUT -----> Performing CDS Training Run (16.8s)
   2025-05-26T14:48:40.38+0900 [STG/0] OUT Exit status 0
   2025-05-26T14:48:40.39+0900 [STG/0] OUT Uploading droplet, build artifacts cache...
   2025-05-26T14:48:40.39+0900 [STG/0] OUT Uploading droplet...
   2025-05-26T14:48:40.39+0900 [STG/0] OUT Uploading build artifacts cache...
   2025-05-26T14:48:40.43+0900 [STG/0] OUT Uploaded build artifacts cache (132B)
   2025-05-26T14:48:44.63+0900 [API/0] OUT Creating droplet for app with guid 2c31ecd4-7779-4d6d-a743-84cca5613807
   2025-05-26T14:48:50.24+0900 [STG/0] OUT Uploaded droplet (159.5M)
   2025-05-26T14:48:50.30+0900 [STG/0] OUT Uploading complete
   2025-05-26T14:48:51.02+0900 [STG/0] OUT Cell 8bab47f5-967b-414d-b599-7e16cd38614d stopping instance 2906b154-8496-4a61-b224-a5064cd7068d
   2025-05-26T14:48:51.02+0900 [STG/0] OUT Cell 8bab47f5-967b-414d-b599-7e16cd38614d destroying container for instance 2906b154-8496-4a61-b224-a5064cd7068d
   2025-05-26T14:48:51.87+0900 [STG/0] OUT Cell 8bab47f5-967b-414d-b599-7e16cd38614d successfully destroyed container for instance 2906b154-8496-4a61-b224-a5064cd7068d
   2025-05-26T14:48:51.96+0900 [API/0] OUT Updated app with guid 2c31ecd4-7779-4d6d-a743-84cca5613807 ({:droplet_guid=>"c9d7609a-4ce3-4f0c-a6cc-12455b289fbc"})
   2025-05-26T14:48:52.02+0900 [API/0] OUT Creating revision for app with guid 2c31ecd4-7779-4d6d-a743-84cca5613807
   2025-05-26T14:48:52.08+0900 [CELL/0] OUT Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 stopping instance b270af0a-e682-4b9c-50b2-8f3b
   2025-05-26T14:48:52.34+0900 [CELL/0] OUT Cell 8bab47f5-967b-414d-b599-7e16cd38614d creating container for instance a3921379-afca-41c2-7bcf-ed14
   2025-05-26T14:48:52.63+0900 [API/0] OUT Restarted app with guid 2c31ecd4-7779-4d6d-a743-84cca5613807
   2025-05-26T14:48:52.69+0900 [CELL/0] OUT Security group rules were updated
   2025-05-26T14:48:52.71+0900 [CELL/0] OUT Cell 8bab47f5-967b-414d-b599-7e16cd38614d successfully created container for instance a3921379-afca-41c2-7bcf-ed14
   2025-05-26T14:48:53.05+0900 [CELL/0] OUT Downloading droplet...
   2025-05-26T14:48:57.57+0900 [CELL/SSHD/0] OUT Exit status 0
   2025-05-26T14:48:57.60+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:48:57.597Z  INFO 52 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
   2025-05-26T14:48:57.60+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:48:57.607Z  INFO 52 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
   2025-05-26T14:48:57.61+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:48:57.613Z  INFO 52 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
   2025-05-26T14:48:57.99+0900 [APP/PROC/WEB/0] OUT Exit status 143
   2025-05-26T14:48:58.00+0900 [CELL/0] OUT Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 destroying container for instance b270af0a-e682-4b9c-50b2-8f3b
   2025-05-26T14:48:58.08+0900 [PROXY/0] OUT Exit status 137
   2025-05-26T14:48:58.69+0900 [CELL/0] OUT Cell 5efa5d03-cae0-4a91-ba4e-ed0c8f0314d0 successfully destroyed container for instance b270af0a-e682-4b9c-50b2-8f3b
   2025-05-26T14:48:58.91+0900 [CELL/0] OUT Downloaded droplet (159.5M)
   2025-05-26T14:48:58.91+0900 [CELL/0] OUT Starting health monitoring of container
   2025-05-26T14:48:59.10+0900 [APP/PROC/WEB/0] OUT Invoking pre-start scripts.
   2025-05-26T14:48:59.11+0900 [APP/PROC/WEB/0] OUT Invoking start command.
   2025-05-26T14:48:59.12+0900 [APP/PROC/WEB/0] OUT JVM Memory Configuration: -Xmx352393K -Xss1M -XX:ReservedCodeCacheSize=240M -XX:MaxDirectMemorySize=10M -XX:MaxMetaspaceSize=184182K
   2025-05-26T14:48:59.27+0900 [APP/PROC/WEB/0] OUT Listening for transport dt_socket at address: 8000


   2025-05-26T14:48:59.87+0900 [APP/PROC/WEB/0] OUT .   ____          _            __ _ _
   2025-05-26T14:48:59.87+0900 [APP/PROC/WEB/0] OUT /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
   2025-05-26T14:48:59.87+0900 [APP/PROC/WEB/0] OUT ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
   2025-05-26T14:48:59.87+0900 [APP/PROC/WEB/0] OUT \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
   2025-05-26T14:48:59.87+0900 [APP/PROC/WEB/0] OUT '  |____| .__|_| |_|_| |_\__, | / / / /
   2025-05-26T14:48:59.87+0900 [APP/PROC/WEB/0] OUT =========|_|==============|___/=/_/_/_/
   2025-05-26T14:48:59.87+0900 [APP/PROC/WEB/0] OUT :: Spring Boot ::                (v3.3.0)
   2025-05-26T14:49:00.04+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.038Z DEBUG 7 --- [kground-preinit] o.c.s.CloudFoundryContainerProvider      : KeyManager enabled
   2025-05-26T14:49:00.04+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.040Z DEBUG 7 --- [kground-preinit] o.c.s.CloudFoundryContainerProvider      : TrustManager enabled
   2025-05-26T14:49:00.04+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.040Z DEBUG 7 --- [kground-preinit] o.c.s.CloudFoundryContainerProvider      : Provider loaded
   2025-05-26T14:49:00.12+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.123Z  INFO 7 --- [           main] .m.c.SpringApplicationContextInitializer : Found services
   2025-05-26T14:49:00.12+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.126Z  INFO 7 --- [           main] o.c.samples.music.Application            : Starting AOT-processed Application v1.0 using Java 21.0.6 with PID 7 (/home/vcap/app/spring-music-cds.jar started by vcap in /home/vcap/app)
   2025-05-26T14:49:00.12+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.128Z  INFO 7 --- [           main] o.c.samples.music.Application            : The following 1 profile is active: "http2"
   2025-05-26T14:49:00.78+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.781Z  INFO 7 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
   2025-05-26T14:49:00.78+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.789Z  INFO 7 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
   2025-05-26T14:49:00.78+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.789Z  INFO 7 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.24]
   2025-05-26T14:49:00.81+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.813Z  INFO 7 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
   2025-05-26T14:49:00.81+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:00.815Z  INFO 7 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 684 ms
   2025-05-26T14:49:01.13+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:01.132Z  INFO 7 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
   2025-05-26T14:49:01.36+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:01.364Z  INFO 7 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:dfcf16cf-2f78-4401-b285-59b2f49beb6c user=SA
   2025-05-26T14:49:01.36+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:01.366Z  INFO 7 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
   2025-05-26T14:49:01.41+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:01.412Z  INFO 7 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
   2025-05-26T14:49:01.43+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:01.437Z  INFO 7 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.5.2.Final
   2025-05-26T14:49:01.45+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:01.451Z  INFO 7 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
   2025-05-26T14:49:01.79+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:01.795Z  INFO 7 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
   2025-05-26T14:49:02.18+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:02.180Z  INFO 7 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
   2025-05-26T14:49:02.19+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:02.192Z  INFO 7 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
   2025-05-26T14:49:02.51+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:02.512Z  WARN 7 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
   2025-05-26T14:49:02.55+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:02.558Z  INFO 7 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
   2025-05-26T14:49:03.30+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:03.304Z  INFO 7 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 14 endpoints beneath base path '/actuator'
   2025-05-26T14:49:03.38+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:03.381Z  INFO 7 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
   2025-05-26T14:49:03.38+0900 [APP/PROC/WEB/0] OUT 2025-05-26T05:49:03.387Z  INFO 7 --- [           main] o.c.samples.music.Application            : Started Application in 3.79 seconds (process running for 4.258)
   2025-05-26T14:49:05.21+0900 [CELL/0] OUT Container became healthy