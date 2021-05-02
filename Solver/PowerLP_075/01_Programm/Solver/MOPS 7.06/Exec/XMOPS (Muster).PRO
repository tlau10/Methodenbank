  xoutsl = 1  xlptyp = 0     xmxmin = 15.0   



  xfnmps = '..\models\prodplan.mps' xminmx='max'

* xfnmps = '..\models\shell.mps'    xminmx = 'min'  xheutp = 0
c  xfnmps = '..\models\lseu.mps'     xminmx = 'min'  xlptyp = 0
* xfnmps = '..\models\xcase.mps'    xminmx = 'min'  xinfor = 4
C xfnmps = '..\models\aw70tp.mps' xminmx='min' xinfor=2 xlptyp=4 xheutp=0


c xiplpt = 1  ! dual simplex in B&B instead of primal  
c xlptyp = 2  ! dual simplex for solving the initial LP
c xlptyp = 4  ! interior point for solving the initial LP