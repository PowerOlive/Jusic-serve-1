package com.scoder.jusic.controller;

import com.scoder.jusic.common.message.Response;
import com.scoder.jusic.model.Chat;
import com.scoder.jusic.model.MessageType;
import com.scoder.jusic.model.User;
import com.scoder.jusic.service.MailService;
import com.scoder.jusic.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * @author H
 */
@Controller
@Slf4j
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private SessionService sessionService;

    @MessageMapping("/mail/send")
    public void send(Chat chat, StompHeaderAccessor stompHeaderAccessor) {
        String sessionId = stompHeaderAccessor.getHeader("simpSessionId").toString();
        User user = sessionService.getUser(sessionId);
        User black = sessionService.getBlack(sessionId);
        long currentTime = System.currentTimeMillis();
        if (null != black && black.getSessionId().equals(sessionId)) {
            sessionService.send(sessionId, MessageType.NOTICE, Response.failure((Object) null, "你已被拉黑"));
        } else if (null != user.getLastMessageTime() && currentTime - user.getLastMessageTime() < 2000) {
            sessionService.send(sessionId, MessageType.NOTICE, Response.failure((Object) null, "发言时间间隔太短"));
        } else {
            chat.setSessionId(user.getSessionId());
            chat.setNickName(user.getNickName());
            chat.setContent("@管理员 " + chat.getContent());
            sessionService.send(MessageType.CHAT, Response.success(chat, "@管理员"));
            sessionService.setLastMessageTime(user, System.currentTimeMillis());

            StringBuilder content = new StringBuilder()
                    .append(user)
                    .append("\n\n\n\n")
                    .append(chat.getContent());
            boolean result = mailService.sendSimpleMail("music.scoder.club@管理员[" + user.getRemoteAddress() + "]", content.toString());
            if (result) {
                log.info("session id: {}, @管理员, 邮件发送成功", sessionId);
                sessionService.send(sessionId, MessageType.NOTICE, Response.success((Object) null, "@管理员 成功"));
            } else {
                log.info("session id: {}, @管理员, 邮件发送失败", sessionId);
                sessionService.send(sessionId, MessageType.NOTICE, Response.failure((Object) null, "@管理员 失败"));
            }
        }
    }
}
