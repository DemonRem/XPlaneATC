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
class ConfigContainer:

    plugin = False


    @staticmethod
    def setPlugin(plugin):
        ConfigContainer.plugin = plugin



    @staticmethod
    def getPlugin():
        return ConfigContainer.plugin