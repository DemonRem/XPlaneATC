'''
X-Plane Follow Me Car for Community

Provides a animated Follow Me Car for Taxiways and Gates/Stands.
An Airport with all its taxiways and gates must be recoreded before the
FollowMeCar can be used.

Created by Mirko Bubel
---
This program is free software.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY. Feel free to modify, change or retribute it,
'''
execfile('Resources/plugins/PythonScripts/XHSClient/MenuManager.py')
execfile('Resources/plugins/PythonScripts/XHSClient/MainMenuCallback.py')



class XHSMainMenu:



    def __init__(self):

        self.menuManager = MenuManager()

        
        self.menuCB = MainMenuCallback(self.menuManager)
        self.menuManager.setMenuCallBack(self.menuCB,"Resources/plugins/PythonScripts/XHSClient/MainMenuCallback.py")

        self.menuManager.createMenu("XHSClient")
        self.menuManager.createSubMenuItem("Send")
        self.menuManager.createSubMenuItem("Stop")
        


