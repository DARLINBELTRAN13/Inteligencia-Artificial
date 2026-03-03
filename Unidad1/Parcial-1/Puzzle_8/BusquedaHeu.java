package Puzzle_8;

import java.util.*;

public class BusquedaHeu {
	String estadoActual;
	String estadoFinal = "1238*4765";
	private List<String> camino = new ArrayList<>();
	private int nodosExplorados;
	private int costoTotal;
	private List<Integer> costosPorMovimiento;

	private int[][] movimientos = { { 1, 3 }, { 0, 2, 4 }, { 1, 5 }, { 0, 4, 6 }, { 1, 3, 5, 7 }, { 2, 4, 8 }, { 3, 7 },
			{ 4, 6, 8 }, { 5, 7 } };

	private Map<Nodo, Integer> heuristicaMap = new HashMap<>();

	public BusquedaHeu(String e) {
		estadoActual = e;
		nodosExplorados = 0;
		costoTotal = 0;
		costosPorMovimiento = new ArrayList<>();
	}

	public void solucionar() {
		long inicio = System.currentTimeMillis();
		if (aStarSearch()) {
			long fin = System.currentTimeMillis();  
	        long tiempoTotal = fin - inicio; 
			imprimirSolucion();
			System.out.println("-----------------------");
			System.out.println("Solución encontrada en : " + (camino.size() - 1) + " movimientos");
			System.out.println("Nodos totales explorados: " + nodosExplorados);
			System.out.println("Costo total de la solución: " + costoTotal);
			System.out.println("Tiempo de ejecución: " + (tiempoTotal / 1000.0) + " segundos");
			System.out.println("-----------------------");
		} else {
			System.out.println("No se encontró solución.");
		}
	}

	private boolean aStarSearch() {
		PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>((n1, n2) -> {
			int f1 = n1.costoAcumulado + heuristicaMap.get(n1);
			int f2 = n2.costoAcumulado + heuristicaMap.get(n2);
			return Integer.compare(f1, f2);
		});
		Map<String, Integer> mejorCostoEstado = new HashMap<>();

		int heuristicaInicial = calcularFichasMalColocadas(estadoActual);
		Nodo raiz = new Nodo(estadoActual, 0, null, 0);
		heuristicaMap.put(raiz, heuristicaInicial);

		colaPrioridad.add(raiz);
		mejorCostoEstado.put(estadoActual, 0);
		nodosExplorados++;

		while (!colaPrioridad.isEmpty()) {
			Nodo nodoActual = colaPrioridad.poll();

			if (nodoActual.estado.equals(estadoFinal)) {
				reconstruirCaminoDesdeNodo(nodoActual);
				costoTotal = nodoActual.costoAcumulado;
				return true;
			}

			int posVacia = nodoActual.estado.indexOf('*');

			for (int destino : movimientos[posVacia]) {
				String nuevoEstado = intercambiar(nodoActual.estado, posVacia, destino);
				char numeroMovido = nodoActual.estado.charAt(destino);
				int costoMovimiento;
				if (numeroMovido == '*') {
					costoMovimiento = 0;
				} else {
					costoMovimiento = Character.getNumericValue(numeroMovido);
				}

				int nuevoCosto = nodoActual.costoAcumulado + costoMovimiento;
				if (!mejorCostoEstado.containsKey(nuevoEstado) || nuevoCosto < mejorCostoEstado.get(nuevoEstado)) {
					mejorCostoEstado.put(nuevoEstado, nuevoCosto);
					int heuristica = calcularFichasMalColocadas(nuevoEstado);
					Nodo nodoHijo = new Nodo(nuevoEstado, nodoActual.nivel + 1, nodoActual, nuevoCosto);
					heuristicaMap.put(nodoHijo, heuristica);
					colaPrioridad.add(nodoHijo);
					nodosExplorados++;
				}
			}
		}return false;
	}

	/*Heuristica que utilce fue: Fichas mal colocadas, que nos cuenta cuántas fichas no se encuentran en su 
	 * posición correcta*/
	private int calcularFichasMalColocadas(String estado) {
		int malColocadas = 0;
		for (int i = 0; i < 9; i++) {
			char fichaActual = estado.charAt(i);
			char fichaObjetivo = estadoFinal.charAt(i);
			if (fichaActual != '*' && fichaActual != fichaObjetivo) {
				malColocadas++;
			}
		}return malColocadas;
	}

	public int getNodosExplorados() {
		return nodosExplorados;
	}

	public int getCostoTotal() {
		return costoTotal;
	}

	public List<Integer> getCostosPorMovimiento() {
		return costosPorMovimiento;
	}

	private void reconstruirCaminoDesdeNodo(Nodo nodoFinal) {
		camino.clear();
		costosPorMovimiento.clear();

		Stack<String> pilaEstados = new Stack<>();
		Stack<Integer> pilaCostos = new Stack<>();

		Nodo actual = nodoFinal;

		while (actual != null) {
			pilaEstados.push(actual.estado);
			if (actual.padre != null) {
				int posVaciaPadre = actual.padre.estado.indexOf('*');
				char numeroMovido = actual.estado.charAt(posVaciaPadre);
				int costoMovimiento;
				if (numeroMovido == '*') {
					costoMovimiento = 0;
				} else {
					costoMovimiento = Character.getNumericValue(numeroMovido);
				} pilaCostos.push(costoMovimiento);
			}actual = actual.padre;
		}

		while (!pilaEstados.isEmpty()) {
			camino.add(pilaEstados.pop());
		}

		while (!pilaCostos.isEmpty()) {
			costosPorMovimiento.add(pilaCostos.pop());
		}
	}

	private String intercambiar(String estado, int i, int j) {
		char[] chars = estado.toCharArray();
		char temp = chars[i];
		chars[i] = chars[j];
		chars[j] = temp;
		return new String(chars);
	}

	private void imprimirSolucion() {
		System.out.println("-----------------------");
		System.out.println("Camino de solución (A* con Heurística)");
		for (int i = 0; i < camino.size(); i++) {
			System.out.println("-----------------------");
			System.out.println("Paso " + i + " (nivel " + i + "):");
			if (i > 0) {
				System.out.println("Costo del movimiento: " + costosPorMovimiento.get(i - 1));
				int costoAcumulado = calcularCostoAcumuladoHastaPaso(i);
				System.out.println("Costo acumulado (g): " + costoAcumulado);
				int h = calcularFichasMalColocadas(camino.get(i));
				System.out.println("Heurística (h): " + h + " fichas mal colocadas");
				System.out.println("f(n) = g(n) + h(n) = " + costoAcumulado + " + " + h + " = " + (costoAcumulado + h));
				System.out.println("-----------------------");
			}
			imprimirEstado(camino.get(i));
		}
	}
	private int calcularCostoAcumuladoHastaPaso(int paso) {
		int costo = 0;
		for (int i = 0; i < paso; i++) {
			costo += costosPorMovimiento.get(i);
		}
		return costo;
	}

	private void imprimirEstado(String estado) {
		for (int i = 0; i < 9; i++) {
			System.out.print(estado.charAt(i) + " ");
			if ((i + 1) % 3 == 0) {
				System.out.println();
			}
		}
	}
}