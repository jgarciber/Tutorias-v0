package org.iesalandalus.programacion.tutorias.mvc.modelo;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Profesores;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Citas;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Sesiones;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Tutorias;


public class Modelo {

	private static final int CAPACIDAD = 50;
	
	private Profesores profesores;
	private Tutorias tutorias;
	private Sesiones sesiones;
	private Citas citas;
	private Alumnos alumnos;
	
	public Modelo() {
		profesores = new Profesores(CAPACIDAD);
		tutorias = new Tutorias(CAPACIDAD);
		sesiones = new Sesiones(CAPACIDAD);
		citas = new Citas(CAPACIDAD);
		alumnos = new Alumnos(CAPACIDAD);
	}
	
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		alumnos.insertar(alumno);
	}
	
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}
	
	public void insertar(Tutoria tutoria) throws OperationNotSupportedException {
		//compruebo que el profesor existe ya que la tutoria no podrá ser creada si el profesor no existe
		//lo hago desde el modelo ya que es la clase que reune todos lo métodos buscar del negocio, no se podría implementar esta
		//condición dentro de la clase Tutorias ya que carece del método buscar por profesor.
		if(buscar(tutoria.getProfesor())==null){
			throw new OperationNotSupportedException("El profesor no existe.");
		}
		
		//Creo una nueva tutoria con el resultado de la busqueda del profesor que dovolverá un profesor si existe con todos sus datos, y por otro lado el nombre de la tutoria
		Tutoria tutoria2= new Tutoria(buscar(tutoria.getProfesor()),tutoria.getNombre());
		tutorias.insertar(tutoria2);
	}
	
	public void insertar(Sesion sesion) throws OperationNotSupportedException{
		//Si la tutoría existe entonces también existe el profesor ya que la tutoría se compone de un profesor entre otros parametros
		if(buscar(sesion.getTutoria())==null){
			throw new OperationNotSupportedException("La tutoría no existe.");
		}
		//Creo una nueva sesion con el resultado de la busqueda de la tutoría que dovolverá una tutoría si existe con todos sus datos, además de la fecha, horaInicio, horaFin y los minutos de duración de la sesión

		Sesion sesion2= new Sesion(buscar(sesion.getTutoria()),sesion.getFecha(),sesion.getHoraInicio(),sesion.getHoraFin(),sesion.getMinutosDuracion());
		sesiones.insertar(sesion2);
	}
	
	public void insertar(Cita cita) throws OperationNotSupportedException  {
		if(buscar(cita.getAlumno())==null){
			throw new OperationNotSupportedException("El alumno no existe.");
		}
		if(buscar(cita.getSesion())==null) {
			throw new OperationNotSupportedException("La sesión no existe.");

		}
		//Creo una nueva cita con el resultado de la busqueda del alumno que dovolverá un alumno si existe con todos sus datos, el resultado de la búsqueda de la sesión que devolverá una sesión si existe con todos sus datos, ademaás de la hora de la cita

		Cita cita2= new Cita(buscar(cita.getAlumno()),buscar(cita.getSesion()),cita.getHora());
		citas.insertar(cita2);
	}
	
	
	
	public Alumno buscar(Alumno alumno) {
		//La Vista ya se encarga de mostrar el resultado, haya encontrodo o no el alumno. En caso de no haberlo encontrado se pasa un null que se interpreta en la Vista
		return alumnos.buscar(alumno);
	}
	
	public Profesor buscar(Profesor profesor) {
		return profesores.buscar(profesor);
	}
	
	public Tutoria buscar(Tutoria tutoria) {
		return tutorias.buscar(tutoria);
	}
	
	public Sesion buscar(Sesion sesion) {
		return sesiones.buscar(sesion);
	}
	
	public Cita buscar(Cita cita) {
		return citas.buscar(cita);
	}
	
	
	
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if(getCitas(alumno)!=null){
			throw new OperationNotSupportedException("No se puede borrar el alumno ya que tiene una o más citas asociadas.");
		} 
		alumnos.borrar(alumno);
	}
	
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if(getTutorias(profesor)!=null){
			throw new OperationNotSupportedException("No se puede borrar el profesor ya que tiene una o más tutorías asociadas.");
		}
		profesores.borrar(profesor);
	}
	
	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		if(getSesiones(tutoria)!=null){
			throw new OperationNotSupportedException("No se puede borrar la tutoría ya que tiene una o más sesiones asociadas.");
		}
		tutorias.borrar(tutoria);
	}
	
	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		if(getCitas(sesion)!=null){
			throw new OperationNotSupportedException("No se puede borrar la sesión ya que tiene una o más citas asociadas.");
		}
		sesiones.borrar(sesion);
	}
	
	public void borrar(Cita cita) throws OperationNotSupportedException {
		citas.borrar(cita);
	}
	
	
	
	public Alumno[] getAlumnos() {
		return alumnos.get();
	}
	
	public Profesor[] getProfesores() {
		return profesores.get();
	}
	
	public Tutoria[] getTutorias() {
		return tutorias.get();
	}
	
	public Tutoria[] getTutorias(Profesor profesor) {
		return tutorias.get(profesor);
	}
	
	public Sesion[] getSesiones() {
		return sesiones.get();
	}
	
	public Sesion[] getSesiones(Tutoria tutoria) {
		return sesiones.get(tutoria);
	}
	
	public Cita[] getCitas() {
		return citas.get();
	}
	
	public Cita[] getCitas(Sesion sesion) {
		return citas.get(sesion);
	}
	
	public Cita[] getCitas(Alumno alumno) {
		return citas.get(alumno);
	}
	
	
	

}

