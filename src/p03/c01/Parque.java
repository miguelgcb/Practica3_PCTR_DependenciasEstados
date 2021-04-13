package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque {

	private long timeinicial;
	private long timetotal;
	private long timemedio;
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	private static final int MINPER = 0; // mínimo valor que se nos permite
	private static final int MAXPER = 40; // máximo valor que se nos permite

	public Parque() { // TODO
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		timeinicial = System.currentTimeMillis();
		timemedio = 0;
		timetotal = 0;
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

		esperarSalirEstadoSuperior();

		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");

		// Medici�n del tiempo medi
		timetotal += (System.currentTimeMillis() - timeinicial) / 1000;
		timemedio = timetotal / contadorPersonasTotales;

		// Check invariante
		checkInvariante();

	}

	protected void esperarSalirEstadoSuperior() throws InterruptedException {
		while (contadorPersonasTotales == MAXPER)
			wait();
	}

	// Metodo Salir del Parque

	@Override
	public void salirDelParque(String puerta) {

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
		// TODO
		// TODO
	}

	protected void comprobarAntesDeEntrar() throws InterruptedException { 
		//
		// TODO
		//
	}

	protected void comprobarAntesDeSalir() throws InterruptedException{ 
		//
		// TODO
		//
	}

}
