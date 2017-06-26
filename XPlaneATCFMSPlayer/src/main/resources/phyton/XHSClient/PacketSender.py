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
import socket


execfile('Resources/plugins/PythonScripts/XHSClient/FCAircraft.py')

class PacketSender:
    
    
    def __init__(self):
        
        self.server = "192.168.56.1"
        self.port = 8181
        self.aircraft = FCAircraft()
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM) # UDP

        
    def FL(self, elapsedMe, elapsedSim, counter, refcon):
        
        self.aircraft.renewData()
        
        message = self.aircraft.posLatitudeString + " " \
            + self.aircraft.posLongitudeString + " "    \
            + self.aircraft.posAltitudeString + " "     \
            + str(self.aircraft.gpsSpeedFloat) + " "    \
            + str(self.aircraft.weight) + " "           \
            + str(self.aircraft.groundspeedFloat) + " " \
            + str(self.aircraft.transponder) + " "      \
            + str(self.aircraft.transponderMode) + " "  \
            + self.aircraft.posHeadingString + " "      \
            + str(self.aircraft.truePHI) + " "          \
            + str(self.aircraft.trueTheta) + " "        \
            + self.aircraft.com1Freq                    \
            + "|||"
        
        
        counter = 0
        if (XPLMCountFMSEntries() > 0):
            fmsCount = XPLMCountFMSEntries()
            while (counter < fmsCount):
                outType = []; outID = []; outRef = []; outAltitude = []; outLat = []; outLon = []
                XPLMGetFMSEntryInfo(counter, outType, outID, outRef, outAltitude, outLat, outLon)
                
                message += str(outType[0]) + "**" + str(outID[0]) + "**"  + str(outRef[0]) + "**"  + str(outAltitude[0]) + "**"  + str(outLat[0]) + "**"  + str(outLon[0]) + "***"
                
                counter += 1
                
        
        
        Config.debugMessage("Sending " + message)
        
        
        self.sock.sendto(message, (self.server, self.port))
        return 5
