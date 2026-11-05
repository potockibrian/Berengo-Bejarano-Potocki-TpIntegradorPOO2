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
					reserva.getCliente().getDireccionDeEmail()
					, "Reserva confirmada"
					, "Se ha confirmado vuestra reserva"
					, null);
		}
		
		if (estadoNuevo.esEstadoEnCurso()) {
		
			mailSender.enviarMail(
					reserva.getCliente().getDireccionDeEmail()
					, "Reserva en curso"
					, "Se ha ejecutado vuestra reserva"
					, null);
		}
		
		if (estadoNuevo.esEstadoFinalizado()) {
		
			mailSender.enviarMail(
					reserva.getCliente().getDireccionDeEmail()
					, "Reserva finalizada"
					, "Se ha finalizado vuestra reserva"
					, null);
		}
		
	}

	/*
	private boolean esEstadoConfirmado(EstadoDeReserva estadoNuevo) {
		
		return estadoNuevo.getClass().getName() == EstadoConfirmado.class.getName();
		
	}

	private boolean esEstadoEnCurso(EstadoDeReserva estadoNuevo) {
		
		return estadoNuevo.getClass().getName() == EstadoEnCurso.class.getName();
		
	}

	private boolean esEstadoFinalizado(EstadoDeReserva estadoNuevo) {
		
		return estadoNuevo.getClass().getName() == EstadoFinalizado.class.getName();
		
	}
	*/

}
