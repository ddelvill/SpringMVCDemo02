package com.example.services;

import java.util.List;

import com.example.entities.Estudiante;

public interface EstudianteService {
    
    public List<Estudiante> findAll();
    public Estudiante findById(int idEstudiante);
    public void save(Estudiante estudiante);
    public void deleteById(int idEstudiante);
    public void delete(Estudiante estudiante);

    /**
     * No es necesario metodo update ya que lo realiza el save,
     * Inserta y actualiza en dependecia que el idEstudiante exista o no,
     * si no existe lo crea y si existe lo actualiza.
     * Estos metodos se pueden poner así porque estan en el CRUD REPOSITORY
     * implementados en el jpaRepository
     */


}
