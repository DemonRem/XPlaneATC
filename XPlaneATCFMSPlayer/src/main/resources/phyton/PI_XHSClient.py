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

from PythonScriptMessaging import *
from SandyBarbourUtilities import *
from XPLMCamera import *
from XPLMDataAccess import *
from XPLMDefs import *
from XPLMDisplay import *
from XPLMGraphics import *
from XPLMMenus import *
from XPLMNavigation import *
from XPLMPlanes import *
from XPLMPlugin import *
from XPLMProcessing import *
from XPLMScenery import *
from XPLMUtilities import *
from XPStandardWidgets import *
from XPWidgetDefs import *
from XPWidgets import *
import cPickle
from math import *
from os import path
from random import randint


execfile('Resources/plugins/PythonScripts/XHSClient/XHSMainMenu.py')
execfile('Resources/plugins/PythonScripts/XHSClient/ConfigContainer.py')

DEBUG = False

# False constants
__VERSION__ = 'alpha1'



class PythonInterface:
    def XPluginStart(self):

        self.Name = "XPlane Home Server ATC Client - " + __VERSION__
        self.Sig = "XHSClient.MBL.PI"
        self.Desc = "XPlane Home Server ATC Client"

        Config.setPlugin(self)
        ConfigContainer.setPlugin(self) #this is needed for library to work

        self.xhsMainMenu = XHSMainMenu()

        return self.Name, self.Sig, self.Desc

    def reset(self):
        pass

    def XPluginStop(self):
        pass

    def XPluginEnable(self):
        return 1

    def XPluginDisable(self):
        pass

    def XPluginReceiveMessage(self, inFromWho, inMessage, inParam):
        pass

