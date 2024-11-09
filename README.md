Hazelcast demo application
==========================

This is demo appliction for "Java Profilers" training course.

Demo include 3+1 Hazelcast cluster and perform simple map/reduce operation.

Run demo
--------

 * `mvn -P run test` to start cluster
 * `mvn -P run_n_load test` to start cluster and load script
 * `mvn -P stop test` stop demo processes
 * `mvn clean` would also clean demo processes

Run demo in docker
------------------

 * `mvn -P docker_run test` to start cluster
 * `mvn -P docker_run_n_load test` to start cluster and load script
 * `mvn -P stop test` stop demo processes
 * `mvn clean` would also clean demo processes (though it will not remove containers)

Performance logs are under `var/loadgen`