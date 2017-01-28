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
execfile('Resources/plugins/PythonScripts/XHSClient/ConfigContainer.py')
class FlightLoopManager:


    def __init__(self):

        self.flightLoopClass = False
        self.flightLoopFileName = False
        self.CB = False


    def createFL(self,flightLoop,filename):
        self.flightLoopClass = flightLoop
        self.flightLoopFileName = filename

        execfile(self.flightLoopFileName)
        self.CB = self.innerFC
        XPLMRegisterFlightLoopCallback(ConfigContainer.plugin, self.CB, 1, 0)


    def innerFC(self, elapsedMe, elapsedSim, counter, refcon):
        return self.flightLoopClass.FL(elapsedMe, elapsedSim, counter, refcon)


    def destroyFL(self):
        XPLMUnregisterFlightLoopCallback(ConfigContainer.plugin, self.CB, 0)