Gatling Simulations using Gatling plugin for Gradle  
=============================================
Simulations use gatling gradle plugin "com.github.lkishalmi.gatling" v0.7.4. 
Any higher versions won't work since dse gatling plugin only supports gatling.core 2.3

Java: 1.8
Scala: 2.12.8
Gatling: 2.3

# Using the Examples

## Building
`gradle clean build`

## Building a fat/uber jar
`gradle clean fatJar`  This creates, cam_datastax_gatling-all.jar in build/libs folder.


## Running Simulations
#gradle run
`gradle gatlingRun-sims.examples.cql.accounts.ReadWriteAccountSimulation`

#using fat/uber jar
(cd to build/libs directory or where ever the uber jar(cam_datastax_gatling-all.jar) is located)
`Java -jar  cam_datastax_gatling-all.jar listSims`   list all simulations
`Java -jar  cam_datastax_gatling-all.jar run sims.examples.cql.accounts.ReadWriteAccountSimulation`

#explicitly use starter app to run gatling sims
`Java -cp  cam_datastax_gatling-all.jar  com.datastax.gatling.stress.Starter listSims`
`Java -cp  cam_datastax_gatling-all.jar  com.datastax.gatling.stress.Starter runsims.examples.cql.accounts.ReadWriteAccountSimulation`

#create an executable and run simulations
`./create_sim_exec.sh`    Creates an executable for the jar in build/libs
use the executable to list and run simulations
`./cam_datastax_gatling-all listSims`  List Simulations
`./cam_datastax_gatling-all run sims.examples.cql.accounts.ReadWriteAccountSimulation`  Run a simulation

## Configuration
Project configs can be found in the `src/main/resources` the `application.conf` is the file to set the Simulation and Cassandra settings.

