# coding=utf-8
#!/usr/bin/python3

import os
from flask import Flask, send_from_directory, request

app = Flask(__name__)


@app.route("/download/<path:filename>", methods=['POST', 'GET'])
def downloader(filename):
    """
    不支持断点的下载
    """
    data = request.values.get('key')
    print(data)
    dirpath = '/Users/aria/dev/ftp'
    # as_attachment=True 一定要写，不然会变成打开，而不是下载
    return send_from_directory(dirpath, filename, as_attachment=True)


@app.route("/download1", methods=['POST', 'GET'])
def downloader1():
    """
    不支持断点的下载
    """
    filename = request.values.get('filename')
    data = request.values.get('key')
    print(data)
    dirpath = 'D:/test'
    # as_attachment=True 一定要写，不然会变成打开，而不是下载
    return send_from_directory(dirpath, filename, as_attachment=True)


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)  # 需要关闭防火墙
