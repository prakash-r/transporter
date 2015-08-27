package com.transporter.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;

public abstract class FileEditorWrapper extends FieldEditorPreferencePage implements IWorkbenchPropertyPage {

	
	private IPreferenceStore overlayStore;
	private IAdaptable element;
	
	public FileEditorWrapper(int style) {
		super(style);
	}
	
	protected abstract String getPageId();
	
	@Override
	public IAdaptable getElement() {
		return element;
	}

	@Override
	public void setElement(IAdaptable element) {
		this.element = element;
	}
	
	@Override
	protected void addField(FieldEditor editor) {
		super.addField(editor);
	}
	
	@Override
	public void createControl(Composite parent) {
		overlayStore = new PropertyStore(
							(IResource) getElement(), 
							super.getPreferenceStore(), 
							getPageId());
		overlayStore.addPropertyChangeListener(new IPropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				
			}
		});
		super.createControl(parent);
	}
	
	@Override
	public IPreferenceStore getPreferenceStore() {
		return overlayStore;
	}

}
