using System;
using PersplanLibrary;

namespace PersplanSolverInterface
{
	/// <summary>
	/// Zusammenfassung f�r Class1.
	/// </summary>
	public interface IPersplanSolver
	{
		bool InitSolver (string ConfigurationFile);
		bool Solve(ExtendedTreeNode RootNode);
		void GetSolution();
	}
}
