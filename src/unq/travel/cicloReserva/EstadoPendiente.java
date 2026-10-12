package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.traslado.Reserva;

public class EstadoPendiente extends EstadoDeReserva {

	public EstadoPendiente(Reserva reserva) {

		super(reserva);
		
	}

	@Override
	public void agregarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		items.add(item);
		
	}

	@Override
	public void quitarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		items.remove(item);
		
	}

	@Override
	public void confirmar() {

		// TODO: Testear e Implementar lógica de disponibilidad
		
		reserva.cambiarEstado(new EstadoConfirmado(reserva));
		
	}

	@Override
	public void checkin() {

		reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void ejecutar() {

		reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void completar() {

		reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void cancelar() {

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