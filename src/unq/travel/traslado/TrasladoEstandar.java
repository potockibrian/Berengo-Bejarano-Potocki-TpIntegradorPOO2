package unq.travel.traslado;

import libraries.PlataformaTransporte;

public class TrasladoEstandar extends EstrategiaTraslado {

	private Direccion direccion;
	private int cantidadDePasajeros;
	private PlataformaTransporte libreria;

	public TrasladoEstandar(PlataformaTransporte libreria) {
	
		this.libreria = libreria;
		
	}

	@Override
	public float costoTasasTraslado(Reserva reserva) {

		return libreria.estimarTraslado(reserva.cantidadPasajeros(), reserva.direccion());

	}

}
