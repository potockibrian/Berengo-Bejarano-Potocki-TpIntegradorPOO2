package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;

class CriterioAndTest {

	private ItemCatalogo item;
	private Criterio primerCriterio;
	private Criterio segundoCriterio;
	private Criterio criterioAnd;

	@BeforeEach
	void setUp() {
		item = mock(ItemCatalogo.class);
		primerCriterio = mock(Criterio.class);
		segundoCriterio = mock(Criterio.class);
		criterioAnd = new CriterioAnd(primerCriterio, segundoCriterio);
	}

	@Test
	void testCumpleCuandoAmbosCriteriosCumplen() {

		when(primerCriterio.cumpleConCriterio(item)).thenReturn(true);
		when(segundoCriterio.cumpleConCriterio(item)).thenReturn(true);

		assertTrue(criterioAnd.cumpleConCriterio(item));

	}

	@Test
	void testNoCumpleCuandoSoloCumpleElPrimerCriterio() {

		when(primerCriterio.cumpleConCriterio(item)).thenReturn(true);
		when(segundoCriterio.cumpleConCriterio(item)).thenReturn(false);

		assertFalse(criterioAnd.cumpleConCriterio(item));

	}

	@Test
	void testNoCumpleCuandoSoloCumpleElSegundoCriterio() {

		when(primerCriterio.cumpleConCriterio(item)).thenReturn(false);
		when(segundoCriterio.cumpleConCriterio(item)).thenReturn(true);

		assertFalse(criterioAnd.cumpleConCriterio(item));

	}

	@Test
	void testNoCumpleCuandoNingunCriterioCumple() {

		when(primerCriterio.cumpleConCriterio(item)).thenReturn(false);
		when(segundoCriterio.cumpleConCriterio(item)).thenReturn(false);

		assertFalse(criterioAnd.cumpleConCriterio(item));

	}

}
