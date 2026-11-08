package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;

class EstadoPendienteTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private MailSender mailSender;
	
	@BeforeEach
	void setUp() {
		
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		mailSender = mock(MailSender.class);
		
		estado = new EstadoPendiente(reserva);
		
	}
	
	@Test
	void testAgregarItem() {
		
		estado.agregarItem(item);
		
		verify(reserva).doAgregarItem(item, null);
		
	}
	
	@Test
	void testQuitarItem() {
		
		estado.quitarItem(item);
		
		verify(reserva).doQuitarItem(item);
		
	}

	@Test
	void testConfirmar() {
		
		estado.confirmar();
		
		verify(reserva).doConfirmar();
		
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
		
		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.completar());
		
	}
	
	@Test
	void testCancelar() {
		
		estado.cancelar();
		
		verify(reserva).doCancelar();
		
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
		
		assertFalse(estado.esEstadoEnCurso());
		
	}
	
	@Test
	void testEstadoConfirmado() {
		
		assertFalse(estado.esEstadoConfirmado());
		
	}
	
}