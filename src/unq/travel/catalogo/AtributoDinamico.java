package unq.travel.catalogo;

import java.util.List;

public class AtributoDinamico {

	private String nombre;
	private boolean obligatorio;
	private TipoAtributo tipo;
	private Object valor;

	public AtributoDinamico(String nombre, boolean obligatorio, TipoAtributo tipo, Object valor) {

		try {
			
			List.of(nombre, obligatorio, tipo, valor);
			tipo.esValorValido(valor);
			
		} catch (Exception e) {
			
			throw new ValorDeAtributoInvalidoException();
			
		}
		
		this.nombre = nombre;
		this.obligatorio = obligatorio;
		this.tipo = tipo;
		this.valor = valor;

	}

	public boolean tieneNombre(String nombre) {

		return this.nombre.equals(nombre);

	}

	public void asignarValor(Object valor) {

		try { 
			
			tipo.esValorValido(valor); 
		
		} catch(Exception e) { 
		
			throw new ValorDeAtributoInvalidoException(); 
		
		}
		
		if (valor == null) throw new ValorDeAtributoInvalidoException();
			
		this.valor = valor;

	}

	public Object getValor() {

		if (!this.tieneValor()) throw new AtributoSinValorException("El atributo " + nombre + " no tiene valor asignado");
		
		return valor;

	}

	public boolean tieneValor() {

		return valor != null;

	}

	public boolean esValido() {
			
		return !obligatorio || valor != null;
		
	}

}
