import socketserver


class TCPHandler(socketserver.StreamRequestHandler):

    BASE_PATH = "/Users/aria/temp/tcp/"

    def handle(self):
        data = self.request.recv(1024).strip()
        file_name = data.decode("utf-8")
        print("file_name: %s" % file_name)
        print("{} wrote:".format(self.client_address[0]))
        with open(self.BASE_PATH + file_name, "rb") as f:
            b = f.read(1024)
            if b:
                self.wfile.write(b)
            else:
                print("发送成功")


if __name__ == "__main__":

    HOST, PORT = "localhost", 9999

    with socketserver.TCPServer((HOST, PORT), TCPHandler) as server:
        server.serve_forever()
