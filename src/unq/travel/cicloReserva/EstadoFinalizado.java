package unq.travel.cicloReserva;

import static org.mockito.ArgumentMatchers.eq;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

public class EstadoFinalizado extends EstadoDeReserva {

	public EstadoFinalizado(Reserva reserva) {
		super(reserva);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cancelar() {

		throw new OperacionInvalidaEstadoException();
		
	}
	
	@Override
	public boolean esEstadoFinalizado() {
		
		return true;
		
	}
	
	@Override
	public boolean esEstadoTerminal() {
		
		return true;
		
	}

}
