package Simulation;

public class PersonenFabrik 
{
    public Person erzeuge(double eintritt, int idQ, int idZ, int idZH) 
    {        
        return new Person(eintritt, idQ, idZ, idZH);
    } 
}
