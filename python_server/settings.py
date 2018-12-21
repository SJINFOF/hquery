# -*- coding:utf-8 -*-

ScanBatchSize = 100000
HistoryDbName = "hisdata30g"
HBaseHost = "srv1"
HBasePort = "9090"


def IntList(v):
    return [int(x) for x in v.strip().split(' ')]


ConvertRules = {
    'code': str,  # 股票代码
    'uiDateTime': str,  # 日期时间
    'uiPreClosePx': int,  # 昨收价(3)
    'uiOpenPx': int,  # 开始价(3)'
    'uiHighPx': int,  # 最高价(3)
    'uiLowPx': int,  # 最低价(3)
    'uiLastPx': int,  # 收盘价(3)
    'uiNumTrades': int,  # 成交笔数(0)
    'strInstrumentStatus': str,  # 交易状态
    'uiTotalVolumeTrade': int,  # 成交总量(3)
    'uiTotalValueTrade': int,  # 成交总金额(5)
    'uiTotalBidQty': int,  # 委托买入总量(3)
    'uiTotalOfferQty': int,  # 委托卖出总量(3)
    'uiWeightedAvgBidPx': int,  # 加权平均委买价格（债券共用）(3)
    'uiWeightedAvgOfferPx': int,  # 加权平均委卖价格（债券共用）(3)
    'uiWithdrawBuyNumber': int,  # 买入撤单笔数(0)
    'uiWithdrawSellNumber': int,  # 卖出撤单笔数(0)
    'uiWithdrawBuyAmount': int,  # 买入撤单数量(3)
    'uiWithdrawBuyMoney': int,  # 买入撤单金额(5)
    'uiWithdrawSellAmount': int,  # 卖出撤单数量(3)
    'uiWithdrawSellMoney': int,  # 卖出撤单金额(5)
    'uiTotalBidNumber': int,  # 买入总笔数(0)
    'uiTotalOfferNumber': int,  # 卖出总笔数(0)
    'uiBidTradeMaxDuration': int,  # 买入委托成交最大等待时间(0)
    'uiOfferTradeMaxDuration': int,  # 卖出委托成交最大等待时间(0)
    'uiNumBidOrders': int,  # 买方委托价位数(0)
    'uiNumOfferOrders': int,  # 卖方委托价位数(0)
    'arrBidPrice': IntList,  # 申买价(3)
    'arrBidOrderQty': IntList,  # 申买量(3)
    'arrBidNumOrders': IntList,  # 申买十实际总委托笔数(0)
    'arrBidOrders': IntList,  # 申买一前50笔订单(3)
    'arrOfferPrice': IntList,  # 申卖价(3)
    'arrOfferOrderQty': IntList,  # 申卖量(3)
    'arrOfferNumOrders': IntList,  # 申卖十实际总委托笔数(0)
    'arrOfferOrders': IntList,  # 申卖一前50笔订单(3)
    'uiIOPV': int,  # ETF净值估值（ETF）(3)
    'uiETFBuyNumber': int,  # ETF申购笔数（ETF）(0)
    'uiETFBuyAmount': int,  # ETF申购数量（ETF）(3)
    'uiETFBuyMoney': int,  # ETF申购金额（ETF）(5)
    'uiETFSellNumber': int,  # ETF赎回笔数（ETF）(0)
    'uiETFSellAmount': int,  # ETF赎回数量（ETF）(3)
    'uiETFSellMoney': int,  # ETF赎回金额（ETF）(5)
}

if __name__ == '__main__':
    print ConvertRules
