package com.htwg.powerlp.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;

/**
 * @author Bastian Schoettle
 * 
 */
public class JInternalFrameWrapper implements MouseListener {

	private JInternalFrame interalFrame;
	private JComboBox<String> matrixOptions;
	private JComboBox<String> matrixOptionsInteger;

	/**
	 * @param interalFrame
	 * @param matrixOptions
	 * @param matrixOptionsInteger
	 */
	public JInternalFrameWrapper(InternalFrame frame) {
		super();
		this.interalFrame = frame.getInternalFrame();
		this.matrixOptions = frame.getOptionsMatrix();
		this.matrixOptionsInteger = frame.getOptionsInteger();
		this.matrixOptionsInteger.addMouseListener(this);
	}

	public JInternalFrame getInteralFrame() {
		return interalFrame;
	}

	public void setInteralFrame(JInternalFrame interalFrame) {
		this.interalFrame = interalFrame;
	}

	public JComboBox<String> getMatrixOptions() {
		return matrixOptions;
	}

	public void setMatrixOptions(JComboBox<String> matrixOptions) {
		this.matrixOptions = matrixOptions;
	}

	public JComboBox<String> getMatrixOptionsInteger() {
		return matrixOptionsInteger;
	}

	public void setMatrixOptionsInteger(JComboBox<String> matrixOptionsInteger) {
		this.matrixOptionsInteger = matrixOptionsInteger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println("clicked2");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
