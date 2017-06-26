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
class NavPoint:


    def __init__(self,x, y, z, heading, phi=0, theta=0):

        self.x = x
        self.y = y
        self.z = z
        self.heading = heading
        self.theta = theta
        self.phi = phi
        self.latitude = 0
        self.longitude = 0
        self.name = False


    def getPos(self):
        return [self.x, self.y, self.z,self.theta,self.heading,self.phi]

    def toLocal(self):
        
        
        self.x, self.y, self.z = XPLMWorldToLocal(self.latitude, self.longitude, 0)
        
        
    def toWorld(self):
        self.latitude, self.longitude, self.phi = XPLMLocalToWorld(self.x, self.y, self.z)
        


    def setPos(self,x,y,z,heading,phi=0,theta=0):
        self.x = x
        self.y = y
        self.z = z
        self.heading = heading
        self.phi = phi
        self.theta = theta