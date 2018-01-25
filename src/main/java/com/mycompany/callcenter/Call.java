/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.callcenter;

/**
 *
 * @author eviloria
 */
public class Call {
    private int numberCall;

    public Call(int numberCall) {
        this.numberCall = numberCall;
    }  
    
    public int getNumberCall() {
        return numberCall;
    }

    public void setNumberCall(int numberCall) {
        this.numberCall = numberCall;
    }

    @Override
    public String toString() {
        return "Call Number " + numberCall ;
    }
}
