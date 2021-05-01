using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for ISolver.
	/// </summary>
	public abstract class  ISolver
	{
		public ISolver()
		{
		}
		/// <summary>
		///  erzeuge die MPS Datei für die Solver
		///  und lege diese Datei in C:\Temp bzw.
		///  voreingestelltes Arbeitsverzeichnis ab.
		/// </summary>
 		public abstract void createMPS( );

		/// <summary>
		///  starte den gewählten Solver...
		/// </summary>
		public abstract void solve( );
		
		/// <summary>
		///  lese die Ausgabedatei des Solvers aus
		///  ( bei LP-Solve: LP_SOLVE.OUT )
		///  und gebe die Lösung zurück
		/// </summary>
		/// <returns>AusgabeDaten</returns>
		public abstract AusgabeDaten readResults( );
	}
}
