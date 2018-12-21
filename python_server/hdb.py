# -*- coding:utf-8 -*-
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from hbase import Hbase
from hbase.ttypes import *
import time

import utils
import settings
from dbbase import HistoryDb


class HBaseHistoryDb(HistoryDb):
    def __init__(self):
        HistoryDb.__init__(self)
        transport = TSocket.TSocket(settings.HBaseHost, settings.HBasePort)
        transport = TTransport.TBufferedTransport(transport)
        protocol = TBinaryProtocol.TBinaryProtocol(transport)
        client = Hbase.Client(protocol)

        self.transport = transport
        self.client = client

    def get(self, code, timestamp):
        """

        :param code: str
        :param timestamp: datetime.datetime
        :return:
        """
        return self.internelGet(self.genRowKey(code, timestamp))

    def scan(self, code, startTime, endTime):
        """

        :param code: str
        :param startTime: datetime.datetime
        :param endTime: datetime.datetime
        :return:
        """
        startRow = self.genRowKey(code, startTime)
        stopRow = self.genRowKey(code, endTime)
        return self.internalScan(startRow, stopRow)

    def genRowKey(self, code, timestamp):
        timeStr = timestamp.strftime("%Y%m%d%H%M%S")
        return "{}{}".format(code, timeStr)

    def internelGet(self, rowKey):
        self.transport.open()
        start = time.time()
        row = self.client.getRow(settings.HistoryDbName, rowKey)
        end = time.time()
        timeCost = end - start
        self.transport.close()
        if row:
            return utils.TResultToDict(row[0]), timeCost
        else:
            return [], timeCost

    def internalScan(self, startRow, stopRow):
        self.transport.open()
        # Initialize a scanner to query all columns between startRow and stopRow
        scannerId = self.client.scannerOpenWithStop(settings.HistoryDbName, startRow, stopRow, ['data'])
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
    historyDb = HBaseHistoryDb()
    # result, timeCost = historyDb._get(startRow)
    # print result
    # print timeCost
    resultList, timeCost = historyDb.internalScan(startRow, stopRow)
    print len(resultList)
    print timeCost
