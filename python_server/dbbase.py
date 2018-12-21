# -*- coding:utf-8 -*-


class HistoryDb:
    def __init__(self):
        pass

    def get(self, code, timestamp):
        raise NotImplementedError()

    def scan(self, code, startTime, endTime):
        raise NotImplementedError()
