import { dmSocket } from "@/socket-utils/dmSocket";

const { connect, send, subscribe, disconnect } = dmSocket;

export default function DmRoomPage() {
  connect(() => {
    console.log("WebSocket 연결...");

    subscribe(`/queue/receive/${roomId}`, (message) => {
      const data = JSON.parse(message.body);

      console.log("subscribe...");
    });
  });
}
