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
execfile('Resources/plugins/PythonScripts/XHSClient/Config.py')
execfile('Resources/plugins/PythonScripts/XHSClient/FCAircraft.py')
execfile('Resources/plugins/PythonScripts/XHSClient/MenuManager.py')
execfile('Resources/plugins/PythonScripts/XHSClient/PacketSender.py')
execfile('Resources/plugins/PythonScripts/XHSClient/FlightLoopManager.py')


from os import system

class MainMenuCallback:

    def __init__(self,menuManager):

        self.menuManager = menuManager
        self.flightLoopManager = FlightLoopManager()
        self.flCBFile = "Resources/plugins/PythonScripts/XHSClient/PacketSender.py"
        self.flCBClass = PacketSender()


    def CB(self,menuRef, menuItem):

        Config.debugMessage("ITEM Pressed: " + str(menuItem))
      
        if menuItem == 1:
            
            self.flightLoopManager.createFL(self.flCBClass,self.flCBFile)
            
            return 1
        
        elif menuItem == 2:
            self.flightLoopManager.destroyFL()
            return 1
        
        return 0