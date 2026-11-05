package unq.travel.cicloReserva;

import static org.mockito.ArgumentMatchers.eq;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;

public class EstadoConfirmado extends EstadoDeReserva {

	public EstadoConfirmado(Reserva reserva) {
		super(reserva);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void agregarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void quitarItem(ItemCatalogo item, List<ItemCatalogo> items) {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void confirmar(List<ItemCatalogo> items) {

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}

	@Override
	public void checkin() {

		reserva.cambiarEstado(new EstadoEnCheckin(reserva));
		
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

		for (int i=0; i<items.size(); i++) {
			
			items.get(i).incrementarCupo();
			
		}
		
		reserva.cambiarEstado(new EstadoCancelado(reserva));
		
	}
	
	@Override
	public boolean esEstadoConfirmado() {
		
		return true;
		
	}

	/*@Override
	public void notificarPorEmail(Reserva reserva, MailSender mailSender) {

		mailSender.enviarMail(
				  reserva.getCliente().getDireccionDeEmail()
				, "Reserva confirmada"
				, "Se ha confirmado vuestra reserva"
				, null);
		
	}

	@Override
	public void generarFactura(Reserva reserva) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fidelizar(Reserva reserva, MailSender mailSender) {
		// TODO Auto-generated method stub
		
	}*/

	
}