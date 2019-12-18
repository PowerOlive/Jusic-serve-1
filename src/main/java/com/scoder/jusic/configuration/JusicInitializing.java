package com.scoder.jusic.configuration;

import com.scoder.jusic.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author H
 */
@Component
@Slf4j
public class JusicInitializing implements InitializingBean {

    private final JusicProperties jusicProperties;
    private final ResourceLoader resourceLoader;
    private final ConfigRepository configRepository;
    private final SessionRepository sessionRepository;
    private final MusicDefaultRepository musicDefaultRepository;
    private final MusicPlayingRepository musicPlayingRepository;
    private final MusicPickRepository musicPickRepository;
    private final MusicVoteRepository musicVoteRepository;
    private final SessionBlackRepository sessionBlackRepository;

    public JusicInitializing(ConfigRepository configRepository, SessionRepository sessionRepository, MusicDefaultRepository musicDefaultRepository, MusicPlayingRepository musicPlayingRepository, MusicPickRepository musicPickRepository, MusicVoteRepository musicVoteRepository, JusicProperties jusicProperties, ResourceLoader resourceLoader, SessionBlackRepository sessionBlackRepository) {
        this.configRepository = configRepository;
        this.sessionRepository = sessionRepository;
        this.musicDefaultRepository = musicDefaultRepository;
        this.musicPlayingRepository = musicPlayingRepository;
        this.musicPickRepository = musicPickRepository;
        this.musicVoteRepository = musicVoteRepository;
        this.jusicProperties = jusicProperties;
        this.resourceLoader = resourceLoader;
        this.sessionBlackRepository = sessionBlackRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        clearSurvive();
        initialize();
    }

    /**
     * 读取默认列表
     *
     * @throws IOException -
     */
    private void initDefaultMusicId() throws IOException {
        InputStream inputStream = resourceLoader.getResource(jusicProperties.getDefaultMusicFile()).getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String musicId = "";
        // 逐行读取
        while ((musicId = bufferedReader.readLine()) != null) {
            jusicProperties.getDefaultList().add(musicId);
        }
    }

    /**
     * 初始化 config
     * 初始化 default
     */
    private void initialize() throws IOException {
        log.info("初始化工作开始");
        this.initDefaultMusicId();
        configRepository.initialize();
        musicDefaultRepository.initialize();
        log.info("初始化工作完成");
    }

    /**
     * 清理 session
     * 清理 config
     * 清理 default
     * 清理 playing
     * 清理 pick
     */
    private void clearSurvive() {
        log.info("清理工作开始");
        sessionRepository.destroy();
        sessionBlackRepository.destroy();
        configRepository.destroy();
        musicDefaultRepository.destroy();
        musicPlayingRepository.destroy();
        musicPickRepository.destroy();
        musicVoteRepository.destroy();
        log.info("清理工作完成");
    }
}
