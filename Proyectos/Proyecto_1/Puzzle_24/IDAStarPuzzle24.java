package Puzzle_24;
import java.util.*;

public class IDAStarPuzzle24 {

	private static final String OBJETIVO = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,0,";
	private static final int DIMENSION = 5, TOTAL_CELDAS = 25;
	private static int nodosManhattan = 0, nodosFichas = 0, longitudManhattan = 0, longitudFichas = 0;
	private static double tiempoManhattan = 0, tiempoFichas = 0;
	private static boolean manhattanEjecutada = false, fichasEjecutada = false;
	private long nodosExpandidos = 0, tiempoInicio, tiempoFin;
	private boolean usarFichasMalColocadas;
	private String estadoInicial;
	private List<String> solucion;

	public IDAStarPuzzle24(String estadoInicial, boolean usarFichasMalColocadas) {
		this.estadoInicial = estadoInicial;
		this.usarFichasMalColocadas = usarFichasMalColocadas;
	}

	public boolean resolver() {
		tiempoInicio = System.currentTimeMillis();
		nodosExpandidos = 0;

		if (!esResoluble(estadoInicial)) {
			System.out.println("-. El estado no es resoluble");
			return false;
		}
		System.out.println("-. El estado es resoluble. ");
		System.out.println("═════════════════════════════════════════════════════════════════════════════════");

		int threshold = heuristica(estadoInicial);
		System.out.println("Umbral inicial: " + threshold);
		int iteracion = 1;

		while (true) {
			System.out.println("Iteración " + iteracion + " - Umbral: " + threshold);
			ResultadoBusqueda resultado = busquedaProfunda(new Nodo(estadoInicial, 0, null, 0), threshold);

			if (resultado.solucion != null) {
				solucion = reconstruirCamino(resultado.nodoFinal);
				tiempoFin = System.currentTimeMillis();

				double tiempoEjecucion = (tiempoFin - tiempoInicio) / 1000.0;
				int longitud = solucion.size() - 1;

				if (usarFichasMalColocadas) {
					nodosFichas = (int) nodosExpandidos;
					tiempoFichas = tiempoEjecucion;
					longitudFichas = longitud;
					fichasEjecutada = true;
				} else {
					nodosManhattan = (int) nodosExpandidos;
					tiempoManhattan = tiempoEjecucion;
					longitudManhattan = longitud;
					manhattanEjecutada = true;
				}
				return true;
			}

			if (resultado.nuevoThreshold == Integer.MAX_VALUE) {
				return false;
			}
			threshold = resultado.nuevoThreshold;
			iteracion++;
		}
	}

	private class ResultadoBusqueda {
		Nodo nodoFinal;
		int nuevoThreshold;
		List<String> solucion;

		ResultadoBusqueda(Nodo nodo, int threshold) {
			this.nodoFinal = nodo;
			this.nuevoThreshold = threshold;
			this.solucion = null;
		}

		ResultadoBusqueda(Nodo nodo) {
			this.nodoFinal = nodo;
			this.solucion = new ArrayList<>();
		}
	}

	private ResultadoBusqueda busquedaProfunda(Nodo nodo, int threshold) {
		int f = nodo.costoAcumulado + heuristica(nodo.estado);
		if (f > threshold) {
			return new ResultadoBusqueda(nodo, f);
		}
		if (nodo.estado.equals(OBJETIVO)) {
			return new ResultadoBusqueda(nodo);
		}nodosExpandidos++;
		int minThreshold = Integer.MAX_VALUE;
		List<Nodo> hijos = generarHijos(nodo);
		hijos.sort((a, b) -> {
			int ha = heuristica(a.estado);
			int hb = heuristica(b.estado);
			return Integer.compare(ha, hb);
		});
		for (Nodo hijo : hijos) {
			ResultadoBusqueda resultado = busquedaProfunda(hijo, threshold);

			if (resultado.solucion != null) {
				return resultado;
			}

			if (resultado.nuevoThreshold < minThreshold) {
				minThreshold = resultado.nuevoThreshold;
			}
		}
		return new ResultadoBusqueda(null, minThreshold);
	}

	private List<Nodo> generarHijos(Nodo padre) {
		List<Nodo> hijos = new ArrayList<>();
		String[] nums = padre.estado.split(",");

		int posVacio = -1;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i].equals("0")) {
				posVacio = i;
				break;
			}
		}
		if (posVacio == -1)
			return hijos;
		int fila = posVacio / DIMENSION;
		int col = posVacio % DIMENSION;

		int[][] movimientos = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

		for (int[] mov : movimientos) {
			int nuevaFila = fila + mov[0];
			int nuevaCol = col + mov[1];

			if (nuevaFila >= 0 && nuevaFila < DIMENSION && nuevaCol >= 0 && nuevaCol < DIMENSION) {
				int nuevaPos = nuevaFila * DIMENSION + nuevaCol;
				String nuevoEstado = intercambiar(nums, posVacio, nuevaPos);

				if (padre.padre == null || !nuevoEstado.equals(padre.padre.estado)) {
					hijos.add(new Nodo(nuevoEstado, padre.nivel + 1, padre, padre.costoAcumulado + 1));
				}
			}
		}
		return hijos;
	}

	private String intercambiar(String[] nums, int pos1, int pos2) {
		String[] copia = nums.clone();
		String temp = copia[pos1];
		copia[pos1] = copia[pos2];
		copia[pos2] = temp;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < copia.length; i++) {
			sb.append(copia[i]);
			if (i < copia.length - 1) {
				sb.append(",");
			}
		}
		sb.append(",");
		return sb.toString();
	}

	private int heuristica(String estado) {
		if (usarFichasMalColocadas) {
			return fichasMalColocadas(estado);
		} else {
			return manhattan(estado);
		}
	}

	private int manhattan(String estado) {
		String[] nums = estado.split(",");
		int distancia = 0;

		for (int i = 0; i < TOTAL_CELDAS; i++) {
			int valor = Integer.parseInt(nums[i]);
			if (valor != 0) {
				int filaActual = i / DIMENSION;
				int colActual = i % DIMENSION;
				int filaObjetivo = (valor - 1) / DIMENSION;
				int colObjetivo = (valor - 1) % DIMENSION;
				distancia += Math.abs(filaActual - filaObjetivo) + Math.abs(colActual - colObjetivo);
			}
		}
		return distancia;
	}

	private int fichasMalColocadas(String estado) {
		String[] nums = estado.split(",");
		int malColocadas = 0;

		for (int i = 0; i < TOTAL_CELDAS; i++) {
			int valor = Integer.parseInt(nums[i]);
			if (valor != 0) {
				if (i != valor - 1) {
					malColocadas++;
				}
			}
		}
		return malColocadas;
	}

	private boolean esResoluble(String estado) {
		String[] nums = estado.split(",");
		int[] arr = new int[TOTAL_CELDAS];

		for (int i = 0; i < TOTAL_CELDAS; i++) {
			arr[i] = Integer.parseInt(nums[i]);
		}

		int invCount = 0;
		for (int i = 0; i < TOTAL_CELDAS; i++) {
			if (arr[i] == 0)
				continue;
			for (int j = i + 1; j < TOTAL_CELDAS; j++) {
				if (arr[j] != 0 && arr[i] > arr[j]) {
					invCount++;
				}
			}
		}
		return (invCount % 2 == 0);
	}

	private List<String> reconstruirCamino(Nodo nodoFinal) {
		List<String> camino = new ArrayList<>();
		Nodo actual = nodoFinal;

		while (actual != null) {
			camino.add(0, actual.estado);
			actual = actual.padre;
		}
		return camino;
	}

	public void mostrarResultados() {
		if (solucion == null) {
			System.out.println("No hay solución para mostrar");
			return;
		}
		System.out.println("\n═════════════════════════════════════════════════════════════════════════════════");
		System.out.println("                              RESULTADOS                                    ");
		System.out.println("═════════════════════════════════════════════════════════════════════════════════");
		System.out.printf("%-22s │ %-17s │ %-12s │ %-18s%n", "Heurística", "Nodos Expandidos", "Tiempo (s)",
				"Longitud Solución");
		System.out.println("───────────────────────┼───────────────────┼──────────────┼────────────────────");
		System.out.printf("%-22s │ %-17d │ %-12.3f │ %-18d%n",
				(usarFichasMalColocadas ? "Fichas mal colocadas" : "Manhattan"), nodosExpandidos,
				((tiempoFin - tiempoInicio) / 1000.0), (solucion.size() - 1));
		System.out.println("═════════════════════════════════════════════════════════════════════════════════");
	}

	public void mostrarSecuenciaCompleta() {
		if (solucion == null)
			return;
		Scanner sc = new Scanner(System.in);
		System.out.println("\n¿Mostrar secuencia completa? (s/n)");
		if (sc.nextLine().equalsIgnoreCase("s")) {
			for (int i = 0; i < solucion.size(); i++) {
				System.out.println("\n========= Movimiento " + i + " =========");
				imprimirEstado(solucion.get(i));
				if (i < solucion.size() - 1) {
					System.out.println("             (↓)      ");
				}
			}
		}
	}

	public void mostrarResultadosComparativos() {
		System.out.println("\n═════════════════════════════════════════════════════════════════════════════════");
		System.out.println("                           COMPARATIVA DE HEURÍSTICAS                            ");
		System.out.println("═════════════════════════════════════════════════════════════════════════════════");
		System.out.printf("%-22s │ %-17s │ %-12s │ %-18s%n", "Heurística", "Nodos Expandidos", "Tiempo (s)",
				"Longitud Solución");
		System.out.println("───────────────────────┼───────────────────┼──────────────┼────────────────────");

		if (manhattanEjecutada) {
			System.out.printf("%-22s │ %-17d │ %-12.3f │ %-18d%n", "Manhattan", nodosManhattan, tiempoManhattan,
					longitudManhattan);
		} else {
			System.out.printf("%-22s │ %-17s │ %-12s │ %-18s%n", "Manhattan", "---", "---", "---");
		}
		if (fichasEjecutada) {
			System.out.printf("%-22s │ %-17d │ %-12.3f │ %-18d%n", "Fichas mal colocadas", nodosFichas, tiempoFichas,
					longitudFichas);
		} else {
			System.out.printf("%-22s │ %-17s │ %-12s │ %-18s%n", "Fichas mal colocadas", "---", "---", "---");
		}

		System.out.println("═════════════════════════════════════════════════════════════════════════════════");
	}

	private static void imprimirEstado(String estado) {
		String[] numeros = estado.split(",");
		System.out.println("┌─────┬─────┬─────┬─────┬─────┐");
		for (int i = 0; i < 25; i++) {
			if (i % 5 == 0)
				System.out.print("│");

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