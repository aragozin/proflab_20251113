Hazelcast demo application
==========================

This is demo application for "Java Profilers" training course.

Demo includes 3+1 node Hazelcast cluster and perform simple map/reduce operation.

Run demo
--------

 * `mvn -P run test` to start cluster
 * `mvn -P run_n_load test` to start cluster and load script
 * `mvn -P stop test` stop demo processes
 * `mvn clean` would also clean demo processes

Run demo in docker
------------------

Build image with `mvn clean package -P build_image`.

 * `mvn -P docker_run test` to start cluster
 * `mvn -P docker_run_n_load test` to start cluster and load script
 * `mvn -P stop test` stop demo processes
 * `mvn clean` would also clean demo processes (though it will not remove containers)

Performance logs are under `var/loadgen`