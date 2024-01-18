import { useEffect, useRef } from "react";
import Paper from "paper";

export default function PuzzleCanvas() {
  const canvasRef = useRef(null);

  useEffect(() => {
    if (!canvasRef.current) {
      return;
    }

    // Paper.js setup
    Paper.setup(canvasRef.current);

    // 이런 식으로 여러 개의 퍼즐을 만든다.
    const puzzle = new Paper.Path.RegularPolygon(new Paper.Point(80, 70), 8, 30); // x, y, shape, size
    puzzle.fillColor = "#cbc8c8";
    puzzle.selected = false;

    // 각 퍼즐에 이벤트 핸들러 붙이기
    puzzle.onMouseEnter = () => {
      canvasRef.current.style.cursor = "pointer";

      puzzle.style.strokeColor = "green";
      puzzle.style.strokeWidth = "3";
    };

    puzzle.onMouseLeave = () => {
      canvasRef.current.style.cursor = "default";

      puzzle.style.strokeColor = "black";
      puzzle.style.strokeWidth = "1";
    };

    puzzle.onMouseDrag = (event) => {
      const { x: dx, y: dy } = event.delta;
      puzzle.position.x += dx;
      puzzle.position.y += dy;

      console.log(puzzle.position);
      // 이제 짝이 맞는 퍼즐 근처에 온다면 ... ?
    };

    console.log(puzzle);
  }, []);

  return <canvas ref={canvasRef} style={{ width: "100%" }}></canvas>;
}
