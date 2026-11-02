package unq.travel.traslado;

import libraries.TrasladoVIP;

public class EstrategiaTrasladoVIP implements EstrategiaTraslado {

	private libraries.TrasladoVIP libreria;

	public EstrategiaTrasladoVIP(libraries.TrasladoVIP libreria) {

		this.libreria = libreria;
		
	}

	@Override
	public float costoTraslado(Reserva reserva) {

		return libreria.calcularCosto(reserva.costoTotalServicios());

	}
/*
	@Override
	public boolean hayDisponibilidad() {

		return true;

	}
*/
}
