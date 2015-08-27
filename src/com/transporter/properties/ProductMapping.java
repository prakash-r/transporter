package com.transporter.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

public class ProductMapping {
	
	private String source;
	private String dest;
	private String propKey;
	private boolean isEnabled = true; 
	public ProductMapping(String source, String dest, String propKey) {
		this.source = source;
		this.dest = dest;
		this.propKey = propKey;
	}
	
	public String getSource(){
		return source;
	}
	
	public String getDestination(){
		return dest;
	}
	public void markEnable(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	public boolean isEnabled(IProject project) throws CoreException{
		if(!isEnabled){
			return false;
		}
		String propVal = project.getPersistentProperty(new QualifiedName("TransporterPropPage", propKey));
		return Boolean.parseBoolean(propVal);
	}
	
}
