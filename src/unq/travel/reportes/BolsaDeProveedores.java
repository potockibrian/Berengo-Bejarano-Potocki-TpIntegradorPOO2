package unq.travel.reportes;

import java.util.*;

public class BolsaDeProveedores {

	private Map<String, Integer> proveedores;
	
	public BolsaDeProveedores() {
	
		 this.proveedores = new HashMap<>();
	
	}
	
	public BolsaDeProveedores(String proveedor) {
		
		 this.proveedores = new HashMap<>();
		 this.proveedores.put(proveedor, 1);
	
	}
	
	public BolsaDeProveedores(Map<String, Integer> proveedores) {
		
		this.proveedores = proveedores;
		
	}
	
	public int cantidadTotalServicios() {
		
		return this.proveedores.values().stream().mapToInt(v -> v).sum();
		
	}
	
	public void agregarProveedor(String proveedor) {
		
		proveedores.put(proveedor, proveedores.getOrDefault(proveedor, 0) + 1);
		
	}
	
	public List<String> proveedores() {
		
		return new ArrayList<String>(proveedores.keySet());
		
	}
	
	public Integer ocurrenciasDeProveedor(String proveedor) {
		
		return proveedores.getOrDefault(proveedor, 0);
		
	}
	
	public Integer cantidadDeProveedores() {
		
		return proveedores.size();
		
	}
	
	public BolsaDeProveedores combinarConBolsaDeProveedores(BolsaDeProveedores bolsa) {
		
		Map<String, Integer> nuevoMap = new HashMap<>(this.proveedores);
		
		// Agregar los proveedores del parámetro bolsa al map actual
		bolsa.proveedores().stream().forEach(p -> nuevoMap.put(p, nuevoMap.getOrDefault(p, 0) + bolsa.ocurrenciasDeProveedor(p)));
		
		// Retorna una BolsaDeProveedores nueva con el map generado anteriormente
		return new BolsaDeProveedores(nuevoMap);
		
	}
	
	public Map<String, Double> getPorcentajesPorProveedor() {
		
		Map<String, Double> porcentajes = new HashMap<String, Double>();
	
		for(String proveedor : proveedores.keySet()) {
	
			double porcentaje =	getPorcentajePorProveedor(proveedor);
		
			porcentajes.put(proveedor, porcentaje);
		
		}
		
		return porcentajes;
			
	}
	
	public double getPorcentajePorProveedor(String proveedor) {
		
		if(cantidadTotalServicios() == 0) return 0;
		
		int cantidadProveedor = proveedores.getOrDefault(proveedor, 0);
		
		return (double) cantidadProveedor / cantidadTotalServicios() * 100;
		
	}
	
}
