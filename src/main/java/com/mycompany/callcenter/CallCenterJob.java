/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.callcenter;

/**
 *  Clase para gestionar los hilos de las llamadas
 *  @author eviloria
 */
public class CallCenterJob implements Runnable{

    private final Dispatcher dispatcherCall;
 
    public CallCenterJob(Dispatcher dispatcherCall) {
        this.dispatcherCall = dispatcherCall;
    }
 
    @Override
    public void run() {
        System.out.printf("%s: Conectando llamada con el call center\n", Thread.currentThread().getName());
        dispatcherCall.dispatchCall();
    }
    
}
