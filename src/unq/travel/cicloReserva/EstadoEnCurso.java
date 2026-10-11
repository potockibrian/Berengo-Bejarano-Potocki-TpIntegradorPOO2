package unq.travel.cicloReserva;

import static org.mockito.ArgumentMatchers.eq;

import java.util.List;

import unq.travel.traslado.Reserva;

public class EstadoEnCurso extends EstadoDeReserva {

	public EstadoEnCurso(Reserva reserva) {
		super(reserva);
		// TODO Auto-generated constructor stub
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
	public void confirmar() {

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

		reserva.cambiarEstado(new EstadoFinalizado(reserva));
		
	}

	@Override
	public void cancelar() {

		reserva.cambiarEstado(new EstadoCancelado(reserva));
		
	}

	@Override
	public void notificarPorEmail(Reserva reserva, MailSenderI mailSender) {
		
		mailSender.enviarMail(
			  reserva.getCliente().getDireccionDeEmail()
			, "Reserva en curso"
			, "Se ha ejecutado vuestra reserva"
			, null);
		
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
