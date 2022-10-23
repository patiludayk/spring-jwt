package com.uday.spring.springjwt.util;

import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalTest {
    public static void main(String[] args) {
        final String secret = TextCodec.BASE64.encode("uday's-secret");
        log.info("encoding of secret 'uday's-secret' : {}", secret);

        log.info("in byte[]: {}", TextCodec.BASE64.decode(secret));
        log.info("in string : {}", TextCodec.BASE64.decodeToString(secret));
    }
}
