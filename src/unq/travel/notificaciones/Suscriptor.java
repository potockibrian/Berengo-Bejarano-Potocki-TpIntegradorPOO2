package unq.travel.notificaciones;

import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.traslado.Reserva;

public abstract class Suscriptor {

	public abstract void notify(Reserva reserva, EstadoDeReserva estado);
	
}
