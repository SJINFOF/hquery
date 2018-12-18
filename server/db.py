# -*- coding:utf-8 -*-

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

from hbase import Hbase
from hbase.ttypes import *

import time

import utils
import settings


class HistoryDb:
    def __init__(self):
        transport = TSocket.TSocket('srv1', 9090)
        transport = TTransport.TBufferedTransport(transport, 118192)
        protocol = TBinaryProtocol.TBinaryProtocol(transport)
        client = Hbase.Client(protocol)

        self.transport = transport
        self.client = client

    def get(self, rowKey):
        self.transport.open()
        start = time.time()
        row = self.client.getRow(settings.HistoryDbName, rowKey)
        end = time.time()
        timeCost = end - start
        self.transport.close()
        return utils.TResultToDict(row[0]), timeCost

    def scan(self, startRow, stopRow):
        self.transport.open()
        # Initialize a scanner to query all columns between startRow and stopRow
        scannerId = self.client.scannerOpenWithStop(tableName, startRow, stopRow, ['data'])
        # Convert results to human readable python dict
        start = time.time()
        rowList = self.client.scannerGetList(scannerId, settings.ScanBatchSize)
        end = time.time()
        timeCost = end - start
        print '{}s'.format(timeCost)
        print 'ok'
        results = []
        while rowList:
            results.extend([utils.TResultToDict(row) for row in rowList])
            rowList = self.client.scannerGetList(scannerId, settings.ScanBatchSize)
        self.client.scannerClose(scannerId)
        self.transport.close()
        return results, timeCost


if __name__ == '__main__':
    tableName = 'hisdata30g'
    startRow = '00001120180108101957'
    stopRow = '00001120180108152257'
    historyDb = HistoryDb()
    # result, timeCost = historyDb.get(startRow)
    # print result
    # print timeCost
    resultList, timeCost = historyDb.scan(startRow, stopRow)
    print len(resultList)
    print timeCost
