# Transporter
Creator asynchronous file tansporter, an eclipse plugin assist to developer for file synchronization between workspace and product. This plugin will transport configured files to development setup without changing the directory structure.
  
This is the beta version which works under normal scenario, with very basic use cases. 

##Note
This is plugin is compatible for eclipse v3.5 or greater and under OS X platform

#Installation

1. Download plugin jar from [here](https://github.com/prakash-r/transporter/raw/master/build/Transporter_1.0.0.201508271746.jar)
2. Copy the downloaded jar to eclipse plugins folder
3. Restart eclipse
4. Right click on project from Project Explorer and select Properties
![screen shot 2015-08-27 at 7 15 02 pm](https://cloud.githubusercontent.com/assets/11437890/9543623/d646f27e-4d97-11e5-947c-56f8ccd79851.png)
5. Choose Tranporter Preference from left pane
![screen shot 2015-08-27 at 7 15 16 pm](https://cloud.githubusercontent.com/assets/11437890/9543624/d673036e-4d97-11e5-8337-a3fef5647238.png)
6. Select Build webapps folder Ex : /Users/prakash/Build/Adv/Sas/webapps
7. Enable/Disable the following context
  - Transport appcreator to ROOT (Live mode old)
  - Transport zohocreator to appbuilder (Edit mode)
  - Transport live to app (Live mode new) 

Thats all, now your workspace and product will be in sync

#Reference
- http://wiki.eclipse.org/PDE/User_Guide.
- http://www.vogella.com/tutorials/EclipsePlugIn/article.html.
- https://eclipse.org/articles/Article-Resource-deltas/resource-deltas.html.
- https://eclipse.org/articles/Article-PDE-does-plugins/PDE-intro.html.
- http://www.eclipse.org/articles/Article-Mutatis-mutandis/overlay-pages.html.
