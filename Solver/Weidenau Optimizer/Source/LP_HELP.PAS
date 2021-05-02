PROGRAM lp_help;

USES helpunit;

CONST help_='LP.WHL';
VAR he:help_type;
BEGIN
  he.help(help_,'',1);
END.