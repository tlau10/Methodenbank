// STRING
void __fastcall strpncpy(char* dest, const char* src, unsigned int cnt);
unsigned int __fastcall strplen(const char* str);
char* __fastcall strreplace(char* str, const char* from, const char* to);
char* __fastcall trim(char* msg, char tc=' ');
// FILE
int __fastcall readLF(int file, char* buffer, long length);
int __fastcall writeLF(int file, char* str);
int __fastcall writeStr(int file, char* str);
void __fastcall CheckSlash(char* dir);

