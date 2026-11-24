package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtributoDinamicoTest {

	private TipoAtributo tipo;
	private AtributoDinamico obligatorio;
	private AtributoDinamico opcional;
	private Object valor;

	@BeforeEach
	void setUp() {

		valor = 10;
		tipo = new TipoNumerico();

		obligatorio = new AtributoDinamico("CapacidadMaxima", true, tipo, valor);
		opcional = new AtributoDinamico("RatingEstrellas", false, tipo, valor);
	}

	@Test
	void testNoTieneValorHastaQueSeLeAsigna() {

		obligatorio.asignarValor(4);

		assertTrue(obligatorio.tieneValor());

	}

	@Test
	void testDevuelveElValorAsignado() {

		obligatorio.asignarValor(4);

		assertEquals(4, obligatorio.getValor());

	}

	/*
	@Test
	void testObligatorioSinValorNoCumpleConLaValidacion() throws Exception {
		
		setCampo(obligatorio, "valor", null);
		
		assertFalse(obligatorio.esValido());

	}
	*/

	/*
	@Test
	void testOpcionalSinValorCumpleConLaValidacion() throws Exception {
		
		setCampo(opcional, "valor", null);
		
		assertTrue(opcional.esValido());

	}
	*/
	
	/*
	@Test
	void testObligatorioConValorDelTipoCorrectoCumpleConLaValidacion() {

		obligatorio.asignarValor(4);

		assertTrue(obligatorio.esValido());

	}
	*/

	@Test
	void testObligatorioConValorDelTipoEquivocadoNoCumpleConLaValidacion() {

		assertThrows(ValorDeAtributoInvalidoException.class, () -> obligatorio.asignarValor("cuatro"));

	}

	/*
	@Test
	void testOpcionalSinValorCumpleConLaValidacionSinConsultarAlTipo() {

		assertTrue(opcional.esValido());

	}
	*/

	/*
	@Test
	void testOpcionalConValorDelTipoCorrectoCumpleConLaValidacion() {

		opcional.asignarValor(4);

		assertTrue(opcional.esValido());

	}
	*/

	@Test
	void testOpcionalConValorDelTipoEquivocadoNoCumpleConLaValidacion() {
		
		assertThrows(ValorDeAtributoInvalidoException.class, () -> opcional.asignarValor("cuatro"));
		
	}
	
	@Test
	void testConstruirUnaInstanciaConElValorInvalidoParaElTipoLevantaExcepcion() {
		
		assertThrows(ValorDeAtributoInvalidoException.class, () -> new AtributoDinamico("CapacidadMaxima", true, tipo, "valor invalido"));
		
	}
	
	@Test
	void testConstruirUnaInstanciaConElValorNullLevantaExcepcion() {
		
		assertThrows(ValorDeAtributoInvalidoException.class, () -> new AtributoDinamico("CapacidadMaxima", true, tipo, null));
		
	}
	
	@Test
	void testAsignarValorNullLevantaExcepcion() {
		
		assertThrows(ValorDeAtributoInvalidoException.class, () -> obligatorio.asignarValor(null));

		opcional.asignarValor(null);

		assertFalse(opcional.tieneValor());
		assertThrows(AtributoSinValorException.class, () -> opcional.getValor());
				
	}
	
	@Test
	void testAsignarValorInvalidoParaElTipoLevantaExcepcion() {
		
		assertThrows(ValorDeAtributoInvalidoException.class, () -> obligatorio.asignarValor("valor inválido"));
		assertThrows(ValorDeAtributoInvalidoException.class, () -> opcional.asignarValor("valor inválido"));
		
	}
	
	@Test
	void testSiElValorEsNullNoTieneValor() throws Exception {
		
		setCampo(obligatorio, "valor", null);
		setCampo(opcional, "valor", null);
		
		assertFalse(obligatorio.tieneValor());
		assertFalse(opcional.tieneValor());
		
	}
	
	@Test
	void testSiElValorEsNullObtenerElValorLevantaUnaExcepcion() throws Exception {
		
		setCampo(obligatorio, "valor", null);
		setCampo(opcional, "valor", null);
		
		assertThrows(AtributoSinValorException.class, () -> obligatorio.getValor());
		assertThrows(AtributoSinValorException.class, () -> opcional.getValor());
		
	}

	private static void setCampo(Object objeto, String nombre, Object valor) throws Exception {
		
		Field campo = objeto.getClass().getDeclaredField(nombre);
		campo.setAccessible(true);
		campo.set(objeto, valor);
		
	}

}
