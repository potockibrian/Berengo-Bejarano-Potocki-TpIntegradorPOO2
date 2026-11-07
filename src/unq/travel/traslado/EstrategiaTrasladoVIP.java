package unq.travel.traslado;

import libraries.TrasladoVIP;

public class EstrategiaTrasladoVIP implements EstrategiaTraslado {

	private libraries.TrasladoVIP libreria;

	public EstrategiaTrasladoVIP(libraries.TrasladoVIP libreria) {

		this.libreria = libreria;
		
	}

	@Override
	public float costoTasasTraslado(Reserva reserva) {

		return libreria.calcularCosto(reserva.costoServicios());

	}
/*
	@Override
	public boolean hayDisponibilidad() {

		return true;

	}
*/
}
