package hello.demo_project.connection;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoApi {

    private String accessToken = "test_access_token";

    public String payStart(long price){//결제 시작할게~
//        RestClient restClient = RestClient.builder().baseUrl(
//                "http://kakao.api/something"
//        ).build();
//
//         RestClient.RequestBodySpec response = restClient.post().body(access_token)
//                 .header("Content-Type", "application/json");
        //response -> "test_order_id"
        return "test_kakaoOrder_Id";
    };

    public ResponseEntity<String> payConfirm(String orderId){//야 사용자가 결제 했냐?
//        RestClient restClient = RestClient.builder().baseUrl(
//                "http://kakao.api/confirm"
//        ).build();
//
//        RestClient.RequestBodySpec response = restClient.post().body(access_token)
//                .header("Content-Type", "application/json");
        return ResponseEntity.ok("test_kakaoOrder_Id");
    }

    public ResponseEntity<String> payComplete(){//ㅇㅋ 나도 결제 승인~
//        RestClient restClient = RestClient.builder().baseUrl(
//                "http://kakao.api/complete"
//        ).build();
//
//        RestClient.RequestBodySpec response = restClient.post().body(access_token)
//                .header("Content-Type", "application/json");
        return ResponseEntity.ok("Success");
    }
}
