/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.callcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author eviloria
 */
public class Dispatcher {

    /**
     * Hacemos uso de la clase Semaphore para garantizar el número de llamadas
     * concurrente.
     */
    private final Semaphore semaphore;

    /**
     * blockeo para adquirir o liberar un empleado en el proceso de las
     * llamadas.
     */
    private final Lock employeeLock;

    /**
     * Este array representa el grupo de empleados que laboran en el Call
     * Center.
     */
    private List<Employee> employees;

    public Dispatcher(int concurentCallNum, int operatorNum, int supervisorNum, int directorNum) {

        /**
         * Instanciamos la clase Semaphore con el numero de llamadas
         * recurrentes, Dado por la variable concurentCall
         */
        semaphore = new Semaphore(concurentCallNum);
        employees = new ArrayList<>();
        /**
         * Adicionamos los operadores a la lista de empleados dado el tamaño por
         * la variable operatorNum
         */
        for (int i = 0; i < operatorNum; i++) {
            employees.add(new Employee(i, EmployeeType.OPERATOR, false));
        }
        /**
         * Adicionamos los supervisores a la lista de empleados dado el tamaño
         * por la variable supervisorNum
         */
        for (int i = 0; i < supervisorNum; i++) {
            employees.add(new Employee(i, EmployeeType.SUPERVISOR, false));
        }
        /**
         * Adicionamos los directores a la lista de empleados dado el tamaño por
         * la variable directorNum
         */
        for (int i = 0; i < directorNum; i++) {
            employees.add(new Employee(i, EmployeeType.DIRECTOR, false));
        }
        employeeLock = new ReentrantLock();
    }

    /**
     * Metodo encargado de la atencion de la llamada, asignacion y desasignacion
     * de los empleados
     */
    public void dispatchCall() {
        try {
            /**
             * Se disminuye el contador del semáforo para marcar un empleado
             * como ocupado
             */
            semaphore.acquire();

            /**
             * Obtenemos el índice del empleado disponible
             */
            int assignedEmployee = getFreeEmployee();
            if (assignedEmployee != -1) {

                /**
                 * Se simula el tiempo requerido para la llamada telefónica
                 * (entre 5 y 10 segundos)
                 */
                long duration = ThreadLocalRandom.current().nextLong(5, 10);
                System.out.printf("%s atendiendo %s Duracion llamada %s\n",
                        employees.get(assignedEmployee),
                        Thread.currentThread().getName(),
                        duration);

                Thread.sleep(TimeUnit.SECONDS.toMillis(duration));

                /**
                 * Una vez terminada la llamada se libera el empleado para que
                 * pueda ser accedidos por las llamadas pendientes.
                 */
                releaseEmployee(assignedEmployee);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.printf("%s: Finalizada\n", Thread
                    .currentThread().getName());

            /**
             * Incrementamos el contador de nuevo
             */
            semaphore.release();
        }
    }

    /**
     * Método para adquirir un empleado libre para una llamada
     *
     * @return
     */
    private int getFreeEmployee() {
        int freeEmployee = -1;
        try {
            /**
             * Bloqueo el acceso para que solo un hilo a la vez puede acceder a
             * un empleado
             */
            employeeLock.lock();

            //Recorro el listado de empleados
            for (int i = 0; i < employees.size(); i++) {
                /**
                 * Verifico cual empleado esta disponioble busy=false
                 */
                if (!employees.get(i).busy) {
                    freeEmployee = i;
                    /**
                     * Si encuentro algún empleado libre lo marco como ocupado
                     * para que conteste la llamada
                     */
                    employees.get(i).busy = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * Desbloqueo el acceso para que otros hilos puedan verificar que
             * empleados están disponibles
             */
            employeeLock.unlock();
        }
        return freeEmployee;
    }

    /**
     * Método para liberar el empleado de una llamada
     *
     * @param i
     */
    private void releaseEmployee(int i) {
        try {
            employeeLock.lock();
            /**
             * Marco el empleado como disponible para que pueda ser accedido por
             * otra llamada
             */
            employees.get(i).busy = false;
        } finally {
            employeeLock.unlock();
        }
    }
}
