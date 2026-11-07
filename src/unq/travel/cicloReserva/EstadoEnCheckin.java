package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

public class EstadoEnCheckin extends EstadoDeReserva {

	public EstadoEnCheckin(Reserva reserva) {

		super(reserva);

	}

	@Override
	public void ejecutar() {
		
		reserva.doEjecutar();
		
	}

	@Override
	public void cancelar() {
		
		reserva.doCancelarFromCheckin();
		
	}
	
}
