package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;

class CriterioNombreTest {

	private ItemCatalogo item;

	@BeforeEach
	void setUp() {
		item = mock(ItemCatalogo.class);
		when(item.getNombre()).thenReturn("Noche de Hotel Central");
	}

	@Test
	void testCumpleCuandoElTextoEsElNombreCompleto() {

		Criterio criterio = new CriterioNombre("Noche de Hotel Central");

		assertTrue(criterio.cumpleConCriterio(item));

	}

	@Test
	void testCumpleCuandoElNombreContieneElTexto() {

		Criterio criterio = new CriterioNombre("Hotel");

		assertTrue(criterio.cumpleConCriterio(item));

	}

	@Test
	void testCumpleSinDistinguirMayusculasNiMinusculas() {

		Criterio criterioEnMinusculas = new CriterioNombre("hotel central");
		Criterio criterioEnMayusculas = new CriterioNombre("NOCHE DE HOTEL");

		assertTrue(criterioEnMinusculas.cumpleConCriterio(item));
		assertTrue(criterioEnMayusculas.cumpleConCriterio(item));

	}

	@Test
	void testNoCumpleCuandoElNombreNoContieneElTexto() {

		Criterio criterio = new CriterioNombre("cabana");

		assertFalse(criterio.cumpleConCriterio(item));

	}

}
