import { useParams } from "react-router-dom";
import Header from "../components/Header";
import UserAPI from "../apis/CustomUserAPI";
import RecordAPI from "../apis/CustomRecordAPI";
import { useEffect, useState } from "react";
import styled from "styled-components";
import backgroundPath from "@/assets/backgrounds/background.gif";


export default function AboutGamePage() {
  const { userId } = useParams();
  const [user, setUser] = useState({});
  const [users, setUsers] = useState([]);
  const [searchEmail, setSearchEmail] = useState("");
  const [searchNickName, setSearchNickName] = useState("");
  const [searchedUsersByEmail, setSearchedUsersByEmail] = useState([]);
  const [searchedUsersByNickName, setSearchedUsersByNickName] = useState([]);
  
  const searchUsersByEmail = async () => {
    // UserController 매개변수 변경 예정
    await UserAPI.searchUserByEmail(searchEmail)
      .then(response => setSearchedUsersByEmail(response))
      .catch(error => console.debug(error));
  };

  const searchUsersByNickName = async () => {
    // UserController 매개변수 변경 예정
    await UserAPI.searchUsersByNickName(searchNickName)
      .then(response => setSearchedUsersByNickName(response))
      .catch(error => console.debug(error));
  };

  useEffect(() => {
    // UserController 매개변수 변경 예정
    UserAPI.fetchUser(userId)
      .then(response => setUser(response))
      .catch(error => console.debug(error));

    // 302 FOUND 오류 추후 OK로 변경 예정
    UserAPI.fetchUsers()
      .then(response => setUsers(response))
      .catch(error => console.debug(error));
  }, [userId]);



  const [recordId, setRecordId] = useState(1);
  const [record, setRecord] = useState({});
  const [records, setRecords] = useState([]);
  const [recordInfo, setRecordInfo] = useState({});

  const getRecordById = async () => {    
    // DB 데이터 없어서 현재 오류남 (RecordId = 1)
    RecordAPI.fetchRecord(recordId)
      .then(response => setRecord(response))
      .catch(error => console.debug(error));
  };

  useEffect(() => {
    
  }, []);

  return (
    <Wrapper>
      <Header />
      <style>{`
        .box {
          width: 100%;
          height: 150px;
        }
        .container {
          display: flex;
          flex-direction: column;
          align-items: center;
          text-align: center;
          padding: 20px;
        }
        .left-box {
          width: 550px;
          height: 50px;
        }
        .search-container {
          display: flex;
          justify-content: center;
          align-items: center;
          margin-top: 30px;
        }
        .search-container input[type="text"] {
          margin-right: 20px;
          padding: 20px;
          font-size: 20px;
          border-radius: 10px;
          border: 2px solid #ccc;
        }
        .search-container button {
          padding: 20px 40px;
          font-size: 20px;
          border-radius: 10px;
          border: none;
          background-color: #007bff;
          color: #fff;
          cursor: pointer;
        }
        .user-container {
          display: flex;
          justify-content: center;
          align-items: center;
          margin-top: 30px;
          width: 100%;
          height: 100%;
        }
        .user-container div {
          margin: 20px;
        }
        .user-info {
          background: white;
          padding: 40px;
          border: 2px solid #ccc;
          border-radius: 20px;
          box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.1);
          height: 70%;
          width : 80%;
          display: flex;
          flex-direction: column;
          justify-content: center; /* 수평 가운데 정렬 */
          align-items: center; /* 수직 가운데 정렬 */
          background: rgba(255, 255, 255, 0.7);
          font-size: 24px;
            font-weight: bold;
        }
        .user-info img {
          width: 300px;
          height: auto;
        }
      `}</style>
      <div className="box"></div>
      <div className="container">
        
        <div className="user-container">
          <div className="user-info">

            <div>asdf</div>
          </div>
        </div>
      </div>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  height: 1000px;
  background-image: url(${backgroundPath});
`;
