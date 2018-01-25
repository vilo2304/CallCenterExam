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
public class Employee {

    private int cod;
    private EmployeeType type;
    protected Boolean busy;

    public Employee( int cod, EmployeeType type, Boolean busy) {
        this.cod = cod;
        this.type = type;
        this.busy = busy;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }      
   

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }   

    @Override
    public String toString() {
        return "Employee cod : " + type + " " + cod;
    }

    
}
