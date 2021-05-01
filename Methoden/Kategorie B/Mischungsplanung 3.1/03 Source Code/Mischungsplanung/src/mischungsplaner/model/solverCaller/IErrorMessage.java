package mischungsplaner.model.solverCaller;

/**
 * Schnittstelle f�r die R�ckgabe von Fehlerbeschreibungen.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:
 * @author Helmut Lindinger
 * @version 1.0
 */
public interface IErrorMessage
{
  /**
  * Mit dieser Methode sollen Fehlertext ausgelesen werden.
  * @return Der Fehlertext
  */
  String getErrorMsg();
}