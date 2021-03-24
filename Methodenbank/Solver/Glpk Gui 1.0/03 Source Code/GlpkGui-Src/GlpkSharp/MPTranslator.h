#pragma once

extern "C" {
#include "glpk.h"

}

using namespace System;
using namespace System::Runtime::InteropServices;
using namespace System::IO;

namespace GlpkSharp {
	public enum class SOLUTIONTYPE{
		BasicSolution=GLP_SOL,
		InteriorPoint=GLP_IPT,
		MIP=GLP_MIP
	};
}
