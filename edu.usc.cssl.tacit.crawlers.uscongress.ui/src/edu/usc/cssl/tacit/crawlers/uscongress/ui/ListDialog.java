package edu.usc.cssl.tacit.crawlers.uscongress.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Shell;

import edu.usc.cssl.tacit.crawlers.uscongress.ui.internal.ElementListSelectionDialog;

public class ListDialog extends ElementListSelectionDialog {

	public ListDialog(Shell parent, ILabelProvider renderer) {
		super(parent, renderer);
	}
	
	public void refresh(Object[] elements){
		setListElements(elements);
	}
}
