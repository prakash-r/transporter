# Transporter
Creator asynchronous file tansporter, an eclipse plugin assist to reduce the file synchronization between workspace and product. This plugin will transport configured files to development setup without changing the directory structure.
  
This is the beta version which works under normal scenario, with very basic use cases. 

#Installation

1. Download plugin jar from [here](#default_modal)
2. Copy the downloaded jar to eclipse plugins folder
3. Restart eclipse
4. Right click on project from Project Explorer and select Properties
5. Choose Tranporter Preference from left pane
6. Choose product webapps folder Ex : /Users/prakash/Build/Adv/Sas/webapps
7. Enable/Disable the following context
  - Transport appcreator to ROOT (Live mode old)
  - Transport zohocreator to appbuilder (Edit mode)
  - Transport live to app (Live mode new) 

Thats all, now your workspace and product will be at sync all time
