package src.p03.c01;

public class SistemaLanzador {
	public static void main(String[] args) {
		final int AFORO = 50;
		IParque parque = new Parque(AFORO);
		char letra_puerta = 'A';

		System.out.println("¡Parque abierto!");

		for (int i = 0; i < Integer.parseInt(args[0]); i++) { // En los argumentos de ejecucion poner un 5.

			String puerta = "" + ((char) (letra_puerta++));

			// Creacion de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread(entradas).start();

			// Creacion de hilos de salida
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread(salidas).start();

		}
	}
}
