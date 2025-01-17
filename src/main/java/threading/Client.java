package threading;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * main.java.threading.Client class
 *
 * @author Kerly Titus
 */

public class Client extends Thread {

    private static int numberOfTransactions;        /* Number of transactions to process */
    private static int maxNbTransactions;            /* Maximum number of transactions */
    private static Transaction[] transactions;    /* main.java.threading.Transactions to be processed */
    private static Network objNetwork;            /* main.java.threading.Client object to handle network operations */
    private String clientOperation;                    /* sending or receiving */

    /**
     * Constructor method of main.java.threading.Client class
     *
     * @param
     * @return
     */
    Client(String operation) {
        if (operation.equals("sending")) {
            System.out.println("\n Initializing client sending application ...");
            numberOfTransactions = 0;
            maxNbTransactions = 100;
            transactions = new Transaction[maxNbTransactions];
            objNetwork = new Network("client");
            clientOperation = operation;
            System.out.println("\n Initializing the transactions ... ");
            readTransactions();
            System.out.println("\n Connecting client to network ...");
            String cip = objNetwork.getClientIP();
            if (!(objNetwork.connect(cip))) {
                System.out.println("\n Terminating client application, network unavailable");
                System.exit(0);
            }
        } else if (operation.equals("receiving")) {
            System.out.println("\n Initializing client receiving application ...");
            clientOperation = operation;
        }
    }

    /**
     * Accessor method of main.java.threading.Client class
     *
     * @param
     * @return numberOfTransactions
     */
    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    /**
     * Mutator method of main.java.threading.Client class
     *
     * @param nbOfTrans
     * @return
     */
    public void setNumberOfTransactions(int nbOfTrans) {
        numberOfTransactions = nbOfTrans;
    }

    /**
     * Accessor method of main.java.threading.Client class
     *
     * @param
     * @return clientOperation
     */
    public String getClientOperation() {
        return clientOperation;
    }

    /**
     * Mutator method of main.java.threading.Client class
     *
     * @param operation
     * @return
     */
    public void setClientOperation(String operation) {
        clientOperation = operation;
    }

    /**
     * Reading of the transactions from an input file
     *
     * @param
     * @return
     */
    public void readTransactions() {
        Scanner inputStream = null;     /* main.java.threading.Transactions input file stream */
        int i = 0;                      /* Index of transactions array */

        try {
            inputStream = new Scanner(new FileInputStream("src/main/resources/transaction.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File transaction.txt was not found");
            System.out.println("or could not be opened.");
            System.exit(0);
        }
        while (inputStream.hasNextLine()) {
            try {
                transactions[i] = new Transaction();
                transactions[i].setAccountNumber(inputStream.next());            /* Read account number */
                transactions[i].setOperationType(inputStream.next());            /* Read transaction type */
                transactions[i].setTransactionAmount(inputStream.nextDouble());  /* Read transaction amount */
                transactions[i].setTransactionStatus("pending");                 /* Set current transaction status */
                i++;
            } catch (InputMismatchException e) {
                System.out.println("Line " + i + "file transactions.txt invalid input");
                System.exit(0);
            }

        }
        setNumberOfTransactions(i);        /* Record the number of transactions processed */

//        System.out.println("\n DEBUG : Client.readTransactions() - " + getNumberOfTransactions() + " transactions processed");

        inputStream.close();

    }

    /**
     * Sending the transactions to the server
     *
     * @param
     * @return
     */
    public void sendTransactions() {
        int i = 0;     /* index of transaction array */

        while (i < getNumberOfTransactions()) {
            while (objNetwork.getInBufferStatus().equals("full")) {
                Thread.yield();
            }
            // while( objNetwork.getInBufferStatus().equals("full") );     /* Alternatively, busy-wait until the network input buffer is available */

            transactions[i].setTransactionStatus("sent");   /* Set current transaction status */

//            System.out.println("\n DEBUG : Client.sendTransactions() - sending transaction on account " + transactions[i].getAccountNumber());

            objNetwork.send(transactions[i]);                            /* Transmit current transaction */
            i++;

        }
    }

    /**
     * Receiving the completed transactions from the server
     *
     * @param transact
     * @return
     */
    public void receiveTransactions(Transaction transact) {
        int i = 0;     /* Index of transaction array */

        while (i < getNumberOfTransactions()) {
            while (objNetwork.getOutBufferStatus().equals("empty")) {
                Thread.yield();
            }
            // while( objNetwork.getOutBufferStatus().equals("empty"));  	/* Alternatively, busy-wait until the network output buffer is available */

            objNetwork.receive(transact);                                /* Receive updated transaction from the network buffer */

//            System.out.println("\n DEBUG : Client.receiveTransactions() - receiving updated transaction on account " + transact.getAccountNumber());

            System.out.println(transact);                                /* Display updated transaction */
            i++;
        }
    }

    /**
     * Create a String representation based on the main.java.threading.Client Object
     *
     * @param
     * @return String representation
     */
    public String toString() {
        return ("\n client IP " + objNetwork.getClientIP() + " Connection status" + objNetwork.getClientConnectionStatus() + "Number of transactions " + getNumberOfTransactions());
    }

    /**
     * Code for the run method
     *
     * @param
     * @return
     */
    public void run() {
        Transaction transact = new Transaction();
        /* Implement the code for the run method */

        if (getClientOperation().equals("sending")) {
            long sendClientStartTime = System.currentTimeMillis();

            sendTransactions();
            System.out.println("\n Terminating client sending thread");

            long sendClientEndTime = System.currentTimeMillis();
            System.out.println("\n Sending client thread - Running time " + (sendClientEndTime - sendClientStartTime) + " milliseconds");
        }

        if (getClientOperation().equals("receiving")) {
            long receiveClientStartTime = System.currentTimeMillis();

            receiveTransactions(transact);
            System.out.println("\n Terminating client receiving thread");

            long receiveClientEndTime = System.currentTimeMillis();
            System.out.println("\n Receiving client thread - Running time " + (receiveClientEndTime - receiveClientStartTime) + " milliseconds");

            objNetwork.disconnect(objNetwork.getClientIP());
        }
    }
}
