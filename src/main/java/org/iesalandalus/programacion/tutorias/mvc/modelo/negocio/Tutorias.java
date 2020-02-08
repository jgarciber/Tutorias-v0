package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Tutorias {

	private int capacidad;
	private int tamano;
	private Tutoria [] coleccionTutorias;
	
	public Tutorias(int capacidad){
		if(capacidad<=0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionTutorias = new Tutoria[capacidad];
		this.capacidad=capacidad;
		this.tamano=0;
		//Creo el array de citas con los objetos cita de longitud capacidad.
		//Inicializo tamano(cantidad de citas guardadas) a 0 y la capacidad la inicializo al CAPACIDAD 
		//que coincide con el parametro pasado capacidad
	}
	
	
	public Tutoria[] get() {
		return copiaProfundaTutorias();
	}
	
	private Tutoria [] copiaProfundaTutorias() {
		Tutoria[] coleccionCopia= new Tutoria[capacidad];
		for (int i=0; i<tamano;i++) {
			coleccionCopia[i]= new Tutoria(coleccionTutorias[i]);
		}
		return coleccionCopia;
	}
	
	public Tutoria[] get(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		Tutoria[] coleccionCopia= new Tutoria[capacidad];
		for (int i=0; i<tamano;i++) {
			if(coleccionTutorias[i].getProfesor().equals(profesor)){
				coleccionCopia[i]= new Tutoria(coleccionTutorias[i]);
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

	public void insertar(Tutoria tutoria) throws OperationNotSupportedException{
		if (tutoria==null) {
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}
		//Buscamos si ya existe la cita.
		if(buscar(tutoria)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe una tutoría con ese identificador.");
		}
		
		if (capacidadSuperada(tamano)==true) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más tutorías.");
		}else {
			coleccionTutorias[tamano]=new Tutoria(tutoria); 
			System.out.println("Tutoría introducida correctamente.");
			tamano++;
		}		
	}
	
	private int buscarIndice(Tutoria tutoria) {
		int indice=tamano+1; //se inicializa a tamano+1 para el caso en que no se encuentre indice.
		for (int i=0; i<tamano;i++) {
			if (coleccionTutorias[i].equals(tutoria)){ 
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
	
	public Tutoria buscar(Tutoria tutoria) {
		if(tutoria==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una tutoría nula.");
		}
		int indice;
		Tutoria encontrado = null; //si no encuentra cita este método devuelve null.
		indice=buscarIndice(tutoria);
		if (!tamanoSuperado(indice)) {
			encontrado=new Tutoria(coleccionTutorias[indice]);//obtengo una copia de la cita
		}

		return encontrado; //devuelvo una copia de la cita encontrada
	}

	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una tutoría nula.");
		}
		int indice;
		indice=buscarIndice(tutoria);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
			System.out.println("tutoría borrada correctamente.");
			tamano--;
		}else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna tutoría con ese identificador.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i=0;i<tamano;i++) { 
			if (i>indice){
				coleccionTutorias[i-1]=coleccionTutorias[i];
			}
		}
		coleccionTutorias[i]=null;//al último objeto vacío del array le asocio null para saber que está vacío
	}
}

