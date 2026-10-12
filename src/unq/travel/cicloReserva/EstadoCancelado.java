package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.traslado.Reserva;

public class EstadoCancelado extends EstadoDeReserva {

	public EstadoCancelado(Reserva reserva) {

		super(reserva);
		
	}

	@Override
	public void agregarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void quitarItem(ItemCatalogo item, List<ItemCatalogo> items) {
		
		reserva.cambiarEstado(new ReservaException(reserva));
				
	}

	@Override
	public void confirmar(List<ItemCatalogo> items) {
		
		reserva.cambiarEstado(new ReservaException(reserva));
	
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
	public void cancelar(List<ItemCatalogo> items) {

		reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void notificarPorEmail(Reserva reserva, MailSenderI mailSender) {
		
		return;
		
	}

	@Override
	public void generarFactura(Reserva reserva) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fidelizar(Reserva reserva, MailSenderI mailSender) {

		mailSender.enviarMail(reserva.getCliente().getDireccionDeEmail()
				, "Reserva cancelada"
				, "Se ha cancelado vuestra reserva"
				, new Cupon());
				
	}

}
