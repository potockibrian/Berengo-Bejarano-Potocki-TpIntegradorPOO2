package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.RangoDeFechas;

class ReporteExposicionProveedoresTest {

	private String proveedor1 = "proveedor1";
	private String proveedor2 = "proveedor2";
	
	private ReporteExposicionProveedores reporte;
	private RangoDeFechas periodo;
	private Catalogo catalogo;
	private BolsaDeProveedores bolsa1;
	private BolsaDeProveedores bolsa2;
	private ItemCatalogo item1;
	private ItemCatalogo item2;
	private Map<String, Integer> proveedores1;
	private Map<String, Integer> proveedores2;
	private Exportador exportador;
	
	@BeforeEach
	void setUp() throws Exception {
	
		exportador = mock(Exportador.class);
		
		proveedores1 = new HashMap<>();
		proveedores2 = new HashMap<>();
		
		proveedores1.put(proveedor1, 10);
		
		proveedores2.put(proveedor1, 7);
		proveedores2.put(proveedor2, 5);
		
		bolsa1 = new BolsaDeProveedores(proveedores1);
		bolsa2 = new BolsaDeProveedores(proveedores2);
		item1 = mock(ItemCatalogo.class);
		item2 = mock(ItemCatalogo.class);
		catalogo = mock(Catalogo.class);
		
		when(item1.proveedores()).thenReturn(bolsa1);
		when(item2.proveedores()).thenReturn(bolsa2);
		when(catalogo.getItems()).thenReturn(List.of(item2, item1));
		
		reporte = new ReporteExposicionProveedores(catalogo);
	
	}

	@Test
	void testPorcentajePorProveedor() {

		assertEquals(77.27272727272727, reporte.getPorcentajePorProveedor(proveedor1));
		assertEquals(22.727272727272727, reporte.getPorcentajePorProveedor(proveedor2));
		
	}

	@Test
	void testPorcentajesPorProveedor() {

		Map<String, Double> porcentajes = reporte.getPorcentajesPorProveedor();
		
		assertEquals(77.27272727272727, porcentajes.get(proveedor1));
		assertEquals(22.727272727272727, porcentajes.get(proveedor2));
		
	}

	@Test
	void testExportar() {

		when(exportador.exportar(reporte)).thenReturn("Exportado");
		
		assertEquals("Exportado", reporte.exportar(exportador));
		
	}

}
