//#include "StdAfx.h"
#include "BnCCallback.h"

namespace GlpkSharp{

	void BnCHook(glp_tree *tree, void *info){
		BranchAndCutDelegate^ d=
		(BranchAndCutDelegate^)Marshal::GetDelegateForFunctionPointer((IntPtr)(info),BranchAndCutDelegate::typeid);
		d(gcnew BranchTree(tree),nullptr);
	}

void BnCCallback::SetCallback(LPProblem^ p, BranchAndCutDelegate^ d)
{
	p->glpiocp->cb_func=BnCHook;
	p->glpiocp->cb_info=Marshal::GetFunctionPointerForDelegate(d).ToPointer();
}

}
