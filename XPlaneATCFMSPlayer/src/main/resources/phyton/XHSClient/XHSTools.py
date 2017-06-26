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
execfile('Resources/plugins/PythonScripts/XHSClient/NavPoint.py')
class XHSTools:

    @staticmethod
    def getRef(refString, type="float", asString=False):

        dataRef = XPLMFindDataRef(refString)

        if (type == "float"):
            value = XPLMGetDataf(dataRef)

        elif (type == "int"):
            value = XPLMGetDatai(dataRef)

        elif (type == "double"):
            value = XPLMGetDatad(dataRef)

        if (asString):
            return str(value)
        else:
            return value


    @staticmethod
    def setRefi(refName,value):
        XPLMSetDatai(refName, value)
        
        

    @staticmethod
    def calcDistanceByCoords(x1,y1,z1,x2,y2,z2):

        distance = sqrt(pow((x1 - x2),2) + pow((y1 - y2),2) + pow((z1 - z2),2))
        return distance

    @staticmethod
    def calcDistanceByNavpoints(navpoint1, navpoint2):

#        Config.debugMessage("Calcing Distance X1:" + str(navpoint1.x) + " Y1: 0" + " Z1: " + str(navpoint1.z) + " X1:" + str(navpoint2.x) + " Y2: 0" + " Z2: " + str(navpoint2.z))
        return FollowMeCarTools.calcDistanceByCoords(navpoint1.x,0,navpoint1.z,navpoint2.x,0,navpoint2.z)
        #distance = sqrt(pow((navpoint1.x - navpoint2.x),2) + pow((navpoint1.y - navpoint2.y),2) + pow((navpoint1.z - navpoint2.z),2))
        #return distance

    @staticmethod
    def findNextAirport(lat, lon):

        Config.debugMessage("finding Airport")
        airportID = XPLMFindNavAid(None, None, lat, lon, None, xplm_Nav_Airport)
        outAirportName = []
        outAirportId = []
        XPLMGetNavAidInfo(airportID, None, None, None, None, None, None, outAirportId, outAirportName, None)
        airportName = outAirportName[0]
        airportIcao = outAirportId[0]
        return airportName,airportIcao

    @staticmethod
    def probeYTerrain(x, y, z):
        probeRef = XPLMCreateProbe(xplm_ProbeHitTerrain)
        info = []
        XPLMProbeTerrainXYZ(probeRef, x, y, z, info)
        alt = info[2]
        XPLMDestroyProbe(probeRef)
        return alt

    @staticmethod
    def isCarBehindPlane(planeX,planeZ,carX,carZ,planeHeading,nextPoint):
        
#        Config.debugMessage(str(planeX) + " " + str(planeZ) + " " + str(carX) + " " + str(carZ))

#        Config.debugMessage("Calculating Heading to Car")
        headingToCar = FollowMeCarTools.calcHeadingBetweenNavPoints(planeX,planeZ,carX,carZ)
        headingToCar = headingToCar - planeHeading
        
        if nextPoint:
            headingToNextPoint = FollowMeCarTools.calcHeadingBetweenNavPoints(planeX,planeZ,nextPoint.x,nextPoint.z)
            headingToNextPoint = headingToNextPoint - planeHeading
        
            if (headingToNextPoint > 135):
                return False
            if (headingToNextPoint < -135):
                return False
        
#        Config.debugMessage("*****HEADING TO PLANE = " + str(heading))
        if (headingToCar > 135):
            return True
        if (headingToCar < -135):
            return True

        return False
        
        

    @staticmethod
    def calcHeadingBetweenNavPoints(x,z,x1,z1):
        # Get heading from point to point
        res = [x1 - x, z1 - z]


        if res[0] == 0:
            if  res[1] > 0: return 0
            else: return 180
        if res[1] == 0:
            if res[0] > 0: 90
            else: return 270

        h = (res[0]**2 + res[1]**2)**0.5
        hdg = fabs(degrees(asin(res[1]/h)))

        #quadrants
        if res[1] < 0:
            if res[0] > 0: hdg = 90 - hdg
            else: hdg = 270 + hdg
        else:
            if res[0] > 0: hdg = 90 + hdg
            else: hdg = 270 - hdg

        return hdg
    
    @staticmethod
    def calcHeadingBetweenNavPointsByNP(navpoint1,navpoint2):
        return FollowMeCarTools.calcHeadingBetweenNavPoints(navpoint1.x,navpoint1.z,navpoint2.x,navpoint2.z)

    @staticmethod
    def calcInitialDistanceOfCarToPlane(weight):

        if (weight >= 15000):
            Config.debugMessage("Wight is above 15000, dist is full")
            return 170

        if (weight >= 10000):
            Config.debugMessage("Wight is above 10000, weight is minus 20")
            return 120

        if (weight >= 5000):
            Config.debugMessage("Wight is above 5000, dist is minus 40")
            return 100

        Config.debugMessage("no Weight is mentioned, returning full distance from Config")
        return 60
    
    @staticmethod
    def calcMaxDistance(weight):

        if (weight >= 15000):
            Config.debugMessage("Wight is above 15000, dist is full")
            return 230

        if (weight >= 10000):
            Config.debugMessage("Wight is above 10000, weight is minus 20")
            return 160

        if (weight >= 5000):
            Config.debugMessage("Wight is above 5000, dist is minus 40")
            return 120

        Config.debugMessage("no Weight is mentioned, returning full distance from Config")
        return 70
    
    
        
    @staticmethod
    def findNextInitialNavPointAndEraseAllOthers(navpointCar,navpointList):
        
        #allright, let's begin and find the next Navpoint to the car
        indexCounter = 0
        distanceCarToPoint = 0
        indexFound = 0
        closestNavpoint = False
        lastDist = 100000
        for navpoint in navpointList:
            distanceCarToPoint = FollowMeCarTools.calcDistanceByNavpoints(navpoint,navpointCar)
            if (distanceCarToPoint < lastDist):
                lastDist = distanceCarToPoint
                closestNavpoint = navpoint
                indexFound = indexCounter
            indexCounter += 1
            
        for i in range(0,indexFound):
            navpointList.pop(0)
            
        return navpointList
    
    