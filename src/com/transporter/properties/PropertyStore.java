package com.transporter.properties;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;

public class PropertyStore extends PreferenceStore {
	
	
	public static final String PRODUCT_PATH = "productPath";
	public static final String ISAPPENABLE = "isAppEnable";
	public static final String ISROOTENABLE = "isRootEnable";
	public static final String ISAPPBUILDERENABLE = "isAppbuildeEnable";
	
	
	private IResource resource;
	private IPreferenceStore workbenchStore;
	private String pageId;
	private boolean inserting = false;
	
	public PropertyStore(IResource resource, IPreferenceStore workbenchStore, String pageId) {
		this.resource = resource;
		this.workbenchStore = workbenchStore;
		this.pageId = pageId;
	}
	
	@Override
	public String getDefaultString(String name) {
	  return workbenchStore.getDefaultString(name);
	}
	
	@Override
	public String getString(String name) {
	  insertValue(name, false);
	  return super.getString(name);
	}
	
	@Override
	public boolean getBoolean(String name) {
		insertValue(name, true);
		return super.getBoolean(name);
	}
	@Override
	public boolean getDefaultBoolean(String name) {
	  return workbenchStore.getDefaultBoolean(name);
	}
	
	private synchronized void insertValue(String name, boolean isBoolean) {
		if (inserting || super.contains(name))
		    return;
		inserting = true;
		String prop = null;
		try {
			prop = getProperty(name);
		} catch (CoreException e) {}
		if (prop == null){
			prop = workbenchStore.getString(name);
		}
		if (prop != null){
			if(isBoolean){
				setValue(name, Boolean.parseBoolean(prop));
			}else{
				setValue(name, prop);
			}
			
		}
		inserting = false;
	}
	private String getProperty(String name)  throws CoreException {
		return resource.getPersistentProperty(new QualifiedName(pageId, name));
	}
	@Override
	public boolean contains(String name) {
		return workbenchStore.contains(name);
	}
	
	@Override
	public void setToDefault(String name) {
		setValue(name, getDefaultString(name));
	}
	@Override
	public boolean isDefault(String name) {
		String defaultValue = getDefaultString(name);
		if (defaultValue == null) return false;
		return defaultValue.equals(getString(name));
	}
	
	@Override
	public void save() throws IOException {
		writeProperties();
	}
	@Override
	public void save(OutputStream out, String header) throws IOException {
		writeProperties();
	}
	
	private void writeProperties() throws IOException {
		String[] preferences = super.preferenceNames();
		for (int i = 0; i < preferences.length; i++) {
			String name = preferences[i];
			try {
				setProperty(name, getString(name));
			} catch (CoreException e) {
				throw new IOException("Cannot write resource property " + name);
			}
		}
	}
	private void setProperty(String name, String value) throws CoreException {
		resource.setPersistentProperty(new QualifiedName(pageId, name), value);
	}
}
