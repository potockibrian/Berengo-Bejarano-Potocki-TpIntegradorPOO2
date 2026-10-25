package unq.travel.notificaciones;

import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.cicloReserva.MailSenderI;
import unq.travel.traslado.Reserva;

public class SuscriptorCorreo extends Suscriptor {

	private MailSenderI mailSender;

	public SuscriptorCorreo(MailSenderI mailSender) {

		this.mailSender = mailSender;
		
	}

	@Override
	public void notify(Reserva reserva, EstadoDeReserva estado) {

		estado.notificarPorEmail(reserva, mailSender);
		
	}

}
