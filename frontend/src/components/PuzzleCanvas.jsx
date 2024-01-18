import { useEffect, useRef } from "react";
import Paper from "paper";

export default function PuzzleCanvas() {
  const canvasRef = useRef(null);

  useEffect(() => {
    if (!canvasRef.current) {
      return;
    }

    Paper.setup(canvasRef.current);
    const path = new Paper.Path();
    path.strokeColor = "black";

    // This function is called whenever the user
    // clicks the mouse in the view:
    function onMouseDown(event) {
      // Add a segment to the path at the position of the mouse:
      path.add(event.point);
    }

    canvasRef.current.addEventListener("mousedown", () => {
      console.log("hi");
    });
  }, []);

  return <canvas ref={canvasRef} style={{ width: "100%" }}></canvas>;
}
