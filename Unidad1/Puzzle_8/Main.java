package Puzzle_8;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String estadoInicial = "5674*8321";
        TipoBusqueda(estadoInicial);
    } 

    public static void TipoBusqueda(String estadoInicial) {
        Scanner sc = new Scanner(System.in);
        int opc;
        do {
        System.out.println("Ingrese que tipo de busqueda quiere realizar:");
        System.out.println("1. Busqueda anchura---------------------------");
        System.out.println("2. Busqueda profundidad-----------------------");
        System.out.println("3. Busqueda costo uniforme--------------------");
        System.out.println("4. Busqueda con heuristica--------------------");
        System.out.println("5. Salir------------------------------------->");
        System.out.println();
        
        opc = sc.nextInt();

            switch (opc) {
                case 1:
                	BusquedaAnchura solucion = new BusquedaAnchura(estadoInicial);
                    solucion.solucionar();
                    break;
                case 2:
                    BusquedaPofundida solucion1 = new BusquedaPofundida(estadoInicial);
                    solucion1.solucionar();
                    break;
                case 3:
                	BusquedaCostoU solucion2 = new BusquedaCostoU(estadoInicial);
                    solucion2.solucionar();
                case 4:
                    System.out.println("Opción no implementada todavía.");
                    break;
                case 5: 
                	System.out.println("Programa cerrado.");
                	break;
                default:
                    System.out.println("Error, ingrese otro numero.");
            }
            
        } while (opc != 5); 
    }
}

    
