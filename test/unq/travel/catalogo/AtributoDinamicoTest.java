package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtributoDinamicoTest {

	private TipoAtributo tipo;
	private AtributoDinamico obligatorio;
	private AtributoDinamico opcional;

	@BeforeEach
	void setUp() {
		tipo = mock(TipoAtributo.class);
		when(tipo.esValorValido(4)).thenReturn(true);
		when(tipo.esValorValido("cuatro")).thenReturn(false);

		obligatorio = new AtributoDinamico("CapacidadMaxima", true, tipo);
		opcional = new AtributoDinamico("RatingEstrellas", false, tipo);
	}

	@Test
	void testNoTieneValorHastaQueSeLeAsigna() {

		assertFalse(obligatorio.tieneValor());

		obligatorio.asignarValor(4);

		assertTrue(obligatorio.tieneValor());

	}

	@Test
	void testDevuelveElValorAsignado() {

		obligatorio.asignarValor(4);

		assertEquals(4, obligatorio.getValor());

	}

	@Test
	void testConsultarElValorSinHaberloAsignadoLanzaExcepcion() {

		assertThrows(AtributoSinValorException.class, () -> obligatorio.getValor());

	}

	@Test
	void testObligatorioSinValorNoCumpleConLaValidacion() {

		assertFalse(obligatorio.esValido());

	}

	@Test
	void testObligatorioConValorDelTipoCorrectoCumpleConLaValidacion() {

		obligatorio.asignarValor(4);

		assertTrue(obligatorio.esValido());

	}

	@Test
	void testObligatorioConValorDelTipoEquivocadoNoCumpleConLaValidacion() {

		obligatorio.asignarValor("cuatro");

		assertFalse(obligatorio.esValido());

	}

	@Test
	void testOpcionalSinValorCumpleConLaValidacionSinConsultarAlTipo() {

		assertTrue(opcional.esValido());

		verify(tipo, never()).esValorValido(any());

	}

	@Test
	void testOpcionalConValorDelTipoCorrectoCumpleConLaValidacion() {

		opcional.asignarValor(4);

		assertTrue(opcional.esValido());

	}

	@Test
	void testOpcionalConValorDelTipoEquivocadoNoCumpleConLaValidacion() {

		opcional.asignarValor("cuatro");

		assertFalse(opcional.esValido());

	}

}
