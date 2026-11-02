package unq.travel.notificaciones;

import unq.travel.cicloReserva.Cupon;
import unq.travel.cicloReserva.EstadoCancelado;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.cicloReserva.EstadoFinalizado;
import unq.travel.cicloReserva.MailSender;
import unq.travel.traslado.Reserva;

public class SuscriptorFidelizador implements Suscriptor {

	private MailSender mailSender;

	public SuscriptorFidelizador(MailSender mailSender) {

		this.mailSender = mailSender;
		
	}

	@Override
	public void notify(Reserva reserva, EstadoDeReserva estadoAnterior, EstadoDeReserva estadoNuevo) {

		if (!esEstadoTerminal(estadoAnterior) && esEstadoCancelado(estadoNuevo)) {
			
			mailSender.enviarMail(
					  reserva.getCliente().getDireccionDeEmail()
					, "Reserva cancelada"
					, "Se ha cancelado vuestra reserva"
					, new Cupon());
			
		}
		
	}

	private boolean esEstadoCancelado(EstadoDeReserva estadoNuevo) {
		
		return estadoNuevo.getClass().getTypeName() == EstadoCancelado.class.getName();
	
	}

	private boolean esEstadoTerminal(EstadoDeReserva estadoAnterior) {

		return estadoAnterior.getClass().getTypeName() == EstadoCancelado.class.getName()
				|| estadoAnterior.getClass().getTypeName() == EstadoFinalizado.class.getName();
		
	}

}
