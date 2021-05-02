//---------------------------------------------------------------------------

#ifndef lpmodellH
#define lpmodellH
#define FILEFORMAT_LPI   0
#define FILEFORMAT_XA_EQ 1
#define FILEFORMAT_MPS   2
class TMDIChild;
//---------------------------------------------------------------------------
typedef enum relation {EQUAL, LTEQUAL, STEQUAL, LARGER, SMALLER} relation;
//---------------------------------------------------------------------------
/**
 *Klasse zur Erzeugung von Fehlermeldungen
 */
class ThrowErrorMsg {
  public:
  virtual void __fastcall PutError(char* msg)  {};
};
#define MAXVARNAME 30 // Maximale Lange eines Variablenbezeichners
//---------------------------------------------------------------------------
typedef char svarname[MAXVARNAME];
/**
 *Klasse die das LP-Modell, auf Basis von LP Data, erzeugt 
 */
class LPModell: public ThrowErrorMsg {
  float** res;     // Restriktionen
  float* bvec;     // B-Vektor
  float* zf;       // Zielfunktion
  svarname* resname; // Name der Restriktion
  char zfname[MAXVARNAME];
  svarname* varname; // Name der Variable
  relation* rels;  // Relation, <,=,<=,>=,>
  bool* integer;   // Ganzzahl
  float* lbound;   // Grenze unten
  float* hbound;   // Grenze oben
  int vars;
  int ress;
  int cvars;
  int cress;
  bool minimize;
  ThrowErrorMsg* Owner;
  void __fastcall delcs(int respos);
public:
  void __fastcall setMinimize(bool minimize);
  bool __fastcall getMinimize();
  float __fastcall getZF(int varno);
  void __fastcall setZF(int varno, float value);
  float __fastcall getRes(int varno, int resno);
  void __fastcall setResName(int resno, char* name);
  int __fastcall getResNameIX(char* name);
  void __fastcall setVarName(int varno, char* name);
  int __fastcall getVarNameIX(char* name);
  void __fastcall setZFName(char* name);
  char* __fastcall getZFName(char* name = NULL);
  bool __fastcall isZFName(char* name);
  void __fastcall setSize(int pvars, int prestr);
  int __fastcall getVarCount();
  int __fastcall getResCount();
  void __fastcall setRes(int varno, int resno, float value);
  float __fastcall getBV(int resno);
  void __fastcall setBV(int resno, float value);
  void __fastcall setInteger(int varno, bool value);
  bool __fastcall getInteger(int varno);
  void __fastcall setBounds(int varno, float lbound, float hbound);
  float __fastcall getLBound(int varno);
  float __fastcall getHBound(int varno);
  relation __fastcall getRel(int resno);
  void __fastcall setRel(int resno, relation value);
  virtual void __fastcall PutError(char* str);
  void __fastcall getDual(LPModell* Dual);
  void __fastcall clone(LPModell* Clone);
  //void __fastcall BoundsToRes();
  void __fastcall ResToBounds();
  bool __fastcall isInt();
  bool __fastcall isIntN();
  bool __fastcall isMixedMode();
  bool __fastcall hasIntNoHBound();
  void __fastcall splitIntN();
  void __fastcall clear();
  void __fastcall RemoveDuplicateRes();
  __fastcall LPModell(ThrowErrorMsg* Owner);
  __fastcall ~LPModell();
};

int __fastcall OpenCreateFile(ThrowErrorMsg* emsg, char* filename);
int __fastcall GetLPFromFile(LPModell* LPM, char* filename);
bool __fastcall PutLPtoFile(LPModell* LPM, char* filename, int format);
#endif
