package com.mod_snmp.SmiParser.ErrorHandler;
/* Copyright 2000-2013 Harrie Hazewinkel. All rights reserved.*/
import java.util.Vector;

/**
 * An extension to Vector in which a 'Message' gets stored.
 * The 'Message' is created from SmiExceptions.
 */
public class MessageList extends Vector {
    static public MessageList list = new MessageList();

    /**
     * Print function of the message list.
     */
    public void print() {
        if (size() > 0) {
            for (int i =  size() - 1; i >= 0 ; i--) {
                ((Message)elementAt(i)).print();
            }
            System.err.println("Output may be flawed: " + size()
                                                + " messages");
        }
    }
    /**
     * Add a parsing message in the message list by line number order.
     */
    public void add(Message msg) {
        int i = 0;
        while (i < size()) {
            if (msg.line > ((Message)elementAt(i)).line) {
                break;
            }
            i++;
        }
        insertElementAt(msg, i);
    }
    /**
     * Wrapper to see whether messages are in the list.
     */
    public boolean hasErrors() {
        return (0 != size());
    }
}
