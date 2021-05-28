# DICE Application

### Assignment 1
* Created '/dice/run-simulation' REST (POST as it is like some RPC, not accessing any specific resource, just triggering a simulation and receiving the results) endpoint
  * Introduced diceNumber, diceSides, rollsNumber request parameters, which are not mandatory. If they are not provided, defaults are used: 3 dice, 6 sides, 100 runs
  * Validation of request parameters is done using javax.validation
  * A response does not contain sums, which were not rolled, as they are obvious (+ shorter result)
* Added dependency to springfox-swagger-ui so once the application is running, it is possible to access http://localhost:8080/swagger-ui.html and simply call the exposed endpoints

### Assignment 2
* Defined DB schema
  * Two tables has been created, first is holding details about a simulation (diceNumber, diceSides, runs) and the second one is holding the simulation results (roll sum - counts)
  * The relationship between the tables is One to Many, so One Simulation has Many Results
  * Sums, which were not rolled in a simulation, are not stored in DB, as they are obvious (+ less records)
* For a purpose of this Assignment, H2 File DB has been chosen as DB (test is using inMemory H2 DB)
* Storing of DB records is performed by utilizing of Hibernate framework, record IDs are generated
* Created '/dice/stats' REST (POST) endpoint to fetch simulation stats
  * No input parameters or body
* In order to build dice number - sides with rollSum - count distribution, one native SQL query has been built to fetch data
  * As complete data is fetched in one sql query, so we do not need to care about transactions here
  * If DB contained huge amount of data it would probably make sense to:
    * Run multiple smaller SQL queries, like collecting results for each dice number - sides pair in separate query
    * All the queries would need to be run in the same transaction with proper isolation level, so consistent result would be fetched...
  * Potentially, in case of huge amount of DB data and depending on an used DB engine, additional indexes may be added to increase fetch speed (in case DB engine is able to utilize them in group by)

### Potential improvements
* Utilizing of some (Out of the box) mapping to DTO after the data is fetched using native SQL query
  * I believe it will not improve performance, only the code will looks more compact
* Specifying some max limits for request parameters in order to avoid application and DB overload
* Write tests to validate REST endpoints, parameters limits, status codes, ... 