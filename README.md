# revolut-money-transfer

Problem statement

Design and implement a RESTful API (including data model and the backing implementation) for
money transfers between accounts.

Explicit requirements:

1. You can use Java or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 and keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require a
pre-installed container/server).
7. Demonstrate with tests that the API works as expected.

Implicit requirements:
1. The code produced by you is expected to be of high quality.
2. There are no detailed requirements, use common sense.

SOLUTION

1. Libraries used:

Jetty - http Server
Java8
Jersey - JAX-RS implementation
JUnit5 , RestAssured , mockito - Testing unit tests, integration tests
log4j - logging library

2. BUILD and RUN

mvn clean
mvn package
java -jar target\revolut-bank-transfer-1.0-SNAPSHOT-jar-with-dependencies.jar

3. API DOCUMENTATION


