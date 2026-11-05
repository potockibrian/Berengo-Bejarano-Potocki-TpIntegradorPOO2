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
	public boolean esEstadoFinalizado() {
		
		return true;
		
	}
	
	@Override
	public boolean esEstadoTerminal() {
		
		return true;
		
	}

	/*@Override
	public void notificarPorEmail(Reserva reserva, MailSender mailSender) {
		
		mailSender.enviarMail(
			  reserva.getCliente().getDireccionDeEmail()
			, "Reserva finalizada"
			, "Se ha finalizado vuestra reserva"
			, null);
		
	}

	@Override
	public void generarFactura(Reserva reserva) {

		reserva.getCliente().añadirComprobante(new Comprobante());;
		reserva.getCliente().añadirVoucher(new Voucher());		
		
	}

	@Override
	public void fidelizar(Reserva reserva, MailSender mailSender) {
		// TODO Auto-generated method stub
		
	}*/

}
