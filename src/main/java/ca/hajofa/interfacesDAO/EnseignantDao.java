/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.hajofa.interfacesDAO;

import ca.hajofa.entites.Enseignant;
import java.util.List;


/**
 *
 * @author fatim
 */
public interface EnseignantDao {
    //operations crud
   public List<Enseignant> findAll();
    public List<Enseignant>findById(int id_prof);
    public Enseignant findByEmail(String email);
    public List<Enseignant>create(Enseignant prof);
    public boolean createEnseignant(Enseignant prof);
    public boolean update(String courriel, String motpass);
}
