package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.notificaciones.Suscriptor;
import unq.travel.traslado.Reserva;

public abstract class EstadoDeReserva {

	protected Reserva reserva;

	public EstadoDeReserva(Reserva reserva) {
		
		this.reserva = reserva;
		
	}
	
	public void agregarItem(ItemCatalogo item) {
		
		throw new OperacionInvalidaEstadoException();
		
	}

	public void quitarItem(ItemCatalogo item) {
		
		throw new OperacionInvalidaEstadoException();
		
	}

	public void confirmar() {
		
		throw new OperacionInvalidaEstadoException();
		
	}

	public void checkin() {
		
		throw new OperacionInvalidaEstadoException();
		
	}

	public void ejecutar() {
		
		throw new OperacionInvalidaEstadoException();
		
	}

	public void completar() {
		
		throw new OperacionInvalidaEstadoException();
		
	}

	public abstract void cancelar() ;

	public boolean esEstadoTerminal() {

		return false;
		
	}

	public boolean esEstadoCancelado() {

		return false;
		
	}

	public boolean esEstadoFinalizado() {

		return false;
		
	}

	public boolean esEstadoEnCurso() {

		return false;
		
	}

	public boolean esEstadoConfirmado() {

		return false;
		
	}

}
