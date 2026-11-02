package unq.travel.traslado;

import libraries.PlataformaTransporte;

public class TrasladoEstandar implements EstrategiaTraslado {

	private Direccion direccion;
	private int cantidadDePasajeros;
	private PlataformaTransporte libreria;

	public TrasladoEstandar(PlataformaTransporte libreria) {
	
		this.libreria = libreria;
		
	}

	@Override
	public float costoTraslado(Reserva reserva) {

		return libreria.estimarTraslado(reserva.cantidadPasajeros(), reserva.direccion());

	}

	/*@Override
	public boolean hayDisponibilidad() {

		return true;

	}*/

}
