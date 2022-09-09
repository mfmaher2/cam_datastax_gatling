Gatling Simulations using Gatling plugin for Gradle  
=============================================
Simulations use gatling gradle plugin "com.github.lkishalmi.gatling" v0.7.4. 
Any higher versions won't work since dse gatling plugin only supports gatling.core 2.3

Java: 1.8
Scala: 2.12.8
Gatling: 2.3
Gradle: 6.6.1   Gradle build might fail if using newer versions


# Using the Examples

## Building
`gradle clean build`

## Building a fat/uber jar
`gradle clean fatJar`  This creates, cam_datastax_gatling-all.jar in build/libs folder.


## Running Simulations
#gradle run after build
`gradle gatlingRun-sims.examples.cql.accounts.ReadWriteAccountSimulation`

#using fat/uber jar
(cd to build/libs directory or where ever the uber jar(cam_datastax_gatling-all.jar) is located)
`Java -jar  cam_datastax_gatling-all.jar listSims`   list all simulations
`Java -jar  cam_datastax_gatling-all.jar run sims.examples.cql.accounts.ReadWriteAccountSimulation`

#explicitly use starter app to run gatling sims
`Java -cp  cam_datastax_gatling-all.jar  com.datastax.gatling.stress.Starter listSims`
`Java -cp  cam_datastax_gatling-all.jar  com.datastax.gatling.stress.Starter run sims.examples.cql.accounts.ReadWriteAccountSimulation`

#create an executable and run simulations
`./create_sim_exec.sh`    Creates an executable for the jar in build/libs
use the executable to list and run simulations
`./cam_datastax_gatling-all listSims`  List Simulations
`./cam_datastax_gatling-all run sims.examples.cql.accounts.ReadWriteAccountSimulation`  Run a simulation

## Configuration
Project configs can be found in the `src/main/resources` the `application.conf` is the file to set the Simulation and Cassandra settings.

#Overriding configuration during runtime
#Overriding the whole application.conf that's in the jar with a conf file, located anywhere in the file system.
`Java -Dconfig.file=/"path of new config file/config_filename.conf  -jar   cam_datastax_gatling-all.jar run "simulation class name with package prefix"`
example: `Java -Dconfig.file=~/config/newconfig.conf  -jar   cam_datastax_gatling-all.jar run sims.examples.cql.accounts.ReadWriteAccountSimulation`

#override UserCount and usersRampTime
`Java -Dsimulations.cam.writeAccount.usersConstantCnt=23  -Dsimulations.cam.defaults.usersRampTime=40s  -jar   cam_datastax_gatling-all.jar run sims.examples.cql.accounts.ReadWriteAccountSimulation`

#override cassandra host(also dc) and simulation properties
#override just the cassandra host, but use default simulation settings configured for the app
`Java -Ddatastax-java-driver.basic.contact-points.0="127.0.0.1:9042"  -Ddatastax-java-driver.basic.load-balancing-policy.local-datacenter=Cassandra  -jar   cam_datastax_gatling-all.jar run sims.examples.cql.accounts.ReadWriteAccountSimulation`


#override contact-point(host), setting datacenter is required when setting contact-point, 
#and  override default simulation setting for UserCount and usersRampTime
`Java -Ddatastax-java-driver.basic.contact-points.0="127.0.0.1:9042"  -Ddatastax-java-driver.basic.load-balancing-policy.local-datacenter=Cassandra -Dsimulations.cam.writeAccount.usersConstantCnt=23  -Dsimulations.cam.defaults.usersRampTime=20s   -jar   cam_datastax_gatling-all.jar run sims.examples.cql.accounts.ReadWriteAccountSimulation`  