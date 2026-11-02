package unq.travel.traslado;

import libraries.PlataformaTransporte;

public class TrasladoEstandar implements EstrategiaTraslado {

	private Direccion direccion;
	private int cantidadDePasajeros;
	private PlataformaTransporte libreria;

	public TrasladoEstandar(int cantidadDePasajeros, Direccion direccion, PlataformaTransporte libreria) {
	
		this.libreria = libreria;
		this.cantidadDePasajeros = cantidadDePasajeros;
		this.direccion = direccion;
		
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
