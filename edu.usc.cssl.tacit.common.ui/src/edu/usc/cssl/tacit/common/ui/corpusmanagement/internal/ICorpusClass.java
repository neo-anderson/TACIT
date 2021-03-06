package edu.usc.cssl.tacit.common.ui.corpusmanagement.internal;

import edu.usc.cssl.tacit.common.ui.corpusmanagement.services.Corpus;


public interface ICorpusClass {
	public String getClassName();
	
	public Long getNoOfFiles();
	
	public Long getNoOfCases();
	
	public String getClassPath();
	
	public void setClassPath(String path);
	
	public String getTacitLocation();
	
	public Corpus getParent();
}
