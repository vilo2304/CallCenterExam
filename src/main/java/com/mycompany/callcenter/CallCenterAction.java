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
public class CallCenterAction {

    public static void main(String[] args) {
        /**
         * Numero de empleados tipo operadores
         */
        int operatorNum = 7;
        /**
         * Numero de empleados tipo supervisores
         */
        int supervisorNum = 2;
        
          /**
         * Numero de empleados tipo directores
         */
        int directorNum = 1;
         /**
         * Numero de Llamadas entrantes
         */
        int callNum = 2;
         /**
          * Numero de Llamadas Concurentes
         */
         int concurentCall = 1;

        /**
         * Instancaimos la clase Dispatcher para configurar los parametro de entrada de la operacion
         */ 
        Dispatcher dispatcher = new Dispatcher(concurentCall, operatorNum, supervisorNum, directorNum);
        
        /**
         * Creamos una coleccion de hilos por el numero de llamadas a gestionar en el proceso
         */
        Thread calls[] = new Thread[callNum];
        for (int i = 0; i < callNum; i++) {
            calls[i] = new Thread(new CallCenterJob(dispatcher), new Call(i).toString());
        }
        for (int i = 0; i < callNum; i++) {
            calls[i].start();
        }
    }
}
