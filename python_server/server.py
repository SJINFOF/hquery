from __future__ import print_function
from flask import render_template
from flask import Flask
from flask import request
from flask import jsonify
from gevent.pywsgi import WSGIServer

import hdb
import utils

############   configurations ##############
host = '0.0.0.0'
port = 7711

############################################

app = Flask(__name__,
            static_folder="../frontend/dist/static",
            template_folder="../frontend/dist")

hBaseHistoryDb = hdb.HBaseHistoryDb()
mongoHistoryDb = None


def make_response(msg='ok',
                  errcode=0,
                  **kwargs):
    return jsonify(
        msg=msg,
        errcode=errcode,
        **kwargs
    )


@app.route("/", methods=['Get'])
def home():
    return render_template('index.html')


@app.route("/query/get", methods=['GET'])
def query_get():
    try:
        code = request.args['code']
        timestamp = utils.isodate(request.args['timestamp'])
        engine_name = request.args['engine']
        engine = hBaseHistoryDb if engine_name == 'hbase' else mongoHistoryDb

        data, timeCostSeconds = engine.get(code, timestamp)
        return make_response(data=data, queryTime=timeCostSeconds * 1000)
    except Exception as e:
        return make_response(msg=str(e), errcode=-1)


@app.route("/query/scan", methods=['GET'])
def query_scan():
    try:
        code = request.args['code']
        startTime = utils.isodate(request.args['start'])
        endTime = utils.isodate(request.args['end'])
        engine_name = request.args['engine']
        engine = hBaseHistoryDb if engine_name == 'hbase' else mongoHistoryDb

        data, timeCostSeconds = engine.scan(code, startTime, endTime)
        return make_response(data=data, queryTime=timeCostSeconds * 1000)
    except Exception as e:
        return make_response(msg=str(e), errcode=-1)


if __name__ == '__main__':
    http_server = WSGIServer((host, port), app)
    http_server.serve_forever()
