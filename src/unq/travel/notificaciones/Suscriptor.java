package unq.travel.notificaciones;

import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.traslado.Reserva;

public interface Suscriptor {

	public abstract void notify(Reserva reserva, EstadoDeReserva estadoAnterior, EstadoDeReserva nuevoEstado);
	
}
