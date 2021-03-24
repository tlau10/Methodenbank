  xoutsl = 1  xlptyp = 0     xmxmin = 15.0   

* xfnmps = '..\models\burmaa.mps'   xminmx = 'MAX'
* xfnmps = '..\models\egout.mps'    xminmx = 'min'
* xfnmps = '..\models\fixnet3.mps'  xminmx = 'min'
* xfnmps = '..\models\khb05250.mps' xminmx = 'min'
* xfnmps = '..\models\p0291.mps'    xminmx = 'min'
* xfnmps = '..\models\food2.mps'    xminmx = 'max'
* xfnmps = '..\models\har2.mps'     xminmx = 'min'
* xfnmps = '..\models\gip.mps'      xminmx = 'max'
c xfnmps = '..\models\flugpl.mps'   xminmx = 'min'
c xfnmps = '..\models\ipc6.mps'     xminmx = 'min'  xheutp = 0
  xfnmps = '..\models\737.mps' xminmx='min' xnored=2 xmxpsu=15
* xfnmps = '..\models\shell.mps'    xminmx = 'min'  xheutp = 0
c  xfnmps = '..\models\lseu.mps'     xminmx = 'min'  xlptyp = 0
* xfnmps = '..\models\xcase.mps'    xminmx = 'min'  xinfor = 4
C xfnmps = '..\models\aw70tp.mps' xminmx='min' xinfor=2 xlptyp=4 xheutp=0


c xiplpt = 1  ! dual simplex in B&B instead of primal  
c xlptyp = 2  ! dual simplex for solving the initial LP
c xlptyp = 4  ! interior point for solving the initial LP