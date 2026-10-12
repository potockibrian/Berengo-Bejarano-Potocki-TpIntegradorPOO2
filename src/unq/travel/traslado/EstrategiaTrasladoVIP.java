package unq.travel.traslado;

import libraries.TrasladoVIP;

public class EstrategiaTrasladoVIP implements EstrategiaTraslado {

	private Reserva reserva;

	public EstrategiaTrasladoVIP(Reserva reserva) {

		this.reserva = reserva;
		
	}

	@Override
	public float costoTraslado() {

		return TrasladoVIP.calcularCostoReserva(reserva.getPrecioTotalServicios());

	}

	@Override
	public boolean hayDisponibilidad() {

		return true;

	}

}
