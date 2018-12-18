# -*- coding:utf-8 -*-

import settings


def convert(key, value):
    return settings.ConvertRules[key](value)


def TResultToDict(result):
    rowKey = result.row
    # A row key is of the format 'code||datetime',
    # for example, '00001120180425105702',
    # here the code is '000011'
    # and datetime is  '20180425105702'
    ret = {
        'code': rowKey[:6],
        'timestamp': rowKey[6:]
    }
    for columnName, columnValue in result.columns.items():
        # A column name is of the format 'data:xxxx',
        # we only need the real name after 'data:'
        key = columnName[5:]
        value = columnValue.value
        ret[key] = convert(key, value)
    return ret
