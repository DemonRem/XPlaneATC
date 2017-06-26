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

import os


class Config:


    plugin = False
    recordWidgetVisible = False
    requestWidgetVisible = False
    chartingWindowVisible = False
    
    isAirbourne = False


    
    @staticmethod
    def setPlugin(p):
        plugin = p

    @staticmethod
    def getPlugin():
        return plugin
    
    @staticmethod
    def getGroundProbeCount():
        return 2

    @staticmethod
    def getAuthor():
        return "Mirko Bubel"
    
    @staticmethod
    def getAppName():
        return "XPlane ATC Client"
    
    @staticmethod
    def getAppVersion():
        return "0.1"
    
    @staticmethod
    def getLastUpdated():
        return "5/30/2016 14:7" #REPLACEUPDATESTRING

    @staticmethod
    def getFullFilePath():


        systemPath = XPLMGetSystemPath()
        Config.debugMessage(systemPath)
        dirSep = XPLMGetDirectorySeparator()

        recordFilePath = "Resources" + dirSep + "plugins" + dirSep + "PythonScripts" + dirSep + "followMeCarScripts" + dirSep + "airportfiles"

        recordFullFilePath = systemPath + recordFilePath
        return recordFullFilePath

    @staticmethod
    def getRelFilePath():
        dirSep = XPLMGetDirectorySeparator()

        recordFilePath = "Resources" + dirSep + "plugins" + dirSep + "PythonScripts" + dirSep + "followMeCarScripts" + dirSep + "airportfiles"
        return recordFilePath

    @staticmethod
    def checkIfFilePathExists():
        if not os.path.exists(Config.getFullFilePath()):
            os.makedirs(Config.getFullFilePath())
            
    @staticmethod
    def checkIfRouteFilePathExists():
        if not os.path.exists(Config.getRouteFilesDir()):
            os.makedirs(Config.getRouteFilesDir())

  

    @staticmethod
    def doDebug():
        return True

    @staticmethod
    def debugMessage(msg):
        if (Config.doDebug()):
            XPLMDebugString(msg + "\n")

    @staticmethod
    def getSystemPath():
        return XPLMGetSystemPath()

    @staticmethod
    def getDirSep():
        return XPLMGetDirectorySeparator()

    @staticmethod
    def getPythonScriptsDir():
        dirSep = XPLMGetDirectorySeparator()
        return Config.getSystemPath() + dirSep + "Resources" + dirSep + "plugins" + dirSep + "PythonScripts"

    @staticmethod
    def getXHSScriptsDir():
        dirSep = XPLMGetDirectorySeparator()
        return Config.getSystemPath() + "Resources" + dirSep + "plugins" + dirSep + "PythonScripts" + dirSep + "XHSClient"

  

