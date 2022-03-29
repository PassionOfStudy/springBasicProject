package com.hanghae99.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class BoardApplication {

    // 서버의 시간을 Asia/Seoul시간으로 맞춰주는 기능
    @PostConstruct
    public void started() { System.setProperty("user.timezone", "Asia/Seoul");
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        TimeZone.setDefault(tz);
        System.out.println("현재시각:" + new Date());
        System.out.println(
                ZonedDateTime.now(ZoneId.of("Asia/Seoul"))); }

    public static void main(String[] args) {

        SpringApplication.run(BoardApplication.class, args);
    }
}