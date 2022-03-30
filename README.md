Run `test.sh` to run a 20 thread parallel build repeatedly

The build contains 10 kotlin modules and 10 non-kotlin modules to try and
provoke a surefire run on a java project to start before the first kotlin
module run.

The desired order of events for the failure is:

1. a surefire test run clones System.properties
2. a kotlin compile execution sets the idea system properties
3. the surefire test comples and restores the old System.properties
4. kotlin compile plugin attempts to use the properties and fails
