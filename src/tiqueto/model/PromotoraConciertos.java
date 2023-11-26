package tiqueto.model;

import tiqueto.EjemploTicketMaster;

public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	@Override
	public void run() {
		while (webCompra.entradasTotales > 0){

			mensajePromotor("Voy a reponer entradas");
			webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);

			int aleatorio = (int) (Math.random()*8000) + 3000;
			try {
				PromotoraConciertos.sleep(aleatorio);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
		mensajePromotor("Ya no tengo mas entradas para reponer");
		webCompra.cerrarVenta();
	}

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajePromotor(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);

	}
}
