package edu.usc.cssl.tacit.cluster.hierarchical.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class HeirarchicalClusterViewImageRegistry {

	ImageRegistry ir = new ImageRegistry();
	static HeirarchicalClusterViewImageRegistry imgIcon;

	public ImageDescriptor getImageDescriptor(String key) {
		return ir.getDescriptor(key);
	}

	private HeirarchicalClusterViewImageRegistry() {
		
		ir.put(IHeirarchicalClusterViewConstants.IMAGE_LRUN_OBJ, ImageDescriptor
				.createFromFile(HeirarchicalClusterViewImageRegistry.class, "/icons/lrun_obj.gif"));
		
		ir.put(IHeirarchicalClusterViewConstants.IMAGE_HELP_CO, ImageDescriptor
				.createFromFile(HeirarchicalClusterViewImageRegistry.class, "/icons/help_contents.gif"));

		ir.put(IHeirarchicalClusterViewConstants.IMAGE_HEIRARCHICAL_CLUSTERING_OBJ,
				ImageDescriptor.createFromFile(
						HeirarchicalClusterViewImageRegistry.class,
						"/icons/HeirarchicalClusteringIcon.png"));


	}

	public static HeirarchicalClusterViewImageRegistry getImageIconFactory() {
		if (imgIcon == null) {
			imgIcon = new HeirarchicalClusterViewImageRegistry();
		}
		return imgIcon;

	}

	public Image getImage(String imageName) {
		return ir.get(imageName);
	}

}
