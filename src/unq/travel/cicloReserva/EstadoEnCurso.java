package unq.travel.cicloReserva;

import static org.mockito.ArgumentMatchers.eq;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

public class EstadoEnCurso extends EstadoDeReserva {

	public EstadoEnCurso(Reserva reserva) {

		super(reserva);

	}

	@Override
	public void completar() {

		reserva.doFinalizar();
		
	}

	@Override
	public void cancelar() {

		reserva.doCancelarFromEnCurso();
		
	}
	
	@Override
	public boolean esEstadoEnCurso() {
		
		return true;
		
	}

}
