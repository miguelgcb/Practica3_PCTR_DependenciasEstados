package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque {

	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	private int maximoPermitido; // máximo valor que se nos permite

	public Parque(int maximoPermitido) {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}

	@Override
	public synchronized void entrarAlParque(String puerta) throws InterruptedException { // Anadimos el synchronized ,
																							// como candado para que
																							// solo uno de los hilos
																							// pueda acceder al metodo.

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		// Comprobamos el estado

		comprobarAntesDeEntrar();

		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

		notifyAll();

		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");

		// Check invariante
		checkInvariante();

	}

	// Metodo Salir del Parque

	@Override
	public void salirDelParque(String puerta) throws InterruptedException {

		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.remove(puerta);

		}

		// Comprobamos antes de salir

		comprobarAntesDeSalir();

		sumarContadoresPuerta();

		// Imprimir estado del parque
		imprimirInfo(puerta, "Salida");

		// Check invariante
		checkInvariante();
	}

	private void imprimirInfo(String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); // + " tiempo medio de estancia: " +
																					// tmedio);

		// Iteramos por todas las puertas e imprimimos sus entradas
		for (String p : contadoresPersonasPuerta.keySet()) {
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}

	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
		Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
		while (iterPuertas.hasMoreElements()) {
			sumaContadoresPuerta += iterPuertas.nextElement();
		}
		return sumaContadoresPuerta;
	}

	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales
				: "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadorPersonasTotales < maximoPermitido : "No se permiten mas personas.";
		assert contadorPersonasTotales >= 0 : "Error en la gestion de salidas.";
	}

	protected void comprobarAntesDeEntrar() throws InterruptedException {
		while (contadorPersonasTotales == maximoPermitido) {
			wait();

		}
	}

	protected void comprobarAntesDeSalir() throws InterruptedException {
		while (contadorPersonasTotales == 0) {
			wait();

		}
	}

}
