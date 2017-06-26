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
from math import *
execfile('Resources/plugins/PythonScripts/XHSClient/XHSTools.py')
execfile('Resources/plugins/PythonScripts/XHSClient/NavPoint.py')
execfile('Resources/plugins/PythonScripts/XHSClient/Config.py')


class FCAircraft:


    def __init__(self):


        self.navpoint = NavPoint(0,0,0,0,0,0)

        self.posXString = ""
        self.posYString = ""
        self.posZString = ""
        self.posHeadingString = ""


        self.refTransponder = "sim/cockpit/radios/transponder_code"
        self.refTransponderMode = "sim/cockpit/radios/transponder_mode"
        self.transponder = False
        self.transponder_mode = False
        self.refX = "sim/flightmodel/position/local_x"
        self.refY = "sim/flightmodel/position/local_y"
        self.refZ = "sim/flightmodel/position/local_z"
        self.refHeading = "sim/flightmodel/position/psi"
        self.refTheta = "sim/flightmodel/position/theta"
        self.refPhi = "sim/flightmodel/position/phi"
        self.groundspeedRef = "sim/flightmodel/position/groundspeed"
        self.weightRef = "sim/flightmodel/weight/m_total"
        self.onGroundRef = "sim/flightmodel/failures/onground_any"
        self.pausedRef = "sim/time/paused"


        self.gpsSpeedRef = "sim/cockpit2/radios/indicators/gps_dme_speed_kts"
        self.refLongitude = "sim/flightmodel/position/longitude"
        self.refLatitude = "sim/flightmodel/position/latitude"
        self.posLatitudeString = ""
        self.posLongitudeString = ""
        self.posAltitudeString = ""
        self.posLongitudeDouble = 0
        self.onGroundInt = 0
        self.posLatitudeDouble = 0
        self.posAltitudeDouble = 0
        self.posLatitudeFloat = 0
        self.posLongitudeFloat = 0
        self.groundspeedFloat = 0
        self.gpsSpeedFloat = 0
        self.weight =0
        self.pi = 3.14159265
        
        self.truePHIRef = "sim/flightmodel/position/true_phi"
        self.truePHI = False
        self.trueThetaRef = "sim/flightmodel/position/true_theta"
        self.trueTheta = False

        self.radioCom1MHZRef = "sim/cockpit2/radios/actuators/com1_frequency_Mhz"
        self.radioCom1KHZREF = "sim/cockpit2/radios/actuators/com1_frequency_khz"
        self.com1Freq = False
        
        self.renewData();


    def renewData(self):



        
        self.com1Freq = str(XHSTools.getRef(self.radioCom1MHZRef,"int")) + "." + str(XHSTools.getRef(self.radioCom1KHZREF,"int"))

        self.truePHI = XHSTools.getRef(self.truePHIRef,"float")
        self.trueTheta = XHSTools.getRef(self.trueThetaRef,"float")
        
        self.transponder = XHSTools.getRef(self.refTransponder,"int")
        self.transponderMode = XHSTools.getRef(self.refTransponderMode,"int")
        
        
        self.gpsSpeedFloat = XHSTools.getRef(self.gpsSpeedRef,"float")
        self.pausedInt = XHSTools.getRef(self.pausedRef,"int")
        self.navpoint.x = XHSTools.getRef(self.refX, "float")
        self.navpoint.y = XHSTools.getRef(self.refY, "float")
        self.navpoint.z = XHSTools.getRef(self.refZ, "float")
        self.navpoint.heading = XHSTools.getRef(self.refHeading, "float")
        self.navpoint.phi = XHSTools.getRef(self.refPhi, "float")
        self.navpoint.theta = XHSTools.getRef(self.refTheta, "float")
        self.groundspeedFloat = XHSTools.getRef(self.groundspeedRef, "float")
        self.weight = XHSTools.getRef(self.weightRef, "float")
        
        self.onGroundInt = XHSTools.getRef(self.onGroundRef,"int")


        self.posXString = XHSTools.getRef(self.refX, "float", True)
        self.posYString = XHSTools.getRef(self.refY, "float", True)
        self.posZString = XHSTools.getRef(self.refZ, "float", True)
        self.posHeadingString = XHSTools.getRef(self.refHeading, "float", True)

        self.posLatitudeFloat = XHSTools.getRef(self.refLatitude, "float")
        self.posLongitudeFloat = XHSTools.getRef(self.refLongitude, "float")
        self.posLatitudeDouble, self.posLongitudeDouble,self.posAltitudeDouble = XPLMLocalToWorld(self.navpoint.x,self.navpoint.y, self.navpoint.z)
        self.posLatitudeString = str(self.posLatitudeDouble)
        self.posLongitudeString = str(self.posLongitudeDouble)
        self.posAltitudeString = str(self.posAltitudeDouble)



    def getPosition(self):
        return [self.navpoint.x, self.navpoint.y, self.navpoint.z, self.navpoint.theta, self.navpoint.heading, self.navpoint.phi]


    def getPOne(self,distance,heading, orig=False):
        
        self.renewData()
        if not orig:
            
            orig = self.navpoint.getPos()
            
        a = 90 + heading + orig[4]
        h = distance
        x = cos(radians(a)) * h
        z = sin(radians(a)) * h
        
        orig = orig[:]
        orig[0] -= x * orig[0]**0
        orig[2] -= z * orig[2]**0
        returnPoint = NavPoint(0,0,0,0,0,0)
        returnPoint.x = orig[0]
        returnPoint.y = 0
        returnPoint.z = orig[2]
        
        
        return returnPoint
        
        


    def getP(self,distance,heading):
        self.renewData()
        returnPoint = NavPoint(0,0,0,0,0,0)
        
        hdg = self.navpoint.heading + heading
        
        returnPoint.x = self.navpoint.x - (distance * cos(self.degreeToRadian(hdg)))
        returnPoint.z = self.navpoint.z + (distance * sin(self.degreeToRadian(hdg)))
        returnPoint.y = 0
        return returnPoint



    def getPointAtHdg(self, distance, hdg = 0, givenPos = False):


        Config.debugMessage("**********************************************")
        Config.debugMessage("Calcing rel pos with distance/Hdg " + str(distance) + "/" + str(hdg) + "\n")
        self.renewData()
        newHdg = self.navpoint.heading + hdg
        #newHdg = hdg
        if newHdg < 0:
            newHdg = 360 - newHdg
        elif newHdg > 360:
            newHdg = newHdg - 360

        Config.debugMessage("New Heading is now: " + str(newHdg) + "\n")


        if not givenPos:
            self.renewData()
            givenPos = self.navpoint

        x = self.navpoint.x
        z = self.navpoint.z

        Config.debugMessage("GIVEN POS X = " + str(x) + "\n")
        Config.debugMessage("GIVEN POS Z = " + str(z) + "\n")

        returnNavPoint = NavPoint(0,0,0,0,0,0)


        returnNavPoint.x = x + distance * cos(newHdg)
        returnNavPoint.y = self.navpoint.y
        returnNavPoint.z = z + distance * sin(newHdg)
        Config.debugMessage("REL POINT X = " + str(returnNavPoint.x) + "\n")
        Config.debugMessage("REL POINT Z = " + str(returnNavPoint.z) + "\n")
        Config.debugMessage("**********************************************")

        return returnNavPoint

    def degreeToRadian(self,deg):
        return (deg * 0.017453292519)
