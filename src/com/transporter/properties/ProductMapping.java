package com.transporter.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

public class ProductMapping {
	
	public static final int PRODUCT_BUILDER = 1;
	public static final int PRODUCT_LIVE = 2;
	
	private String source;
	private int	dest;
	public ProductMapping(String source, int dest) {
		this.source = source;
		this.dest = dest;
	}
	
	public String getSource(){
		return source;
	}
	
	public String getDestination(IProject project) throws CoreException{
		String propKey = getPropKey();
		if(propKey != null){
			String propVal = project.getPersistentProperty(new QualifiedName("TransporterPropPage", propKey));
			return propVal;
		}
		return null;
	}
	private String getPropKey(){
		switch (dest) {
		case PRODUCT_BUILDER:
			return PropertyStore.PRODUCT_BUILDER;
		case PRODUCT_LIVE:
			return PropertyStore.PRODUCT_LIVE;
		}
		return null;
	}
}
