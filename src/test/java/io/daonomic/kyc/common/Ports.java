package io.daonomic.kyc.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Ports {

    public static int nextPort() {
        return nextPorts(1).get(0);
    }

    public static List<Integer> nextPorts(int num) {
        List<Integer> result = new ArrayList<Integer>(num);
        List<ServerSocket> sockets = new ArrayList<ServerSocket>(num);
        try {
            for (int i = 0; i < num; i++) {
                ServerSocket socket = new ServerSocket(0);
                sockets.add(socket);
                result.add(socket.getLocalPort());
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            for (ServerSocket socket : sockets) {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
