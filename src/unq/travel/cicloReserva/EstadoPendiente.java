package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;

public class EstadoPendiente extends EstadoDeReserva {

	public EstadoPendiente(Reserva reserva) {

		super(reserva);
		
	}

	@Override
	public void agregarItem(ItemCatalogo item) {
			
		reserva.doAgregarItem(item, null);
		
	}

	@Override
	public void quitarItem(ItemCatalogo item) {

		reserva.doQuitarItem(item);
		
	}

	@Override
	public void confirmar() {

		reserva.doConfirmar();
		
	}

	@Override
	public void cancelar() {

		reserva.doCancelar();
		
	}	
		
}