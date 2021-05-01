#pragma once

#include "BranchTree.h"

namespace GlpkSharp
{
		void BnCHook(glp_tree *tree, void *info);

		[UnmanagedFunctionPointer(CallingConvention::Cdecl)]
public delegate void BranchAndCutDelegate(BranchTree^ tree,Object^ o);

public ref class BnCCallback
{
public:
	void SetCallback(LPProblem^ p, BranchAndCutDelegate^ d);
};

}