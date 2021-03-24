package mischungsplaner.model.solverCaller;

/**
 * Abstrakte Klasse. Verwaltet den Pfad auf eine Datei.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public abstract class Files implements IErrorMessage
{
  private String _pfad;

  /**
  * Konstruktor.
  * @param pfad Pfad zur Datei.
  */
  public Files(String pfad)
  {
    _pfad = pfad;
  }

  /**
  * Ermittelt den gesetzten Dateipfad.
  * @param pfad Pfad zur Datei (mit Namensangabe).
  */
  public String getFilePath()
  {
    return _pfad;
  }

  /**
  * Setzt den Pfad zur Datei.
  * @param pfad Dateipfad (mit Namensangabe).
  */
  public void setFilePath(String pfad)
  {
    _pfad = pfad;
  }
}