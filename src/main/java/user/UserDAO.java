package user;
//외부 라이브러리 추가
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private Connection conn;  //데이터베이스에 접근하기 위한 객체
    private PreparedStatement pstmt;  //SQL구문을 실행시키는 기능을 갖는 객체
    private ResultSet rs;  // 정보를 담을 수 있는 개체

    //실제로 mysql에 접속할 수 있게 해주는 부분
    public UserDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/bbs";
            String dbID = "root";
            String dbPassword = "1234";
            //드라이버는 mysql를 찾을 수 있게 하는 매개체 역할을 하는 하나의 라이브러리
            Class.forName("com.mysql.jdbc.Driver");
            //connection을 manager로 설정하고 mysql에 접속할 수 있도록 한다. conn객체 안에 담김
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();  //에러 발생시 내용 출력
        }
    }
    //실제로 로그인을 시도하는 함수
    public int login(String userID, String userPassword) {
        //실제로 sql에 명령할 문장 입력
        String SQL = "select userPassword from user where userID = ?";
        //변수가 들어갈 자리는 '?' 로 표시한다. 실행시에 ?에 대응되는 값은 setXXX메소드를 통해 설정한다.
        try {
            //정해진 sql문장을 데이터베이스에 삽입하는 형식으로 인스턴스 가져오기
            pstmt = conn.prepareStatement(SQL);
            //실제로 존재하는 정보인지를 데이터베이스에서 가져와서 확인
            pstmt.setString(1, userID);
            //select 실행 결과를 resultSet 객체에 담아서 리턴함
            rs = pstmt.executeQuery();  // select 사용시에만 executeQuery() 사용
            if(rs.next()) {  //다음 행이 존재하면 rs.next()는 true를 반환
                if(rs.getString(1).equals(userPassword)) { //'1'은 결과로 받은 userPassword임
                    return 1;  //로그인 성공
                } else {return 0;}  //비밀번호 불일치 알림
            }
            return -1;   //(결과가 존재하지 않는다면) 아이디가 없음을 알림
        } catch(Exception e) {
            e.printStackTrace();
        }
        return-2; //데이터베이스 오류
    }

    //회원가입
    public int join(User user) {
        String SQL= "insert into user values (?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserGender());
            pstmt.setString(5, user.getUserEmail());
            return pstmt.executeUpdate();  // NSERT / DELETE / UPDATE 관련 구문에서 executeUpdate() 사용
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; //데이터베이스 오류
    }
}
