package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;

public class EstadoCancelado extends EstadoDeReserva {

	public EstadoCancelado(Reserva reserva) {

		super(reserva);
		
	}

	@Override
	public void cancelar() {

		throw new OperacionInvalidaEstadoException();
		
	}
	
	@Override
	public boolean esEstadoCancelado() {
		
		return true;
		
	}
	
	@Override
	public boolean esEstadoTerminal() {
		
		return true;
		
	}

}
