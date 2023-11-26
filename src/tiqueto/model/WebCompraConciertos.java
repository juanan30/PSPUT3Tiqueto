package tiqueto.model;

import tiqueto.EjemploTicketMaster;
import tiqueto.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb{


	int entradasDisponibles;
	int entradasTotales=EjemploTicketMaster.TOTAL_ENTRADAS;
	boolean ventaActiva = true;


	public WebCompraConciertos() {
		super();
	}


	@Override
	public synchronized boolean comprarEntrada() {

		if (hayEntradas() && ventaActiva) {
			entradasRestantes();
			mensajeWeb("Entrada comprada, quedan "+entradasDisponibles+" entradas disponibles en la web");
			notifyAll();
			return true;
		} else {
			try {
				mensajeWeb("No hay entradas en este momento");
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return false;
		}
	}


	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {
		if (entradasDisponibles == 0){
			if (entradasTotales > numeroEntradas) {
				entradasDisponibles = numeroEntradas;
				entradasTotales = entradasTotales-numeroEntradas;
				mensajeWeb(" Se repusieron "+numeroEntradas+" entradas, " +
						"y las entradas restantes para reponer son "+entradasTotales);
				notifyAll();
			}else{
				entradasDisponibles=entradasTotales;
				numeroEntradas = entradasTotales;
				entradasTotales = 0;
				mensajeWeb(" Se repusieron "+numeroEntradas+" entradas, " +
						"y las entradas restantes para reponer son "+entradasTotales);
				notifyAll();
			}
		}else {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		return entradasDisponibles;
	}


	@Override
	public synchronized void cerrarVenta() {
		ventaActiva = false;
		mensajeWeb("Ventana cerrada, ya no hay mas entradas disponibles a la venta");
		notifyAll();
	}


	@Override
	public synchronized boolean hayEntradas() {
		if (entradasDisponibles > 0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public synchronized int entradasRestantes() {
		entradasDisponibles--;
		return entradasDisponibles;
	}


	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);

	}

}
