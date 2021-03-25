/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.ll1;

import java.util.Scanner;

/**
 * 
 * @author JAMES
 */
public class Analizador {
    
    
    
     private static final int ID = 1;
    private static final int NUM = 2;
    private static final int PAR_A = 3;
    private static final int PAR_C = 4;
    private static final int SUMA = 5;
    private static final int POR = 6;
    private static final int FINCADENA = 0;
    private int siguiente = 0;
    private boolean errorToken = false;
    private String dato;
    private String[] enviar;
    
    int token;
    
    public Analizador() {
    }
    
    public void avanzar() {
        token = getToken();
    }
    
    public int getToken() {
        if (siguiente < enviar.length) {
            switch (enviar[siguiente]) {
                
                case "+":
                    return 5;
                case "*":
                    return 6;
                case "(":
                    return 3;
                case ")":
                    return 4;
                case "ID":
                    return 1;
                default:
                    return verificarNum(enviar[siguiente]);
            }
        }
        return 0;
    }
    
    public int verificarNum(String dato) {
        try {
            int valorEntero = Integer.parseInt(dato);
            return 2;
        } catch (Exception e) {
        }
        return token;
    }
    
    public void error(int tok, int token) {
        
        if (tok == 9 && siguiente < enviar.length) {
            String lexema = "";
            switch (token) {
                case ID:
                    lexema = "+, *  o fin cadena";
                    break;
                case NUM:
                    lexema = "+, *  o fin cadena";
                    break;
                case PAR_A:
                    lexema = "NUMERO o ID";
                    break;
                case PAR_C:
                    lexema = "+, * o fin cadena";
                    break;
                case SUMA:
                    lexema = "NUMERO, ID o (";
                    break;
                case POR:
                    lexema = "NUMERO, ID o (";
                    break;
                default:
                    lexema = "fin cadena";
                    break;
            }
            
            System.out.printf("error sintactico, no se esperaba: %s el token esperado es: %s\n", enviar[siguiente], lexema);
            errorToken = true;
        } else {
            errorToken = true;
            System.out.printf("error sintactico, no se esperaba: %s\n", enviar[siguiente-1]);
        }
    }
    
    public void consumir(int tok) {
        if (tok == token) {
            siguiente++;
            avanzar();
        } else {
            //manejo errors
            error(tok, token);
        }
    }
    
    public void inicio() {
        Scanner in = new Scanner(System.in);
        System.out.println("Ingrese una expresion: ");
        dato = in.nextLine();
        enviar = dato.split(" ");
       // enviar = cadena;
        token = getToken();
        
        E();
        if (!errorToken) {
            System.out.println("cadena aceptada: " + dato);
        }
    }
    
    public void E() {
        
        if (token == ID || token == NUM || token == PAR_A) {
            T();
            Ep();
        } else {
            error(9, token);
            errorToken = true;
        }
        
    }
    
    public void Ep() {
        switch (token) {
            
            case PAR_C:
            case FINCADENA:/*lambda, no hago nada*/
                break;
            case SUMA:
                consumir(SUMA);
                T();
                Ep();
                break;
            default:
                error(9, token);
                break;
        }
    }
    
    public void T() {
        if (token == ID || token == NUM || token == PAR_A) {
            F();
            Tp();
        } else {
            error(9, token);
        }
    }
    
    public void Tp() {
        switch (token) {
            /*lambda, no hago nada*/
            case PAR_C:
            case SUMA:
            case FINCADENA:
                break;
            case POR:
                consumir(POR);
                F();
                Tp();
                break;
            default:
                error(9, token);
                break;
        }
    }
    
    public void F() {
        switch (token) {
            case ID:
                consumir(ID);
                break;
            case NUM:
                consumir(NUM);
                break;
            case PAR_A:
                consumir(PAR_A);
                E();
                consumir(PAR_C);
                break;
            default:
                error(9, token);
        }
    }
    
    

}
