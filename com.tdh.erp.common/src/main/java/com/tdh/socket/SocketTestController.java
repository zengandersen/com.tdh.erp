package com.tdh.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
public class SocketTestController {

    @Autowired
    private Socket socket;
    @RequestMapping(value = "/socket/{message}")
    public void sendMessage(@PathVariable("message") String message) throws IOException {
        socket.sendMessage("直接向服务端发送消息："+message);
    }
}
