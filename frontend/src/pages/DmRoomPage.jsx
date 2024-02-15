import { dmSocket } from "@/socket-utils/dmSocket";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { request } from "@/apis/requestBuilder";
import Header from "../components/Header";
import Footer from "../components/Footer";

const { connect, send, subscribe, disconnect } = dmSocket;

// TODO: 추후 페이지 요청 시 넘겨받은 값으로 사용하도록 수정해야 함

export default function DmRoomPage() {
  const navigate = useNavigate();
  const { friendId } = useParams();
  const [dmHistory, setDmHistory] = useState([]);
  const [dmContent, setDmContent] = useState("");

  const friendUserId = 2;

  const getCookie = (name) => {
    const cookies = document.cookie.split('; ');
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i].trim();
      if (cookie.startsWith(name + '=')) {
        return cookie.substring(name.length + 1);
      }
    }
  };

  // 이전 DM 대화 내역 불러오기
  const fetchDmHistory = async () => {
    console.log("**************fetchDmHistory**************");
    const userId = getCookie("userId");

    const response = await request.post(`/dm/list`, {
      user_id: userId,
      friend_user_id: friendUserId,
    });
    console.log(response);
    const { data: dmHistory } = response;
    console.log(dmHistory);

    setDmHistory(dmHistory);
  };

  const connectDm = async () => {
    console.log("*********connectDM***********");

    connect(() => {
      console.log("**************WebSocket 연결...**************");

      subscribe(`/queue/receive/${friendId}`, (message) => {
        console.log("message: " + message);
        const data = JSON.parse(message.body);
        const { id, from_user_id, to_user_id, content, create_time } = data;
        const receivedMessage = { id, from_user_id, to_user_id, content, create_time }; // 새로 수신한 DM
        console.log(receivedMessage);
        setDmHistory((prevChat) => [...prevChat, receivedMessage]); // DM 내역에 새로 수신한 DM 추가
      });
    });
  };

  const sendDm = () => {
    console.log("*************sendDm**********");
    if (dmContent) {
      send(
        `/app/send/${friendId}`,
        {},
        JSON.stringify({
          from_user_id: userId,
          to_user_id: friendUserId,
          content: dmContent,
        }),
      );
      clearDmContent();
      setDmContent("");
    }
  };

  const handleDmContentChange = (e) => {
    setDmContent(e.target.value);
  };

  const clearDmContent = () => {
    setDmContent("");
  };

  const initialize = async () => {
    try {

      const userId = getCookie("userId");
      if(!userId) {
        alert("잘못된 접근입니다.");
        navigate("/");
      }

      await fetchDmHistory();
      await connectDm();
    } catch (e) {
      console.log("error occurred");
    }
  };

  useEffect(() => {
    initialize();

    // 정리 함수 (컴포넌트가 마운트 해제될 때 호출됨)
    return () => {
      // 소켓 연결 종료
      disconnect();
      console.log("Disconnected from WebSocket");
    };

    // eslint-disable-next-line
  }, []);

  return (
    <>
      <Header />
      <h1>DM ROOM : friendId {friendId}</h1>
      <hr />
      {dmHistory.map((item) => (
        <div key={item.id} style={{ border: "3px solid #010", margin: "10px", padding: "20px" }}>
          <div>id: {item.id}</div>
          <div>sender: {item.from_user_id}</div>
          <div>receiver: {item.to_user_id}</div>
          <div>content: {item.content}</div>
          <div>time: {item.create_time}</div>
        </div>
      ))}
      <hr />
      <div>
        <input id="dmContentInput" value={dmContent} onChange={handleDmContentChange} />
        <button id="dmSendBtn" onClick={sendDm}>
          SEND
        </button>
      </div>
      <Footer />
    </>
  );
}
