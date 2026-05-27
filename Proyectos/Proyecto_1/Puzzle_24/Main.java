package Puzzle_24;
import java.util.Scanner;
public class Main {
    
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	String estadoInicial;
    	int opc;
        System.out.println("\n═════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                              PUZZLE 24                                    ");
        System.out.println("═════════════════════════════════════════════════════════════════════════════════");
    	System.out.println("Seleccione la manera de generar el tablero:");
    	System.out.println("1. Manual");
    	System.out.println("2. Aleatoria");
    	opc = sc.nextInt();
    	sc.nextLine(); 
        System.out.println("═════════════════════════════════════════════════════════════════════════════════");
    	
    	if (opc == 1) {
    		System.out.println("Modo manual - Ingresa los 25 números del 0 al 24 sin repetir (separados por comas):");    		
    		estadoInicial = sc.nextLine();
    		if (!validarCadena(estadoInicial)) {
    			System.out.println("\nPresiona Enter para salir...");
    			sc.nextLine(); 
    			return; 
    		}

    		if (!convinaciones.esResoluble(estadoInicial)) {
    			System.out.println("Nota: Este estado NO es resoluble matemáticamente");
    			System.out.println("¿Deseas continuar de todas formas? (s/n)");
    			String respuesta = sc.nextLine();
    			if (!respuesta.equalsIgnoreCase("s")) {
    				System.out.println("Saliendo...");
    				return;
    			}
    		}
    	} else {
    		estadoInicial = convinaciones.generarCombinacionResoluble();
    		System.out.println("Estado aleatorio resoluble generado:");
    	}
    	System.out.println("\n═════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                              TABLERO INICIAL                                    ");
        System.out.println("═════════════════════════════════════════════════════════════════════════════════");
    	imprimirEstado(estadoInicial);   	
    	System.out.println("═════════════════════════════════════════════════════════════════════════════════");
    	
    	boolean continuar = true;
    	while (continuar) {
    		System.out.println("═════════════════════════════════════════════════════════════════════════════════"); 
    		System.out.println("\n════════════════════════════════ SELECCIONAR HEURÍSTICA ════════════════════════════════");
            System.out.println("1. Distancia de Manhattan");
            System.out.println("2. Piezas mal colocadas");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            int heuristica = sc.nextInt();
            sc.nextLine();
            
            if (heuristica == 3) {
                System.out.println("¡Hasta luego!");
                continuar = false;
                break;
            }
            
            System.out.println("═════════════════════════════════════════════════════════════════════════════════");
            boolean PiezasMalC = (heuristica == 2);
            
            System.out.println("\n══════════════════════════════════ IDA* ══════════════════════════════════");
            IDAStarPuzzle24 resolver = new IDAStarPuzzle24(estadoInicial, PiezasMalC);
            
            if (resolver.resolver()) {
                resolver.mostrarResultados();
                resolver.mostrarSecuenciaCompleta();
                resolver.mostrarResultadosComparativos();
            } else {
                System.out.println("No se pudo encontrar una solución");
            }
    	}
        
        System.out.println("\nPresiona Enter para salir...");
        sc.nextLine();
    	sc.close();
    }
    
    private static boolean validarCadena(String estadoInicial) {
        String[] numerosIngresados = estadoInicial.split(",");
        
        if (numerosIngresados.length != 25) {
            System.out.println("Error: Debes ingresar exactamente 25 números");
            return false;
        }
        boolean[] numerosValidos = new boolean[25];
        
        for (int i = 0; i < 25; i++) {
            try {
                int num = Integer.parseInt(numerosIngresados[i].trim());
                if (num < 0 || num > 24) {
                    System.out.println("Error: El número " + num + " debe estar entre 0 y 24");
                    return false;
                }
                if (numerosValidos[num]) {
                    System.out.println("Error: El número " + num + " está repetido");
                    return false;
                }
                numerosValidos[num] = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato inválido en la posición " + (i + 1));
                return false;
            }
        }
        return true;
    }
    
    private static void imprimirEstado(String estado) {
		String[] numeros = estado.split(",");
        System.out.println("┌─────┬─────┬─────┬─────┬─────┐");
		for (int i = 0; i < 25; i++) {
            if (i % 5 == 0) System.out.print("│");
            
            if (numeros[i].trim().equals("0")) {
                System.out.print("  _  │");
            } else {
			    System.out.printf("%3s  │", numeros[i].trim());
            }
            
			if ((i + 1) % 5 == 0) {
				System.out.println();
                if (i < 24) {
                    System.out.println("├─────┼─────┼─────┼─────┼─────┤");
                }
			}
		}
        System.out.println("└─────┴─────┴─────┴─────┴─────┘");
	}
}