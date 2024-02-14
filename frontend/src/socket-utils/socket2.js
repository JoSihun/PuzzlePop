import * as StompJS from "@stomp/stompjs";

// const { VITE_SOCKET_SERVER_END_POINT, VITE_DEV_SOCKET_SERVER_END_POINT } = import.meta.env;
const SERVER_END_POINT = import.meta.env.DEV
  ? "ws://localhost:8080"
  : "wss://i10a304.p.ssafy.io/api";
const SOCKET_END_POINT = `${SERVER_END_POINT}/game`;

export default function createSocket(roomId) {
  let stomp = null;

  const connect = (onConnect) => {
    if (!stomp) {
      stomp = new StompJS.Client({
        brokerURL: `${SOCKET_END_POINT}/${roomId}`,
        onConnect,
        connectHeaders: {},
      });
    }

    stomp.activate();
  };

  const send = (destination, obj, body) => {
    if (!stomp) {
      return;
    }

    stomp.publish({
      destination,
      body,
    });
  };

  const subscribe = (destination, cb) => {
    if (!stomp) {
      return;
    }

    stomp.subscribe(destination, cb);
  };

  const disconnect = () => {
    if (!stomp) {
      return;
    }

    stomp;
  };

  return {
    stomp,
    connect,
    send,
    subscribe,
    disconnect,
  };
}
