package com.mysql.protocol.tool;

import lombok.SneakyThrows;

import java.security.MessageDigest;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 23:34
 */
/**
 * 针对mysql "Secure Password Authentication"方式的实现，mysql支持5中密码验证方式., 其中"Secure Password Authentication"
 * 是比较安全的一种，又分为插件支持和非插件支持，我们实现 非插件支持的Authentication::Native41方式.
 *
 * @see <a href="http://dev.mysql.com/doc/internals/en/authentication-method.html">http://dev.mysql.com/doc/
 *      internals/en/authentication-method.html</a>
 * @see <a href=
 *      "http://dev.mysql.com/doc/internals/en/secure-password-authentication.html#packet-Authentication::Native41">
 *      http://dev.mysql.com/doc/internals/en/secure-password-authentication.html#packet-Authentication::Native41
 *      </a>
 */
public class PassSecure {

    @SneakyThrows
    public static byte[] nativeSecure(String password, byte[] seed) {
        byte[] bs = password.getBytes("utf-8");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

        return scramble411(bs, seed, messageDigest);


    }

    private static byte[] scramble411(byte[] bs, byte[] seed, MessageDigest messageDigest) {
        byte[] password1 = messageDigest.digest(bs);
        messageDigest.reset();
        byte[] password2 = messageDigest.digest(password1);
        messageDigest.reset();

        messageDigest.update(seed);
        messageDigest.update(password2);

        byte[] result = messageDigest.digest();
        int num = result.length;
        for (int i = 0; i < num; i++) {
            result[i] = (byte) (result[i] ^ password1[i]);
        }
        return result;
    }
}
