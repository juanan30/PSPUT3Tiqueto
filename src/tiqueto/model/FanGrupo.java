package tiqueto.model;

import tiqueto.EjemploTicketMaster;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;

	private String tabuladores = "\t\t\t\t";

	public int getEntradasCompradas() {
		return entradasCompradas;
	}

	public void setEntradasCompradas(int entradasCompradas) {
		this.entradasCompradas = entradasCompradas;
	}

	int entradasCompradas = 0;

	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
	}

	@Override
	public synchronized void run() {
		while(webCompra.ventaActiva && EjemploTicketMaster.MAX_ENTRADAS_POR_FAN > entradasCompradas){

			mensajeFan("Voy a comprar 1 entrada");
			if (webCompra.comprarEntrada()){
				entradasCompradas++;
				mensajeFan("Ya compre la entrada y tengo un total de "+entradasCompradas+" entradas compradas");
			}
			if (webCompra.ventaActiva == false){
				mensajeFan("OH! se cerro la venta , ya no podre comprar mas entradas :(");
			}
			int aleatorio = (int) (Math.random()*3000) + 1000;

			try {
				FanGrupo.sleep(aleatorio);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}



	}

	public void dimeEntradasCompradas() {
		mensajeFan("Sólo he conseguido: " + entradasCompradas);
	}

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeFan(String mensaje) {
		System.out.println(System.currentTimeMillis() + "|" + tabuladores +" Fan "+this.numeroFan +": " + mensaje);
	}
}
