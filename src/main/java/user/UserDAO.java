package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//외부 라이브러리 추가
public class UserDAO {
    private Connection conn;  //데이터베이스에 접근하기 위한 개체
    private PreparedStatement pstmt;  //
    private ResultSet rs;  // 정보를 담을 수 있는 개체

    //실제로 mysql에 접속할 수 있게 해주는 부분
    public UserDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/bbs";
            String dbID = "root";
            String dbPassword = "1234";
            //드라이버는 mysql를 찾을 수 있게 하는 매개체 역할을 하는 하나의 라이브러리
            Class.forName("com.mysql.jdbc.Driver");
            //connection을 manager로 설정하고 mysql에 접속할 수 있도록 한다. conn개체 안에 담김
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch(Exception e) {
            e.printStackTrace();  //에러 발생시 내용 출력
        }
    }
    //실제로 로그인을 시도하늖 함수
    public int login(String userID, String userPassword) {
        //실제로 sql에 명령할 문장 입력
        String SQL = "select userPassword from user where userID = ?";
        try {
            //정해진 sql문장을 데이터베이스에 삽입하는 형식으로 인스턴스 가져오기
            pstmt = conn.prepareStatement(SQL);
            //실제로 존재하는 정보인지를 데이터베이스에서 가져와서 확인
            pstmt.setString(1, userID);
            //실행한 결과 담기
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getString(1).equals(userPassword)) {
                    return 1;  //로그인 성공
                } else {return 0;}  //비밀번호 불일치 알림
            }
            return -1;   //아이디가 없음을 알림
        } catch(Exception e) {
            e.printStackTrace();
        }
        return-2; //데이터베이스 오류
    }
}
