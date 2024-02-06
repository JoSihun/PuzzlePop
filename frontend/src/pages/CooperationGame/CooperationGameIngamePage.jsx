import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import PlayPuzzle from "../../components/PlayPuzzle";
import { getRoomId, getSender } from "../../socket-utils/storage";
import { socket } from "../../socket-utils/socket";
import { parsePuzzleShapes } from "../../socket-utils/parsePuzzleShapes";
import { config } from "../../components/PlayPuzzle/PuzzleCanvas/Puzzle/MovePuzzle";
import { Point } from "paper/dist/paper-core";

const { connect, send, subscribe, disconnect } = socket;

export default function CooperationGameIngamePage() {
  const navigate = useNavigate();
  const { roomId } = useParams();
  const [loading, setLoading] = useState(true);
  const [gameData, setGameData] = useState(null);

  const lockPuzzle = (x, y, index) => {
    console.log(x, y, index);
    // TODO: "Lock"이 걸려있다는 처리해야함
    // 피그마처럼 유저별로 "색깔"을 지정해두고 border 색깔을 변경하는 것도 좋을듯?
  };

  const movePuzzle = (x, y, index) => {
    const { tiles } = config;
    tiles[index].position = new Point(x, y);
  };

  const unLockPuzzle = (x, y, index) => {
    console.log(x, y, index);
    // TODO: 여기서 Lock에 대한 UI처리를 해제한다.
  };

  const addPeice = (fromIndex, toIndex) => {
    console.log(fromIndex, toIndex);
  };

  const connectSocket = async () => {
    // websocket 연결 시도
    connect(
      () => {
        // console.log("WebSocket 연결 성공");

        subscribe(`/topic/game/room/${roomId}`, (message) => {
          const data = JSON.parse(message.body);
          console.log(data);

          // 1. 게임이 끝나면 대기실 화면으로 보낸다.
          //여기 수정함
          if (data.finished === true && data.gameId) {
            window.alert("게임이 종료되었습니다.");
            window.location.href = `/game/cooperation/waiting/${data.gameId}`;
            return;
          }

          // 2. 게임정보 받기
          if (data.gameType && data.gameType === "COOPERATION") {
            setGameData(data);
            return;
          }

          if (data.message && data.message === "LOCKED") {
            const { targets } = data;
            const { x, y, index } = JSON.parse(targets.slice(1, -1));
            lockPuzzle(x, y, index);
            return;
          }

          if (data.message && data.message === "MOVE") {
            const { targets } = data;
            const { x, y, index } = JSON.parse(targets.slice(1, -1));
            movePuzzle(x, y, index);
            return;
          }

          if (data.message && data.message === "UNLOCKED") {
            const { targets } = data;
            const { x, y, index } = JSON.parse(targets.slice(1, -1));
            unLockPuzzle(x, y, index);
            return;
          }

          if (data.message && data.message === "ADD_PIECE") {
            const { targets } = data;
            const [fromIndex, toIndex] = targets.split(",").map((piece) => Number(piece));
            addPeice(fromIndex, toIndex);
            return;
          }

          //랜덤 아이템 드랍(사실 배틀에 있어야하는데 여기서 테스트)
          if (data.randomItem) {
            // 버튼 생성
            const button = document.createElement("button");
            button.textContent = data.randomItem.name;

            // 버튼의 위치 설정
            button.style.position = "absolute";
            button.style.left = data.randomItem.position_x + "px";
            button.style.top = data.randomItem.position_y + "px";

            button.onclick = function () {
              // 부모 요소로부터 버튼 제거
              //근데 이거 다른 클라이언트들도 이 아이템 먹었다고 버튼 사라지는 이벤트 처리하든가 해야함.
              button.parentNode.removeChild(button);

              // 서버로 메시지 전송
              send(
                "/app/game/message",
                {},
                JSON.stringify({
                  type: "GAME",
                  roomId: getRoomId(),
                  sender: getSender(),
                  message: "USE_RANDOM_ITEM",
                  targets: data.randomItem.uuid,
                }),
              );
            };

            // 버튼을 body에 추가
            document.body.appendChild(button);

            // alert 대신 메시지를 콘솔에 출력
            console.log(
              data.randomItem.name +
                " 을 " +
                data.randomItem.position_x +
                " " +
                data.randomItem.position_y +
                " 에 생성한다!",
            );
          }
        });

        // 서버로 메시지 전송
        send(
          "/app/game/message",
          {},
          JSON.stringify({
            type: "ENTER",
            roomId: getRoomId(),
            sender: getSender(),
          }),
        );
      },
      () => {
        window.alert("게임이 종료되었거나 입장할 수 없습니다.");
        navigate(`/game/cooperation`, {
          replace: true,
        });
      },
    );
  };

  useEffect(() => {
    if (roomId !== getRoomId() || !getSender()) {
      navigate("/game/cooperation", {
        replace: true,
      });
      return;
    }

    connectSocket();
    setLoading(false);

    return () => {
      disconnect();
    };

    // eslint-disable-next-line
  }, []);

  if (loading) {
    return <h1>게임 정보를 받아오는 중...</h1>;
  }

  return (
    <>
      <h1>CooperationGameIngamePage : {roomId}</h1>
      {gameData && gameData.redPuzzle && gameData.redPuzzle.board && (
        <PlayPuzzle shapes={parsePuzzleShapes(gameData.redPuzzle.board)} />
      )}
    </>
  );
}
