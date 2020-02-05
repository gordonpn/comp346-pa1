```
Concordia University
comp 346 - Winter 2020
Operating Systems
Programming assignment 1
```
________________________________________________________________________
Deadline: By 11:59pm, Friday January 31 2020

Late Submission: No late submission

Teams: The assignment can be done individually or in teams of 2 (from the
same lecture section). Submit only one assignment per team.

Purpose: The purpose of this assignment is to apply in practice the multi-main.java.threading features of the Java programming language.
________________________________________________________________________

- Problem specification.

```
In a client-server system, the client application sends requests to a server application
through a network connection. In such system, the user interface is implemented in the
client and the database is stored in the server.
You are required to implement a client-server application to process banking
transactions such as withdrawals and deposits. In modern banking systems, a customer
accesses a bank account using an access card at an ATM, at the counter or on the web.
```
- Implementation.

▪ main.java.threading.Network class.
```
The main.java.threading.Network class provides the infrastructure to allow the client and the
server to process the transactions. The client and the server need to be connected
(using connect()) to the network prior to an exchange. The main.java.threading.Network class also
implements an input buffer (inComingPacket[]) and an output buffer
(outGoingPacket[]) to respectively receive transactions from the client and to
return updated transactions to the client. The capacity of these buffers are 10
elements, so the network indicates whether they are full or empty.
```
▪ main.java.threading.Client class.
```
The main.java.threading.Client class reads all the transactions from a file (transaction.txt) and
saves them in an array (transaction[]). A transaction is implemented by the
main.java.threading.Transaction.class.
Using the send() method of main.java.threading.Network class the client transfers the transactions
to the network input buffer and it yields the cpu in case the network input buffer
is full.
Also, using the receive() method of main.java.threading.Network class the client retrieves the
updated transactions from the network output buffer and yields the cpu in case
the buffer is empty. Each updated transaction received is displayed immediately
on the console.
```
▪ main.java.threading.Server class.
```
The main.java.threading.Server class reads all the accounts from a file (account.txt) and saves
them in an array (account[]). An account is implemented by the Accounts class.
Using the transferrIn() method of main.java.threading.Network class the server retrieves the
transactions from the network input buffer and perform the operations
(withdraw, deposit, query) on the specific accounts. It yields the cpu in case the
buffer is empty.
Each updated transaction is transmitted to the network output buffer using the
transferOut( ) method of main.java.threading.Network class and the server yields the cpu in case the
buffer is full.
```
- Problems.

```
▪ You need to complete the Java program that is provided by implementing 4
threads so that the client, the server and the network all run concurrently. The
client has 2 threads, one for sending the transactions and another for receiving
the completed transactions.
In case the input and output network buffers are full or empty each client or
server thread must yield the cpu using the Java method Thread.yield(). The
network thread executes an infinite loop that ends when both client and server
threads have disconnected. In case the client or server threads are still connected
the network thread must continuously yield the cpu.
```

```
▪ You must record the running times of both client threads and the server thread
using the Java method System.currentTimeMillis().
```
```
▪ You need to provide output test cases with the appropriate running times for the
client and the server threads. Perform 3 different runs of the program and
explain why there is a difference in the running times.
```
- Sample output test cases.

```
Activating the network ...
Initializing the server ...
Activating network components for server...
Inializing the Accounts database ...
Connecting server to network ...
Initializing client sending application ...
Activating network components for client...
Initializing the transactions ...
Connecting client to network ...
Initializing client receiving application ...
Account number 31718 Account Balance 100.0 Message done
Account number 11528 Account Balance 500.0 Message done
Account number 16783 Account Balance 8000.0 Message done
Account number 10538 Account Balance 140000.0 Message done
Account number 21O15 Account Balance 100.0 Message done
Account number 61326 Account Balance 1750.0 Message done
Account number 82465 Account Balance 20000.0 Message done
Account number 31816 Account Balance 20500.0 Message done
Account number 73745 Account Balance 100.0 Message done
Account number 81514 Account Balance -200.0 Message done
Account number 71854 Account Balance 10000.0 Message done
Account number 92012 Account Balance 750.0 Message done
Account number 52853 Account Balance 1000.0 Message done
Account number 41714 Account Balance 1100.0 Message done
Account number 58342 Account Balance 0.0 Message done
Account number 62080 Account Balance 100.0 Message done
Account number 21638 Account Balance -1000.0 Message done
Account number 52726 Account Balance 33000.0 Message done
Account number 91316 Account Balance 65000.0 Message done
Account number 60520 Account Balance 1100.0 Message done
Account number 23033 Account Balance 30000.0 Message done
Account number 41911 Account Balance 1500.0 Message done
Account number 39331 Account Balance 0.0 Message done
Account number 91312 Account Balance 1000.0 Message done
Account number 15307 Account Balance 300.0 Message done
Account number 51728 Account Balance -800.0 Message done
Account number 43902 Account Balance 1000.0 Message done
Account number 52521 Account Balance 7000.0 Message done
Account number 31215 Account Balance 2000.0 Message done
Account number 22310 Account Balance 100.0 Message done
Account number 26734 Account Balance 600.0 Message done
Account number 41024 Account Balance 1500.0 Message done
Account number 38421 Account Balance 5800.0 Message done
Account number 70321 Account Balance 0.0 Message done
Account number 12421 Account Balance 1000.0 Message done
Account number 82411 Account Balance -400.0 Message done
Account number 21117 Account Balance 100.0 Message done
Account number 00925 Account Balance 1000.0 Message done
Account number 31820 Account Balance 1000.0 Message done
Account number 91715 Account Balance 100.0 Message done
Account number 31012 Account Balance -1000.0 Message done
Account number 32613 Account Balance 3000.0 Message done
Account number 13133 Account Balance -100.0 Message done
Account number 43018 Account Balance 500.0 Message done
Account number 91412 Account Balance 400.0 Message done
Account number 11314 Account Balance 1000.0 Message done
Account number 00812 Account Balance 1620.0 Message done
Account number 61527 Account Balance 0.0 Message done
Account number 43817 Account Balance 100.0 Message done
Account number 92018 Account Balance -250.0 Message done
Account number 42850 Account Balance 1000.0 Message done
Account number 92716 Account Balance 1000.0 Message done
Account number 01113 Account Balance 300.0 Message done
Account number 60716 Account Balance 1000.0 Message done
Terminating client sending thread - Running time 20 milliseconds
Account number 01823 Account Balance 860.0 Message done
Account number 13331 Account Balance 1500.0 Message done
Account number 81368 Account Balance 350.0 Message done
Account number 91214 Account Balance 1600.0 Message done
Account number 91245 Account Balance 1200.0 Message done
Account number 42018 Account Balance -20.0 Message done
Account number 62023 Account Balance 202200.0 Message done
Account number 01314 Account Balance 1.05E7 Message done
Account number 20922 Account Balance 240.0 Message done
Account number 21219 Account Balance 535000.0 Message done
Account number 83530 Account Balance 980000.0 Message done
Account number 83830 Account Balance 125.0 Message done
Account number 72820 Account Balance 100000.0 Message done
Account number 92318 Account Balance 2400.0 Message done
Account number 42520 Account Balance 1000.0 Message done
Account number 61612 Account Balance 25.0 Message done
Account number 12535 Account Balance 750.0 Message done
Terminating client receiving thread - Running time 24 milliseconds
Terminating server thread - Running time 63 milliseconds
Terminating network thread - Client disconnected Server disconnected
```
- Evaluation.

| Criteria                                                | Marks |
|---------------------------------------------------------|-------|
| Implementation of the 4 threads                         | 35%   |
| Implementation of the main class                        | 15%   |
| Answer to a question during the demo                    | 10%   |
| Implementation of the yield() method                    | 10%   |
| Implementation of the measurements of the running times | 10%   |
| Output test cases including running times               | 20%   |

- Required documents.

```
▪ Source codes in Java.
▪ Output test cases.
▪ I have included DEBUG flags in the source code in order to help you trace the program but once your program works properly you should put the DEBUG flags in comments.
```