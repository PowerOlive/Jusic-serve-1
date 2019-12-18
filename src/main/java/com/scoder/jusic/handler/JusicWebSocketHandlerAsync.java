package com.scoder.jusic.handler;

import com.scoder.jusic.common.message.Response;
import com.scoder.jusic.configuration.JusicProperties;
import com.scoder.jusic.model.MessageType;
import com.scoder.jusic.model.Music;
import com.scoder.jusic.model.Online;
import com.scoder.jusic.repository.MusicPlayingRepository;
import com.scoder.jusic.service.MusicService;
import com.scoder.jusic.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.LinkedList;

/**
 * @author H
 */
@Component
@Slf4j
public class JusicWebSocketHandlerAsync {

    @Autowired
    private JusicProperties musicBar;
    @Autowired
    private MusicPlayingRepository musicPlayingRepository;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MusicService musicService;

    @Async
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionService.putSession(session);
        int size = musicBar.getSessions().size();
        log.info("Connection established: {}, and now online: {}", session.getId(), size);
        Thread.sleep(500);
        sessionService.send(session, MessageType.NOTICE, Response.success((Object) null, "连接到服务器成功！"));
        // 1. send online
        Online online = new Online();
        online.setCount(size);
        sessionService.send(MessageType.ONLINE, Response.success(online));
        // 2. send playing
        Music playing = musicPlayingRepository.getPlaying();
        sessionService.send(session, MessageType.MUSIC, Response.success(playing, "正在播放"));
        // 3. send pick list
        LinkedList<Music> pickList = musicService.getPickList();
        sessionService.send(session, MessageType.PICK, Response.success(pickList, "播放列表"));
        log.info("发现有客户端连接, 已向该客户端: {} 发送正在播放的音乐: {}, 以及播放列表, 共 {} 首", session.getId(), playing.getName(), pickList.size());
    }

    @Async
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessionService.clearSession(session);
        int size = musicBar.getSessions().size();
        log.info("Connection closed: {}, and now online: {}", session.getId(), size);
        Online online = new Online();
        online.setCount(size);
        sessionService.send(MessageType.ONLINE, Response.success(online, null));
    }

}
