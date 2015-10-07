package com.transporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;

import com.transporter.properties.ProductMapping;
import com.transporter.properties.PropertyStore;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;

public class Transport implements IResourceChangeListener 
{

	public static List<ProductMapping> mappings = new ArrayList<ProductMapping>();
	
	public Transport() {
		
		mappings.add(new ProductMapping("appcreator",ProductMapping.PRODUCT_LIVE));
		mappings.add(new ProductMapping("zohocreator",ProductMapping.PRODUCT_BUILDER));
	}
	
	public static ProductMapping getMapping(String source){
		for(int i=0,l=mappings.size();i<l;i++){
			ProductMapping mapping = mappings.get(i);
			if(mapping.getSource().equals(source)){
				return mapping;
			}
		}
		ProductMapping dummyMap = new ProductMapping(null, 0);
		return dummyMap;
	}
	
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() != IResourceChangeEvent.POST_CHANGE){
			return;
		}
		
		IResourceDelta rootDelta = event.getDelta();
		try {
			rootDelta.accept(new TransportVisitor());
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
	}
	

}

class TransportVisitor implements IResourceDeltaVisitor
{
	IWorkspace workspace = ResourcesPlugin.getWorkspace();
	public TransportVisitor() {
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {

        if (delta.getKind() != IResourceDelta.CHANGED){
        	return true;
        }
        if ((delta.getFlags() & IResourceDelta.CONTENT) == 0){
        	return true;
        }
        
        IResource resource = delta.getResource();
        if(resource.getProjectRelativePath().segmentCount() < 3){
        	return true;
        }
        String contextName = resource.getProjectRelativePath().segment(2);
        ProductMapping mapping = Transport.getMapping(contextName);
        String productPath = mapping.getDestination(resource.getProject());
        if(productPath == null){
        	return true;
        }
        if (resource.getType() == IResource.FILE) {
        	
        	if(!isPluginEnabled(resource.getProject())){
        		return true;
        	}
        	
        	IPath target = new Path(mapping.getDestination(resource.getProject())).append("ROOT");
        	IPath rawPath = resource.getRawLocation();
        	IPathVariableManager pathManager = workspace.getPathVariableManager();
        	rawPath = pathManager.resolvePath(rawPath);
        	target = target.append(resource.getProjectRelativePath().removeFirstSegments(3));
        	File source = rawPath.toFile();
        	File destFile = target.toFile();
       	 
        	if(!destFile.isFile()){
        		File dir = destFile.getParentFile();
        		if (!dir.exists()) {
        			dir.mkdirs();
        		}
        		try {
        			destFile.createNewFile();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        	copy(source, destFile);
        }
        return true;
	}
	
	private Boolean isPluginEnabled(IProject project) throws CoreException{
		String isEnable = project.getPersistentProperty(new QualifiedName("TransporterPropPage", PropertyStore.ISPLUGINENABLE));
		return Boolean.parseBoolean(isEnable);
	}
	
	private boolean copy(File source, File destination){
   	 if (source == null || destination == null || !source.exists()
                || !destination.exists() || source.isDirectory()
                || destination.isDirectory()) {
   		 return false;
   	 }
   	 if(destination.lastModified() == source.lastModified()
               && destination.length() == source.length()){
   		 return true;
   	 }
   	 
   	 FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            fin = new FileInputStream(source);
            FileChannel in = fin.getChannel();
            FileChannel out;
            fout = new FileOutputStream(destination);
            out = fout.getChannel();
            long numbytes = in.size();
            out.transferFrom(in, 0, numbytes);
        } catch (IOException e) {
       	 e.printStackTrace();
        } finally {

            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
               	 e.printStackTrace();
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                    return destination.setLastModified(source.lastModified());
                } catch (IOException e) {
               	 e.printStackTrace();
                }
            }

        }
   	 return true;
   }
	
}