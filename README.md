# Spring Batch Hello World

## Goal

Getting familiar with Spring batch, especially with the annotation based configuration. The goal is not to do a lot, but to do it right.

## Hello World Project

This is initially a copy of the Spring Bath sample project with slight changes:
 <https://spring.io/guides/gs/batch-processing/>  . (The sample
is missing schema.sql, which creates the table thru Spring-Boot magic,
but it was a good exercise to figure that out)

What it does is really simple:

First read a CSV file (people.csv) with John and Mary:

    John,Smith
    Marry,Smith

Second convert first and last names to upper case

Third insert it into the people table in a HSQL database

    CREATE TABLE people  (
        person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
        first_name VARCHAR(20),
        last_name VARCHAR(20)
    );

Not rocket science or very useful, but it is a helloworld after all.

Spring-Boot cuts down on maven configuration considerably, by providing opinionated defaults,
which can all be overriden. In contrast to Spring-Roo, which generates code and relies heavily
on aspects, Spring-Boot generates
no code, and it can be easily removed, once the project gets more mature, and and we made
all out own changes for
every dependency, but we are not burdned by that before we can even get started.

There is no Spring-Boot archetype, but if you are lazy like me, go to
<http://start.spring.io/> and use Spring Initializr to generated your
skeleton which compiles.

When you run it using

    java -jar target/springBatchHelloWorld-0.0.1-SNAPSHOT.jar

you will see the following output:

    $ java -jar target/springBatchHelloWorld-0.0.1-SNAPSHOT.jar

      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::        (v1.3.2.RELEASE)

    2016-02-10 14:13:52.323  INFO 22456 --- [           main] t.s.s.h.SpringBatchHelloWorldApplication : Starting SpringBatchHelloWorldApplication v0.0.1-SNAPSHOT on MUSTS186040-922 with PID 22456 (/Users/ts186040/ThinkBig/projects/hgst/sandbox/springBatchHelloWorld/target/springBatchHelloWorld-0.0.1-SNAPSHOT.jar started by ts186040 in /Users/ts186040/ThinkBig/projects/hgst/sandbox/springBatchHelloWorld)
    2016-02-10 14:13:52.327  INFO 22456 --- [           main] t.s.s.h.SpringBatchHelloWorldApplication : No active profile set, falling back to default profiles: default
    2016-02-10 14:13:52.386  INFO 22456 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@5862b6fc: startup date [Wed Feb 10 14:13:52 PST 2016]; root of context hierarchy
    2016-02-10 14:13:52.932  WARN 22456 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    2016-02-10 14:13:52.944  WARN 22456 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    2016-02-10 14:13:53.101  INFO 22456 --- [           main] o.s.j.d.e.EmbeddedDatabaseFactory        : Starting embedded database: url='jdbc:hsqldb:mem:testdb', username='sa'
    2016-02-10 14:13:53.403  INFO 22456 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from URL [jar:file:/Users/ts186040/ThinkBig/projects/hgst/sandbox/springBatchHelloWorld/target/springBatchHelloWorld-0.0.1-SNAPSHOT.jar!/schema.sql]
    2016-02-10 14:13:53.405  INFO 22456 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from URL [jar:file:/Users/ts186040/ThinkBig/projects/hgst/sandbox/springBatchHelloWorld/target/springBatchHelloWorld-0.0.1-SNAPSHOT.jar!/schema.sql] in 2 ms.
    2016-02-10 14:13:53.719  INFO 22456 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from class path resource [org/springframework/batch/core/schema-hsqldb.sql]
    2016-02-10 14:13:53.726  INFO 22456 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from class path resource [org/springframework/batch/core/schema-hsqldb.sql] in 6 ms.
    2016-02-10 14:13:53.814  INFO 22456 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
    2016-02-10 14:13:53.823  INFO 22456 --- [           main] o.s.b.a.b.JobLauncherCommandLineRunner   : Running default command line with: []
    2016-02-10 14:13:53.831  INFO 22456 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: HSQL
    2016-02-10 14:13:53.940  INFO 22456 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
    2016-02-10 14:13:53.996  INFO 22456 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=importUserJob]] launched with the following parameters: [{run.id=1}]
    2016-02-10 14:13:54.003  INFO 22456 --- [           main] b.j.CompletionReportJobExecutionListener :  jobExecution:
    JobExecution: id=0, version=1, startTime=Wed Feb 10 14:13:53 PST 2016, endTime=null, lastUpdated=Wed Feb 10 14:13:53 PST 2016, status=STARTED, exitStatus=exitCode=UNKNOWN;exitDescription=, job=[JobInstance: id=0, version=0, Job=[importUserJob]], jobParameters=[{run.id=1}]
    2016-02-10 14:13:54.076  INFO 22456 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
    2016-02-10 14:13:54.115  INFO 22456 --- [           main] b.j.CompletionReportJobExecutionListener : after jobExecution:
    JobExecution: id=0, version=1, startTime=Wed Feb 10 14:13:53 PST 2016, endTime=Wed Feb 10 14:13:54 PST 2016, lastUpdated=Wed Feb 10 14:13:53 PST 2016, status=COMPLETED, exitStatus=exitCode=COMPLETED;exitDescription=, job=[JobInstance: id=0, version=0, Job=[importUserJob]], jobParameters=[{run.id=1}]
    2016-02-10 14:13:54.117  INFO 22456 --- [           main] b.j.CompletionReportJobExecutionListener : found Person{firstName='JOHN', lastName='SMITH'} in database
    2016-02-10 14:13:54.117  INFO 22456 --- [           main] b.j.CompletionReportJobExecutionListener : found Person{firstName='MARRY', lastName='SMITH'} in database
    2016-02-10 14:13:54.120  INFO 22456 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=importUserJob]] completed with the following parameters: [{run.id=1}] and the following status: [COMPLETED]
    2016-02-10 14:13:54.121  INFO 22456 --- [           main] t.s.s.h.SpringBatchHelloWorldApplication : Started SpringBatchHelloWorldApplication in 2.015 seconds (JVM running for 2.347)
    2016-02-10 14:13:54.122  INFO 22456 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@5862b6fc: startup date [Wed Feb 10 14:13:52 PST 2016]; root of context hierarchy
    2016-02-10 14:13:54.123  INFO 22456 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
    2016-02-10 14:13:54.124  INFO 22456 --- [       Thread-2] o.s.j.d.e.EmbeddedDatabaseFactory        : Shutting down embedded database: url='jdbc:hsqldb:mem:testdb'



Among all the other output you will find the names in Uppercase:

    found Person{firstName='JOHN', lastName='SMITH'} in database
    found Person{firstName='MARRY', lastName='SMITH'} in database


