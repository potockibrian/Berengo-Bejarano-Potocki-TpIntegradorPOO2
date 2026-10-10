package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;

class CriterioOrTest {

	private ItemCatalogo item;
	private Criterio primerCriterio;
	private Criterio segundoCriterio;
	private Criterio criterioOr;

	@BeforeEach
	void setUp() {
		item = mock(ItemCatalogo.class);
		primerCriterio = mock(Criterio.class);
		segundoCriterio = mock(Criterio.class);
		criterioOr = new CriterioOr(primerCriterio, segundoCriterio);
	}

	@Test
	void testCumpleCuandoAmbosCriteriosCumplen() {

		when(primerCriterio.cumpleConCriterio(item)).thenReturn(true);
		when(segundoCriterio.cumpleConCriterio(item)).thenReturn(true);

		assertTrue(criterioOr.cumpleConCriterio(item));

	}

	@Test
	void testCumpleCuandoSoloCumpleElPrimerCriterio() {

		when(primerCriterio.cumpleConCriterio(item)).thenReturn(true);
		when(segundoCriterio.cumpleConCriterio(item)).thenReturn(false);

		assertTrue(criterioOr.cumpleConCriterio(item));

	}

	@Test
	void testCumpleCuandoSoloCumpleElSegundoCriterio() {

		when(primerCriterio.cumpleConCriterio(item)).thenReturn(false);
		when(segundoCriterio.cumpleConCriterio(item)).thenReturn(true);

		assertTrue(criterioOr.cumpleConCriterio(item));

	}

	@Test
	void testNoCumpleCuandoNingunCriterioCumple() {

		when(primerCriterio.cumpleConCriterio(item)).thenReturn(false);
		when(segundoCriterio.cumpleConCriterio(item)).thenReturn(false);

		assertFalse(criterioOr.cumpleConCriterio(item));

	}

}
