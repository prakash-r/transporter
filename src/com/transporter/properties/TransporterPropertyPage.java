package com.transporter.properties;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;

import com.transporter.TransporterPlugin;

public class TransporterPropertyPage extends FileEditorWrapper 
{

	
	public TransporterPropertyPage() {
		super(GRID);
	}



	@Override
	protected void createFieldEditors() {
		
		noDefaultAndApplyButton();
		
		DirectoryFieldEditor prodPath = new DirectoryFieldEditor(PropertyStore.PRODUCT_PATH, "&Build webapps folder:",
		        getFieldEditorParent());
		addField(prodPath);
	    addField(new BooleanFieldEditor(PropertyStore.ISROOTENABLE,
		        "&Transport appcreator", getFieldEditorParent()));
	    addField(new BooleanFieldEditor(PropertyStore.ISAPPBUILDERENABLE,
		        "&Transport zohocreator", getFieldEditorParent()));
	    addField(new BooleanFieldEditor(PropertyStore.ISAPPENABLE,
		        "&Transport live", getFieldEditorParent()));

	}


	@Override
	protected String getPageId() {
		return "TransporterPropPage";
	}

	
	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return TransporterPlugin.getDefault().getPreferenceStore();
	}
}