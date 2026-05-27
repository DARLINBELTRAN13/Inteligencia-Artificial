package Puzzle_24;
import java.util.*;

public class convinaciones {

	public static void main(String[] args) {
		 generarCombinacionResoluble();

	}
	
	public static String generarCombinacion() {
		List<Integer> numeros = new ArrayList<>();

		for (int i = 0; i <= 24; i++) {
			numeros.add(i);
		}
		long semilla = System.currentTimeMillis();
		Random random = new Random(semilla);
		Collections.shuffle(numeros, random);
		StringBuilder estado = new StringBuilder();
		for (int num : numeros) {
			estado.append(num).append(",");
		}
		return estado.toString();
	}
	
	public static String generarCombinacionResoluble() {
		String estado;
		do {
			estado = generarCombinacion();
		} while (!esResoluble(estado));
		
		return estado;
	}
	
	public static boolean esResoluble(String estado) {
		String[] nums = estado.split(",");
		int[] arr = new int[25];
		
		for (int i = 0; i < 25; i++) {
			arr[i] = Integer.parseInt(nums[i]);
		}
		
		int invCount = 0;
		for (int i = 0; i < 25; i++) {
			if (arr[i] == 0) continue;
			for (int j = i + 1; j < 25; j++) {
				if (arr[j] != 0 && arr[i] > arr[j]) {
					invCount++;
				}
			}
		}
		return (invCount % 2 == 0);
	}
}