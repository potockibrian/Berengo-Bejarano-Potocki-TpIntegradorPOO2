package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

class EstadoEnCursoTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private Cliente cliente;
	private MailSender mailSender;
	
	@BeforeEach
	void setUp() {

		cliente = mock(Cliente.class);
		mailSender = mock(MailSender.class);
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		
		estado = new EstadoEnCurso(reserva);
		
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

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.checkin());
		
		//estado.checkin();
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testEjecutar() {

		RuntimeException e = assertThrows(OperacionInvalidaEstadoException.class, () -> estado.ejecutar());
		
		//estado.ejecutar();
		
		//verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCompletar() {
		
		estado.completar();
		
		verify(reserva).cambiarEstado(any(EstadoFinalizado.class));
		
	}
	
	@Test
	void testCancelar() {
		
		// TODO: testear e implementar el reembolso con voucher
		
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.cancelar(items);
		
		verify(cliente).añadirVoucher(any(Voucher.class));
		verify(reserva).cambiarEstado(any(EstadoCancelado.class));
		
	}
	
	@Test
	void testEstadoTerminal() {
		
		assertFalse(estado.esEstadoTerminal());
		
	}
	
	@Test
	void testEstadoCancelado() {
		
		assertFalse(estado.esEstadoCancelado());
		
	}
	
	@Test
	void testEstadoFinalizado() {
		
		assertFalse(estado.esEstadoFinalizado());
		
	}
	
	@Test
	void testEstadoEnCurso() {
		
		assertTrue(estado.esEstadoEnCurso());
		
	}
	
	@Test
	void testEstadoConfirmado() {
		
		assertFalse(estado.esEstadoConfirmado());
		
	}
	
	/*@Test
	void testNotificarPorEmail() {
		
		when(cliente.getDireccionDeEmail()).thenReturn("testmail@test.domain");
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva en curso")
				, eq("Se ha ejecutado vuestra reserva")
				, eq(null));
		
	}
	
	@Test
	void testGenerarFactura() {
		
		estado.generarFactura(reserva);
		
		verify(reserva, never()).getCliente();;
		
	}
	
	@Test
	void testFidelizar() {
		
		estado.fidelizar(reserva, mailSender);
		
		verify(reserva, never()).getCliente();
		
	}*/
	
}