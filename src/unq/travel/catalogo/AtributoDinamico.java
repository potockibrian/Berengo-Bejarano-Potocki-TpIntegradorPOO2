package unq.travel.catalogo;

import java.util.List;

public class AtributoDinamico {

	private String nombre;
	private boolean obligatorio;
	private TipoAtributo tipo;
	private Object valor;

	public AtributoDinamico(String nombre, boolean obligatorio, TipoAtributo tipo, Object valor) {

		this.obligatorio = obligatorio;
		this.tipo = tipo;
		
		validarValor(valor);
		
		this.nombre = nombre;
		this.valor = valor;

	}

	public boolean tieneNombre(String nombre) {

		return this.nombre.equals(nombre);

	}

	public void asignarValor(Object valor) {

		validarValor(valor);
			
		this.valor = valor;

	}

	private void validarValor(Object valor) {
		
		try { 
			
			if(obligatorio && valor == null) throw new Exception();
			tipo.esValorValido(valor); 
		
		} catch(Exception e) { 
		
			throw new ValorDeAtributoInvalidoException(); 
		
		}
		
	}

	public Object getValor() {

		if (!this.tieneValor()) throw new AtributoSinValorException("El atributo " + nombre + " no tiene valor asignado");
		
		return valor;

	}

	public boolean tieneValor() {

		return valor != null;

	}

	/*
	public boolean esValido() {
			
		return !obligatorio || valor != null;
		
	}
	*/

}
