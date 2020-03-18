package com.rizomm.m2.rooforall.rooforall.security;

public class JwtProperties {
    public static final String SECRET = "SomeSecretForJWTGeneration";
    public static final int EXPIRATION_TIME = 10_000_000; // 4200 = 1 hour ? // 10 days = 10_000_000
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
