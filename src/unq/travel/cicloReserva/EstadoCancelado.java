package unq.travel.cicloReserva;

import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;

public class EstadoCancelado extends EstadoDeReserva {

	public EstadoCancelado(Reserva reserva) {

		super(reserva);
		
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

		throw new OperacionInvalidaEstadoException();
		
		//reserva.cambiarEstado(new ReservaException(reserva));
		
	}
	
	@Override
	public boolean esEstadoCancelado() {
		
		return true;
		
	}
	
	@Override
	public boolean esEstadoTerminal() {
		
		return true;
		
	}

	/*@Override
	public void notificarPorEmail(Reserva reserva, MailSender mailSender) {
		
		return;
		
	}

	@Override
	public void generarFactura(Reserva reserva) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fidelizar(Reserva reserva, MailSender mailSender) {

		mailSender.enviarMail(reserva.getCliente().getDireccionDeEmail()
				, "Reserva cancelada"
				, "Se ha cancelado vuestra reserva"
				, new Cupon());
				
	}*/

}
