package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Sesiones {
	private int capacidad;
	private int tamano;
	private Sesion [] coleccionSesiones;
	
	public Sesiones(int capacidad){
		if(capacidad<=0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionSesiones = new Sesion[capacidad];
		this.capacidad=capacidad;
		this.tamano=0;
		//Creo el array de citas con los objetos cita de longitud capacidad.
		//Inicializo tamano(cantidad de citas guardadas) a 0 y la capacidad la inicializo al CAPACIDAD 
		//que coincide con el parametro pasado capacidad
	}
	
	public Sesion[] get() {
		return copiaProfundaSesiones();
	}
	
	private Sesion [] copiaProfundaSesiones() {
		Sesion[] coleccionCopia= new Sesion[tamano];
		for (int i=0; i<tamano;i++) {
			coleccionCopia[i]= new Sesion(coleccionSesiones[i]);
		}
		return coleccionCopia;
	}
	
	public Sesion[] get(Tutoria tutoria) {
		Sesion[] coleccionCopia= new Sesion[tamano];
		for (int i=0; i<tamano;i++) {
			if(coleccionSesiones[i].getTutoria().equals(tutoria)){
				coleccionCopia[i]= new Sesion(coleccionSesiones[i]);
			}
		}
		return coleccionCopia;
	}
	public int getTamano() {
		return tamano;
	}
	
	public int getCapacidad() {
		return capacidad;
	}

	public void insertar(Sesion sesion) throws OperationNotSupportedException{
		if (sesion==null) {
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}
		if(buscar(sesion)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe una sesión con esa fecha.");
		}
		
		if (capacidadSuperada(tamano)==true) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más sesiones.");
		}else {
			coleccionSesiones[tamano]=new Sesion(sesion); 
			System.out.println("Sesion introducida correctamente.");
			tamano++;
		}		
	}
	
	private int buscarIndice(Sesion sesion) {
		int indice=tamano+1; //se inicializa a tamano+1 para el caso en que no se encuentre indice.
		for (int i=0; i<tamano;i++) {
			if (coleccionSesiones[i].equals(sesion)){ 
				//solo se comparan las fechas obviando los nombres.
				//se utiliza el método equals para el array ya que es un array de objetos
				indice=i;
			}
		}
		return indice ;
	}
	
	private boolean tamanoSuperado (int nuevoTamano) {
		boolean superado=false;
		if (nuevoTamano>=tamano) {
			superado=true;
		}
		return superado;
	}
	
	
	private boolean capacidadSuperada(int tamano) {
		boolean superado=false;
		if (tamano>=capacidad) {
			superado=true;
		}
		return superado;
	}
	
	public Sesion buscar(Sesion sesion) {
		if(sesion==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una sesión nula.");
		}
		int indice;
		Sesion encontrado = null; //si no encuentra cita este método devuelve null.
		indice=buscarIndice(sesion);
		if (!tamanoSuperado(indice)) {
			encontrado=new Sesion(coleccionSesiones[indice]);//obtengo una copia de la cita
		}

		return encontrado; //devuelvo una copia de la cita encontrada
	}

	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una sesión nula.");
		}
		int indice;
		indice=buscarIndice(sesion);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
			System.out.println("Sesion borrada correctamente.");
			tamano--;
		}else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna sesión con esa fecha.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i=0;i<tamano;i++) { 
			if (i>indice){
				coleccionSesiones[i-1]=coleccionSesiones[i];
			}
		}
		coleccionSesiones[i]=null;//al último objeto vacío del array le asocio null para saber que está vacío
	}
}
