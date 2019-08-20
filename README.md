# revolut-money-transfer

**Problem statement**

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

**LIBRARIES USED**

1. Jetty - http Server
2. Java8
3. Jersey - JAX-RS implementation
4. JUnit5 , RestAssured , mockito - Testing unit tests, integration tests
5. log4j - logging library

**BUILD and RUN**
```
mvn clean

mvn package

java -jar target\revolut-bank-transfer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

**API DOCUMENTATION**

1. *Get all the accounts*

- Http method : GET
- Uri         : /accounts
- Content-type: application/json
- Sample Response : 200 OK
```
[
    {
        "acountNumber": "3",
        "accountBalance": 460
    },
    {
        "acountNumber": "4",
        "accountBalance": 540
    }
]
```
2. *Get particular account using id*

- Http method : GET
- Uri         : /accounts/{id}
- Content-type: application/json
- Sample Response : 200 OK , 204 N0 Content
```
Request : /accounts/4

{
    "acountNumber": "4",
    "accountBalance": 540
}
```

3. *Add an account

- Http method : POST
- Uri         : /accounts
- Content-type: application/json
- Sample Body1: 
```
{
"acountNumber":"1",
"accountBalance":"500"
}
```
- Sample Response1: 200 OK
```
{
"acountNumber":"1",
"accountBalance":"500"
}
```

- Sample response2: 400 BAD REQUEST
```
Account with id 1 already exists
```

4. *Transact money between accounts

- Http method : POST
- Uri         : /transaction/transact
- Content-type: application/json
- Sample Body1: 
```
{
"source":"1",
"target":"2",
"amount":"10.00"
}
```
- Sample Response1: 200 OK
```
[
    {
        "id": "1",
        "balance": 490
    },
    {
        "id": "2",
        "balance": 510
    }
]
```
- Sample response2: 400 BAD REQUEST
```
Invalid source/destination Account.
```
- Sample response3: 400 BAD REQUEST
```
The transferred amount should be greater than zero.
```
- Sample response3: 409 CONFLICT
```
Transaction failed due to insufficient funds.
```

