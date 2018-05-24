
from PyQt4.QtCore import *
iface.mapCanvas().refresh()
timer = QTimer()
timer.setInterval(500)
QObject.connect(timer,SIGNAL("timeout()"),qgis.utils.iface.mapCanvas().refresh)
timer.start() 
#timer.stop() 