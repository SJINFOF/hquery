#!/usr/bin/env bash
for d in 20180102 20180103 20180104 20180105 20180108 20180109 20180110; do
    hbase org.apache.hadoop.hbase.mapreduce.ImportTsv \
    -Dimporttsv.separator=,  \
    -Dimporttsv.columns="HBASE_ROW_KEY,data:uiPreClosePx,data:uiOpenPx,data:uiHighPx,data:uiLowPx,data:uiLastPx,data:uiNumTrades,data:strInstrumentStatus,data:uiTotalVolumeTrade,data:uiTotalValueTrade,data:uiTotalBidQty,data:uiTotalOfferQty,data:uiWeightedAvgBidPx,data:uiWeightedAvgOfferPx,data:uiWithdrawBuyNumber,data:uiWithdrawSellNumber,data:uiWithdrawBuyAmount,data:uiWithdrawBuyMoney,data:uiWithdrawSellAmount,data:uiWithdrawSellMoney,data:uiTotalBidNumber,data:uiTotalOfferNumber,data:uiBidTradeMaxDuration,data:uiOfferTradeMaxDuration,data:uiNumBidOrders,data:uiNumOfferOrders,data:arrBidPrice,data:arrBidOrderQty,data:arrBidNumOrders,data:arrBidOrders,data:arrOfferPrice,data:arrOfferOrderQty,data:arrOfferNumOrders,data:arrOfferOrders,data:uiIOPV,data:uiETFBuyNumber,data:uiETFBuyAmount,data:uiETFBuyMoney,data:uiETFSellNumber,data:uiETFSellAmount,data:uiETFSellMoney" \
    -Dimporttsv.bulk.output=h_${d} \
    hisdata30g \
    hdfs://srv1:9000/30Gdata/${d}/data.csv
done


hbase org.apache.hadoop.hbase.mapreduce.ImportTsv \
-Dimporttsv.separator=,  \
-Dimporttsv.columns="HBASE_ROW_KEY,data:uiPreClosePx,data:uiOpenPx,data:uiHighPx,data:uiLowPx,data:uiLastPx,data:uiNumTrades,data:strInstrumentStatus,data:uiTotalVolumeTrade,data:uiTotalValueTrade,data:uiTotalBidQty,data:uiTotalOfferQty,data:uiWeightedAvgBidPx,data:uiWeightedAvgOfferPx,data:uiWithdrawBuyNumber,data:uiWithdrawSellNumber,data:uiWithdrawBuyAmount,data:uiWithdrawBuyMoney,data:uiWithdrawSellAmount,data:uiWithdrawSellMoney,data:uiTotalBidNumber,data:uiTotalOfferNumber,data:uiBidTradeMaxDuration,data:uiOfferTradeMaxDuration,data:uiNumBidOrders,data:uiNumOfferOrders,data:arrBidPrice,data:arrBidOrderQty,data:arrBidNumOrders,data:arrBidOrders,data:arrOfferPrice,data:arrOfferOrderQty,data:arrOfferNumOrders,data:arrOfferOrders,data:uiIOPV,data:uiETFBuyNumber,data:uiETFBuyAmount,data:uiETFBuyMoney,data:uiETFSellNumber,data:uiETFSellAmount,data:uiETFSellMoney" \
-Dimporttsv.bulk.output=h_20180102 \
hisdata30g \
hdfs://srv1:9000/30Gdata/20180102/data.csv

hadoop jar lib/hbase-server-1.4.8.jar completebulkload h_20180102 hisdata30g

hbase org.apache.hadoop.hbase.mapreduce.ImportTsv \
-Dimporttsv.separator=,  \
-Dimporttsv.columns="HBASE_ROW_KEY,data:uiPreClosePx,data:uiOpenPx,data:uiHighPx,data:uiLowPx,data:uiLastPx,data:uiNumTrades,data:strInstrumentStatus,data:uiTotalVolumeTrade,data:uiTotalValueTrade,data:uiTotalBidQty,data:uiTotalOfferQty,data:uiWeightedAvgBidPx,data:uiWeightedAvgOfferPx,data:uiWithdrawBuyNumber,data:uiWithdrawSellNumber,data:uiWithdrawBuyAmount,data:uiWithdrawBuyMoney,data:uiWithdrawSellAmount,data:uiWithdrawSellMoney,data:uiTotalBidNumber,data:uiTotalOfferNumber,data:uiBidTradeMaxDuration,data:uiOfferTradeMaxDuration,data:uiNumBidOrders,data:uiNumOfferOrders,data:arrBidPrice,data:arrBidOrderQty,data:arrBidNumOrders,data:arrBidOrders,data:arrOfferPrice,data:arrOfferOrderQty,data:arrOfferNumOrders,data:arrOfferOrders,data:uiIOPV,data:uiETFBuyNumber,data:uiETFBuyAmount,data:uiETFBuyMoney,data:uiETFSellNumber,data:uiETFSellAmount,data:uiETFSellMoney" \
hisdata30g \
hdfs://srv1:9000/test.csv

dd if=/dev/zero of=/mds/swap count=16384 bs=1MiB
sudo chmod 600 /mds/swap
sudo mkswap /mds/swap
sudo swapon /mds/swap