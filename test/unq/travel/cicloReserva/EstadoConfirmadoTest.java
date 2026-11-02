package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;

class EstadoConfirmadoTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private Cliente cliente;
	private MailSender mailSender;
	
	@BeforeEach
	void setUp() {
		
		mailSender = mock(MailSender.class);
		cliente = mock(Cliente.class);
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		
		estado = new EstadoConfirmado(reserva);
		
	}
	
	@Test
	void testAgregarItem() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.agregarItem(item, items));
		
		//estado.agregarItem(item, items);
		
		verify(items, never()).add(any(ItemCatalogo.class));
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testQuitarItem() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.quitarItem(item, items));
		
		//estado.quitarItem(item, items);
		
		verify(items, never()).remove(any(ItemCatalogo.class));
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testConfirmar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.confirmar(items));
		
		//estado.confirmar(items);
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCheckin() {
		
		estado.checkin();
		
		verify(reserva).cambiarEstado(any(EstadoEnCheckin.class));
		
	}
	
	@Test
	void testEjecutar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.ejecutar());
		
		//estado.ejecutar();
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCompletar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.completar());
		
		//estado.completar();
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCancelar() {
		
		List<ItemCatalogo> items1 = new ArrayList<ItemCatalogo>();
		ItemCatalogo item1 = mock(ItemCatalogo.class);
		items1.add(item);
		
		estado.cancelar(items1);
		
		verify(reserva).cambiarEstado(any(EstadoCancelado.class));
		verify(item).incrementarCupo();
		
	}
	
	/*@Test
	void testNotificarPorEmail() {
		
		when(cliente.getDireccionDeEmail()).thenReturn("testmail@test.domain");
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva confirmada")
				, eq("Se ha confirmado vuestra reserva")
				, eq(null));
		
	}
	
	@Test
	void testGenerarFactura() {
		
		estado.generarFactura(reserva);
		
		verify(reserva, never()).getCliente();
		
	}
	
	@Test
	void testFidelizar() {
		
		estado.fidelizar(reserva, mailSender);
		
		verify(reserva, never()).getCliente();
		
	}*/
	
}