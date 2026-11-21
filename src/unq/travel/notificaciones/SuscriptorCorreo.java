package unq.travel.notificaciones;

import unq.travel.cicloReserva.EstadoConfirmado;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.cicloReserva.EstadoEnCurso;
import unq.travel.cicloReserva.EstadoFinalizado;
import unq.travel.cicloReserva.MailSender;
import unq.travel.traslado.Reserva;

public class SuscriptorCorreo implements Suscriptor {

	private MailSender mailSender;

	public SuscriptorCorreo(MailSender mailSender) {

		this.mailSender = mailSender;
		
	}

	@Override
	public void notify(Reserva reserva, EstadoDeReserva estadoAnterior, EstadoDeReserva estadoNuevo) {
		
		if (estadoNuevo.esEstadoConfirmado()) {
		
			mailSender.enviarMail(
					reserva.obtenerEmailDelCliente()
					, "Reserva confirmada"
					, "Se ha confirmado vuestra reserva"
					, null);
		}
		
		if (estadoNuevo.esEstadoEnCurso()) {
		
			mailSender.enviarMail(
					reserva.obtenerEmailDelCliente()
					, "Reserva en curso"
					, "Se ha ejecutado vuestra reserva"
					, null);
		}
		
		if (estadoNuevo.esEstadoFinalizado()) {
		
			mailSender.enviarMail(
					reserva.obtenerEmailDelCliente()
					, "Reserva finalizada"
					, "Se ha finalizado vuestra reserva"
					, null);
		}
		
	}

}
