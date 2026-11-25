package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BolsaDeProveedoresTest {

	private String proveedor1 = "proveedor1";
	private String proveedor2 = "proveedor2";
	
	private BolsaDeProveedores bolsa1;
	private BolsaDeProveedores bolsa2;
	private BolsaDeProveedores bolsaIdentidad;
	private Map<String, Integer> proveedores1;
	private Map<String, Integer> proveedores2;
	
	@BeforeEach
	void setUp() throws Exception {
	
		//proveedores1 = new HashMap<>();
		proveedores2 = new HashMap<>();
	
		//proveedores1.put(proveedor1, 10);
		
		proveedores2.put(proveedor1, 7);
		proveedores2.put(proveedor2, 5);
		
		bolsa1 = new BolsaDeProveedores(proveedor1);
		bolsa2 = new BolsaDeProveedores(proveedores2);
		bolsaIdentidad = new BolsaDeProveedores();

		for(int i=0; i<10; i++) bolsa1.agregarProveedor(proveedor1);
		
	}

	@Test
	void testCantidadTotalServicios() {

		assertEquals(11, bolsa1.cantidadTotalServicios());
		assertEquals(7+5, bolsa2.cantidadTotalServicios());
		
	}
	
	@Test
	void testProveedores() {
		
		assertEquals(List.of("proveedor1"), bolsa1.proveedores());
		assertEquals(List.of("proveedor2", "proveedor1"), bolsa2.proveedores());
		
	}
	
	@Test
	void testOcurrenciasDeProveedor() {
		
		assertEquals(11, bolsa1.ocurrenciasDeProveedor(proveedor1));
		assertEquals(0, bolsa1.ocurrenciasDeProveedor(proveedor2));
		assertEquals(7, bolsa2.ocurrenciasDeProveedor(proveedor1));
		assertEquals(5, bolsa2.ocurrenciasDeProveedor(proveedor2));
		
	}
	
	@Test
	void testCantidadDeProveedores() {
		
		assertEquals(1, bolsa1.cantidadDeProveedores());
		assertEquals(2, bolsa2.cantidadDeProveedores());
		
	}
	
	@Test
	void testCombinarConBolsaDeProveedores() {
		
		BolsaDeProveedores bolsa = bolsa1.combinarConBolsaDeProveedores(bolsa2);

		assertEquals(11+7+5, bolsa.cantidadTotalServicios());
		assertEquals(List.of("proveedor2", "proveedor1"), bolsa.proveedores());
		assertEquals(11+7, bolsa.ocurrenciasDeProveedor(proveedor1));
		assertEquals(5, bolsa.ocurrenciasDeProveedor(proveedor2));
		assertEquals(2, bolsa.cantidadDeProveedores());
		
	}
	
	@Test
	void testPorcentajePorPorProveedor() {
		
		assertEquals(100, bolsa1.getPorcentajePorProveedor(proveedor1));
		assertEquals(0, bolsa1.getPorcentajePorProveedor(proveedor2));
		assertEquals(58.333333333333336, bolsa2.getPorcentajePorProveedor(proveedor1));
		assertEquals(41.66666666666667, bolsa2.getPorcentajePorProveedor(proveedor2));
		assertEquals(0, bolsaIdentidad.getPorcentajePorProveedor(proveedor1));
		
	}
	
	@Test
	void testPorcentajesPorPorProveedor() {
		
		Map<String, Double> porcentajes1 = bolsa1.getPorcentajesPorProveedor();
		Map<String, Double> porcentajes2 = bolsa2.getPorcentajesPorProveedor();
		
		assertEquals(100, porcentajes1.get(proveedor1));
		assertEquals(58.333333333333336, porcentajes2.get(proveedor1));
		assertEquals(41.66666666666667, porcentajes2.get(proveedor2));
		
	}

}
