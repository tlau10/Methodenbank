/*************************************************************************
 * 
 * CONFIDENTIAL
 * __________________
 * 
 *  [2016] Bastian Schoettle & Tim Schoettle
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Bastian Schoettle & Tim Schoettle and his suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Bastian Schoettle & Tim Schoettle
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Bastian Schoettle & Tim Schoettle.
 *
 */
package com.htwg.powerlp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.ImageIcon;

import com.htwg.powerlp.solver.SupportedSolver;

/**
 * @author schobast
 *
 */
/**
 * @author schobast
 *
 */
public class Configurator {

	/**
	 * 
	 */
	private static final String APPL_ICON_NAME = "icon.png";
	
	/**
	 * 
	 */
	private static final String RES_DIR= "powerlp.res_dir";
	/**
	 * 
	 */
	private static final String DARK_THEME_ENABLED = "powerlp.dark_theme_enabled";
	
	/**
	 * 
	 */
	private static final String DEFAULT_FILE = "powerlp.solver.default_file"; 
	/**
	 * 
	 */
	private static final String LP_SOLVE_EXE = "powerlp.solver.lp_solve_exe";
	/**
	 * 
	 */
	private static final String CFG_FILE_NAME = "powerlp.properties";
	/**
	 * 
	 */
	private static final String HELP_FILE_NAME = "powerlp.help_file";
	/**
	 * 
	 */
	private static final String SOLVER_CPLEX_ENABLED = "powerlp.solver.cplex_enabled";
	/**
	 * 
	 */
	private static final String SOLVER_LP_SOLVE_ENABLED = "powerlp.solver.lp_solve_enabled";
	/**
	 * 
	 */
	private static final String SOLVER_GUROBI_ENABLED = "powerlp.solver.gurobi_enabled";
	/**
	 * 
	 */
	private static final String SOLVER_GLPK_ENABLED = "powerlp.solver.glpk_enabled";
	/**
	 * 
	 */
	private static final String SOLVER_MOPS_ENABLED = "powerlp.solver.mops_enabled";
	/**
	 * 
	 */
	private static final String SOLVER_WEIDENAUER_ENABLED = "powerlp.solver.weidenauer_enabled";
	/**
	 * 
	 */
	private static final String SOLVER_CBC_ENABLED = "powerlp.solver.cbc_enabled";
	/**
	 * 
	 */
	private static final String WD_DYNAMIC_ENABLED = "powerlp.dynamic_wd_enabled";
	/**
	 * 
	 */
	private static final String WD_STATIC_DIR = "powerlp.static_wd_dir";
	/**
	 * 
	 */
	private static final String LICENSE_GUROBI = "powerlp.solver.gurobi_license";
	/**
	 * 
	 */
	private static final String LICENSE_CPLEX = "powerlp.solver.cplex_license";
	/**
	 * 
	 */
	private static final String LICENSE_MISSING_MSG = "powerlp.missing_license_message";
	/**
	 * 
	 */
	private static final String POWERLP_INFO_MSG = "powerlp.info_message";
	/**
	 * 
	 */
	private static final String SOLVER_MODEL_FILE = "powerlp.solver.model_file";
	/**
	 * 
	 */
	private static final String SOLVER_RESULT_FILE = "powerlp.solver.result_file";
	/**
	 * 
	 */
	private static final String SAVE_MPS_FORMAT = "powerlp.save_model_format";
	/**
	 * Execution directory
	 */
	private String execDir;
	/**
	 * 
	 */
	private String solverDir;
	/**
	 * 
	 */
	private String glpkDir;
	/**
	 * 
	 */
	private String gurobiDir;
	/**
	 * 
	 */
	private String cplexDir;
	/**
	 * 
	 */
	private String lpSolveDir;
	/**
	 * 
	 */
	private String mopsDir;
	/**
	 * 
	 */
	private String weidenauerDir;
	/**
	 * 
	 */
	private String cbcDir;
	/**
	 * 
	 */
	private Properties properties;

	/**
	 * 
	 */
	private boolean isGurobiLicenseValid;

	private Map<Integer, Integer> indexSolverMap;
	/**
	 * 
	 */
	private boolean isClplexLicenseValid;

	public Configurator(String cfgPath) {
		if (cfgPath == null) {
			cfgPath = CFG_FILE_NAME;
		}
		properties = new Properties();
		try {
			properties.load(CommonUtils.loadResourceAsStream(cfgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!isDynamicWdEnabled()) {
			execDir = getStaticWdDir();
		} else {
			execDir = System.getProperty("user.dir");
		}
		initEnv();
	}

	private void initEnv() {
		this.solverDir = execDir + "\\lib";
		this.glpkDir = solverDir + "\\" + SupportedSolver.GLPK.getSolver() + "\\";
		this.gurobiDir = solverDir + "\\" + SupportedSolver.GUROBI.getSolver() + "\\";
		this.cplexDir = solverDir + "\\" + SupportedSolver.CPLEX.getSolver() + "\\";
		this.lpSolveDir = solverDir + "\\" + SupportedSolver.LP_SOLVE.getSolver() + "\\";
		// this.mopsDir = solverDir + "\\" + SupportedSolver.MOPS.getSolver() +
		// "\\Exec\\";
		this.weidenauerDir = solverDir + "\\" + SupportedSolver.WEIDENAUER.getSolver() + "\\";
		this.cbcDir = solverDir + "\\" + SupportedSolver.CBC.getSolver() + "\\";
	}

	public String getSaveMpsFormat() {
		return properties.getProperty(SAVE_MPS_FORMAT);
	}
	
	public String getLPSolveExe() {
		return properties.getProperty(LP_SOLVE_EXE);
	}

	public boolean isLP_SolveEnabled() {
		return Boolean.parseBoolean(properties.getProperty(SOLVER_LP_SOLVE_ENABLED));
	}

	public boolean isILOG_CPLEXEnabled() {
		return Boolean.parseBoolean(properties.getProperty(SOLVER_CPLEX_ENABLED));
	}

	public boolean isGurobiEnabled() {
		return Boolean.parseBoolean(properties.getProperty(SOLVER_GUROBI_ENABLED));
	}
	
	public boolean isCBCEnabled() {
		return Boolean.parseBoolean(properties.getProperty(SOLVER_CBC_ENABLED));
	}

	public boolean isMOPSEnabled() {
		return Boolean.parseBoolean(properties.getProperty(SOLVER_MOPS_ENABLED));
	}

	public boolean isGLPKEnabled() {
		return Boolean.parseBoolean(properties.getProperty(SOLVER_GLPK_ENABLED));
	}

	public boolean isWeidenauerEnabled() {
		return Boolean.parseBoolean(properties.getProperty(SOLVER_WEIDENAUER_ENABLED));
	}

	public boolean isDynamicWdEnabled() {
		return Boolean.parseBoolean(properties.getProperty(WD_DYNAMIC_ENABLED));
	}

	
	
	/**
	 * @return
	 */
	public String getStaticWdDir() {
		return properties.getProperty(WD_STATIC_DIR);
	}

	/**
	 * @return help file name
	 */
	public String getHelpFileName() {
		return getResDir() + "\\" + properties.getProperty(HELP_FILE_NAME);
	}

	public String getGurobiLicenseFile() {
		return properties.getProperty(LICENSE_GUROBI);
	}

	public String getCplexLicenseFile() {
		return properties.getProperty(LICENSE_CPLEX);
	}

	public String getMissingLicenseMessage() {
		return properties.getProperty(LICENSE_MISSING_MSG);
	}

	public String getInfoMessage() {
		return properties.getProperty(POWERLP_INFO_MSG);
	}

	public String getModelFile() {
		return properties.getProperty(SOLVER_MODEL_FILE);
	}

	public String getResultFile() {
		return properties.getProperty(SOLVER_RESULT_FILE);
	}

	/**
	 * @return
	 */
	public String getExecutionDirectory() {
		return execDir;
	}

	/**
	 * @return
	 */
	public String getSolverDirectory() {
		return solverDir;
	}

	/**
	 * @return the execDir
	 */
	public String getExecDir() {
		return execDir;
	}

	/**
	 * @return the solverDir
	 */
	public String getSolverDir() {
		return solverDir;
	}

	/**
	 * @return the glpkDir
	 */
	public String getGlpkDir() {
		return glpkDir;
	}
	
	/**
	 * @return the glpkDir
	 */
	public String getCbcDir() {
		return cbcDir;
	}

	/**
	 * @return the gurobiDir
	 */
	public String getGurobiDir() {
		return gurobiDir;
	}

	/**
	 * @return the cplexDir
	 */
	public String getCplexDir() {
		return cplexDir;
	}

	/**
	 * @return the lpSolveDir
	 */
	public String getLpSolveDir() {
		return lpSolveDir;
	}

	/**
	 * @return the mopsDir
	 */
	public String getMopsDir() {
		return mopsDir;
	}

	/**
	 * @return the weidenauerDir
	 */
	public String getWeidenauerDir() {
		return weidenauerDir;
	}

	public boolean getGurobiLicenseEnabled() {
		return isGurobiLicenseValid;
	}

	public void setGurobiLicenseValid(boolean isGurobiLicenseValid) {
		this.isGurobiLicenseValid = isGurobiLicenseValid;
	}

	public boolean getCplexLicenseEnabled() {
		return isClplexLicenseValid;
	}

	public void setClplexLicenseValid(boolean isClplexLicenseValid) {
		this.isClplexLicenseValid = isClplexLicenseValid;
	}
	
	public String getDefaultFile(){
		return getResDir() + "\\" + this.properties.getProperty(DEFAULT_FILE);
	}
	
	public boolean isDarkThemeEnabled(){
		return Boolean.parseBoolean(properties.getProperty(DARK_THEME_ENABLED));
	}

	public void addIndexMapping(int key, int value) {
		if (indexSolverMap == null) {
			indexSolverMap = new HashMap<>();
		}
		indexSolverMap.put(key, value);
	}

	public Map<Integer, Integer> getIndexSolverMapping() {
		return indexSolverMap;
	}
	
	/**
	 * @return
	 */
	public String getResDir(){
		return getExecDir() + "\\" + properties.getProperty(RES_DIR);
	}
	
	public ImageIcon getApplicationIcon(){
		return CommonUtils.loadIcon(APPL_ICON_NAME);
	}
}
