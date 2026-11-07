package unq.travel.cicloReserva;

import static org.mockito.ArgumentMatchers.eq;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;

public class EstadoConfirmado extends EstadoDeReserva {

	public EstadoConfirmado(Reserva reserva) {
		
		super(reserva);
		
	}

	@Override
	public void checkin() {

		reserva.doCheckin();
		
	}

	@Override
	public void cancelar() {
		
		reserva.doCancelarFromConfirmada();
		
	}
	
	@Override
	public boolean esEstadoConfirmado() {
		
		return true;
		
	}

	
}