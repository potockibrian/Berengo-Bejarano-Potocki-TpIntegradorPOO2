package unq.travel.notificaciones;

import unq.travel.cicloReserva.Comprobante;
import unq.travel.cicloReserva.EstadoCancelado;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.cicloReserva.EstadoFinalizado;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

public class SuscriptorFacturador implements Suscriptor {

	@Override
	public void notify(Reserva reserva, EstadoDeReserva estadoAnterior, EstadoDeReserva estadoNuevo) {

		if (estadoNuevo.esEstadoFinalizado()) {
			
			reserva.generarComprobanteAlCliente();
			
		}

	}

}
