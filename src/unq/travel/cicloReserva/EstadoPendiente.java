package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;

public class EstadoPendiente extends EstadoDeReserva {

	public EstadoPendiente(Reserva reserva) {

		super(reserva);
		
	}

	@Override
	public void agregarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		if (!item.tieneDisponibilidad()) throw new NoHayDisponibilidadException();
			
		items.add(item);
		
	}

	@Override
	public void quitarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		items.remove(item);
		
	}

	@Override
	public void confirmar(List<ItemCatalogo> items) {

		// TODO: Testear e Implementar lógica de disponibilidad
		
		items.stream().forEach(i -> i.decrementarCupo());
		
		reserva.cambiarEstado(new EstadoConfirmado(reserva));
		
	}

	@Override
	public void checkin() {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void ejecutar() {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void completar() {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void cancelar(List<ItemCatalogo> items) {

		reserva.cambiarEstado(new EstadoCancelado(reserva));
		
	}

	@Override
	public void notificarPorEmail(Reserva reserva, MailSenderI mailSender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generarFactura(Reserva reserva) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fidelizar(Reserva reserva, MailSenderI mailSender) {
		// TODO Auto-generated method stub
		
	}

	
		
}