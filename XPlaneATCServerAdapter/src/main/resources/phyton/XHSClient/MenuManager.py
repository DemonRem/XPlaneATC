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

class MenuManager:

    def __init__(self):
        self.mainMenuID = False
        self.menuCallBack = False
        self.appendedMenuItem = False
        self.menuSubItemCounter = 0
        self.CB = False
        self.callBackClass = False
        self.callBackFile = False



    def setMenuCallBack(self,menuCB,fileName):
        self.callBackClass = menuCB
        self.callBackFile = fileName
        execfile(self.callBackFile)
        self.CB = self.innerMenuCB
        



    def createMenu(self, menuItemName):

        self.appendedMenuItem = XPLMAppendMenuItem(XPLMFindPluginsMenu(), menuItemName, 0, 1)
        self.mainMenuID = XPLMCreateMenu(ConfigContainer.getPlugin(), menuItemName, XPLMFindPluginsMenu(), self.appendedMenuItem, self.CB, 0)

    def createSubMenuItem(self, subItemName):

        self.menuSubItemCounter += 1
        subItem = XPLMAppendMenuItem(self.mainMenuID, subItemName, self.menuSubItemCounter, 1)



    def destroyMenu(self):

        XPLMDestroyMenu(ConfigContainer.getPlugin,self.mainMenuID)


    def innerMenuCB(self,menuRef, menuItem):
        return self.callBackClass.CB(menuRef,menuItem)
