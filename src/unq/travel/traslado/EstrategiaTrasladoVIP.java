package unq.travel.traslado;

import libraries.TrasladoVIP;

public class EstrategiaTrasladoVIP implements EstrategiaTraslado {

	private Reserva reserva;
	private libraries.TrasladoVIP libreria;

	public EstrategiaTrasladoVIP(Reserva reserva, libraries.TrasladoVIP libreria) {

		this.libreria = libreria;
		this.reserva = reserva;
		
	}

	@Override
	public float costoTraslado(Reserva reserva) {

		return libreria.calcularCosto(reserva.getPrecioTotalServicios());

	}
/*
	@Override
	public boolean hayDisponibilidad() {

		return true;

	}
*/
}
