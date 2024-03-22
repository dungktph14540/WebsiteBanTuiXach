package com.example.websildebag.service;

import com.example.websildebag.repository.RoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "63713DE1666A2BB2CEBC4B3C26515C95672546A6AB9A37DAD88CCBB342";
    private final RoleRepository roleRepository;
    public String extractUsername(String token) {
        return extrachClaims(token, Claims::getSubject);
    }

    public <T> T extrachClaims(String token, Function<Claims, T> claimsResolver){ // trich xuat thong tin tu payload
        final Claims claims = extrackAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        List<String> roles = roleRepository.getRoleName(userDetails.getUsername());

        Map<String, Object> claims = new HashMap<>();
        if (!roles.isEmpty()) {
            claims.put("rolename", roles.get(0));
        }

        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInket(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String gennerateToken(Map<String,Object> extraClaims, UserDetails userDetails){
//        return Jwts.builder()
//                .setClaims(extraClaims)  //Thiết lập các thông tin cụ thể (claims) từ extraClaims. Đây có thể là bất kỳ thông tin nào bạn muốn bổ sung vào payload của token.
//                .setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())) //Thiết lập Subject (chủ thể) của token bằng cách sử dụng tên người dùng từ đối tượng UserDetails.
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //Thiết lập thời điểm hết hạn của token. Trong trường hợp này, bạn thiết lập thời điểm hết hạn là 24 giờ
//                .signWith(getSignInket(), SignatureAlgorithm.HS256)//Ký (sign) token bằng cách sử dụng khoá bí mật (khoá HMAC-SHA) từ getSignInket() và thuật toán HS256. Điều này đảm bảo tính toàn vẹn của token.
//                .compact();//Chuyển đổi các thông tin đã thiết lập thành một chuỗi token JWT hoàn chỉnh.
//    }
    private Claims extrackAllClaims(String token){
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInket())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}

    private boolean isTokenExpired(String token) {
        return extrachExpiration(token).before(new Date());
    }

    private Date extrachExpiration(String token) {
        return extrachClaims(token,Claims::getExpiration);
    }

    private Key getSignInket() {
        byte[]  keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
