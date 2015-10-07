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
		
		
		addField(new BooleanFieldEditor(PropertyStore.ISPLUGINENABLE,
		        "&Enable Transporter", getFieldEditorParent()));
		DirectoryFieldEditor builderPath = new DirectoryFieldEditor(PropertyStore.PRODUCT_BUILDER, "&AdventNetBuilder webapps folder:",
		        getFieldEditorParent());
		addField(builderPath);
		
		DirectoryFieldEditor livePath = new DirectoryFieldEditor(PropertyStore.PRODUCT_LIVE, "&AdventNetLive webapps folder:",
		        getFieldEditorParent());
		addField(livePath);

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