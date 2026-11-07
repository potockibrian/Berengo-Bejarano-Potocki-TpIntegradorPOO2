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

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.agregarItem(item));
		
		verify(items, never()).add(any(ItemCatalogo.class));
		
	}
	
	@Test
	void testQuitarItem() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.quitarItem(item));
		
		verify(items, never()).remove(any(ItemCatalogo.class));
		
	}
	
	@Test
	void testConfirmar() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.confirmar());
		
	}
	
	@Test
	void testCheckin() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.checkin());
		
	}
	
	@Test
	void testEjecutar() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.ejecutar());
		
	}
	
	@Test
	void testCompletar() {
		
		estado.completar();
		
		verify(reserva).doFinalizar();
		
	}
	
	@Test
	void testCancelar() {
		
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.cancelar();
		
		verify(reserva).doCancelarFromEnCurso();
		
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
	
}