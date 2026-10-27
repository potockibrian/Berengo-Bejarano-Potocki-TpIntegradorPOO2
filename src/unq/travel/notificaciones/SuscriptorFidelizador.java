package unq.travel.notificaciones;

import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.cicloReserva.MailSenderI;
import unq.travel.traslado.Reserva;

public class SuscriptorFidelizador extends Suscriptor {

	private MailSenderI mailSender;

	public SuscriptorFidelizador(MailSenderI mailSender) {

		this.mailSender = mailSender;
		
	}

	@Override
	public void notify(Reserva reserva, EstadoDeReserva estado) {

		estado.fidelizar(reserva, mailSender);

	}

}
