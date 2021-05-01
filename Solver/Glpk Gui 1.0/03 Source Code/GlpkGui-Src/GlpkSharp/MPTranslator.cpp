//#include "StdAfx.h"
#include "MPTranslator.h"
#include "GlpkSharp.h"

namespace GlpkSharp{
public ref class MPTranslator
{
	private:
		glp_tran* tran;

		char * AllocCharPointer(String^ s){
			char * sp=static_cast<char *>(Marshal::StringToHGlobalAnsi(s).ToPointer());
			return sp;
		}
		void FreeCharPointer(char * sp){
			Marshal::FreeHGlobal(safe_cast<IntPtr>(sp));
		}
	protected:
		!MPTranslator(){
			glp_mpl_free_wksp(tran);
		}

	public:
		MPTranslator()
		{
			tran=glp_mpl_alloc_wksp();
		}
		~MPTranslator(){
			glp_mpl_free_wksp(tran);
		}

		bool ReadModel(String^ fname,bool skipData){
			char * p= AllocCharPointer(fname);
			int i=glp_mpl_read_model(tran,p,skipData);
			FreeCharPointer(p);
			return i==0;
		}
		bool ReadData(String^ fname){
			char * p=AllocCharPointer(fname);
			int i=glp_mpl_read_data(tran,p);
			FreeCharPointer(p);
			return i==0;
		}

		bool GenerateModel(String^ fname){
			char * p=AllocCharPointer(fname);
			int i= glp_mpl_generate(tran,p);
			FreeCharPointer(p);
			return i==0;
		}
		LPProblem^ GenerateProblem(){
			LPProblem^ p=gcnew LPProblem();
			glp_mpl_build_prob(tran,p->prob);
			return p;
		}

		bool PostSolve(LPProblem^ p,SOLUTIONTYPE sol){
			int i=glp_mpl_postsolve(tran,p->prob,int(sol));
			return i==0;
		}
};

}
