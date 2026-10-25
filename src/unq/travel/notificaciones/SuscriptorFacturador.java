package unq.travel.notificaciones;

import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.traslado.Reserva;

public class SuscriptorFacturador extends Suscriptor {

	@Override
	public void notify(Reserva reserva, EstadoDeReserva estado) {

		estado.generarFactura(reserva);

	}

}
