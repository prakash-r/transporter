package com.transporter;


import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.transporter.Transport;

/**
 * The activator class controls the plug-in life cycle
 */
public class TransporterPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "Transporter"; //$NON-NLS-1$

	// The shared instance
	private static TransporterPlugin plugin;
	
	/**
	 * The constructor
	 */
	public TransporterPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	IWorkspace workspace;
	IResourceChangeListener listener;
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		workspace = ResourcesPlugin.getWorkspace();
		listener = new Transport();
		workspace.addResourceChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		workspace.removeResourceChangeListener(listener);
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TransporterPlugin getDefault() {
		return plugin;
	}

	 public static void log(String messageID) {
	        Status status = new Status(Status.ERROR, PLUGIN_ID, messageID);
	        getDefault().getLog().log(status);
	        if(getDefault().isDebugging()){
	            System.out.println(status);
	        }
	    }
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
}
