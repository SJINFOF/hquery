from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

from hbase import Hbase
from hbase.ttypes import *

import utils
import settings

transport = TSocket.TSocket('srv1', 9090)

transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)

client = Hbase.Client(protocol)
transport.open()
print client.getTableNames()

print client.getRow('hisdata30g', '00001120180102101957')

tableName = 'hisdata30g'
startRow = '00001120180102101957'
stopRow = '00001120180102112557'
# Initialize a scanner to query all columns between startRow and stopRow
scannerId = client.scannerOpenWithStop(tableName, startRow, stopRow, ['data'])

# Convert results to human readable python dict
rowList = client.scannerGetList(scannerId, settings.ScanBatchSize)
results = []
while rowList:
    results.extend([utils.TResultToDict(row) for row in rowList])
    rowList = client.scannerGetList(scannerId, settings.ScanBatchSize)
client.scannerClose(scannerId)

import json

print json.dumps(results[0], indent=4)
print len(results)

if __name__ == '__main__':
    pass
