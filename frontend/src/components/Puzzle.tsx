import React, { useRef, useEffect } from "react"

export default function CanvasDrag() {
  const canvas = useRef<HTMLCanvasElement>(null)
  const piece1 = useRef<HTMLImageElement>(null)
  let getCtx = null
  
  // 임시 퍼즐 조각
  // const piece1 = new Image()
  // const piece2 = new Image()
  // const piece3 = new Image()
  // piece1.src = "../assets/1.png"
  // piece2.src = "http://localhost:5173/2.png"
  // piece3.src = "http://localhost:5173/3.png"
  const canBoxes: Array<object> = [
    // { img: piece1, x: 190, y: 250, w: piece1.width * 0.5, h: piece1.height * 0.5 },
    // { img: piece2, x: 110, y: 115, w: piece2.width * 0.5, h: piece2.height * 0.5 },
    // { img: piece3, x: 250, y: 230, w: piece3.width * 0.5, h: piece3.height * 0.5 },
  ]

  let isMoveDown = false
  let targetCanvas = null
  let startX = null
  let startY = null

  useEffect(() => {
    const canvasDimensions = canvas.current
    if (canvasDimensions) {
      canvasDimensions.width = canvasDimensions.clientWidth
      canvasDimensions.height = canvasDimensions.clientHeight
      getCtx = canvasDimensions.getContext("2d")
    }

    if (piece1.current) {
      piece1.current.src = "http://localhost:5173/1.png"

      canBoxes.push({ img: piece1.current, x: 190, y: 250, w: piece1.current.width * 0.5, h: piece1.current.height * 0.5 })
    }

    
  }, [])

  useEffect(() => {
    canvasDraw()
  }, [])

  const canvasDraw = () => {
    getCtx.clearRect(
      0,
      0,
      canvas.current.clientWidth,
      canvas.current.clientHeight,
    )
    canBoxes.map((info) => fillCanvas(info))
  }

  const fillCanvas = (info) => {
    const { img, x, y, w, h } = info
    getCtx.drawImage(img, x, y, w, h)
  }

  const moveableItem = (x: number, y: number) => {
    let isCanvasTarget = null
    for (let i = 0; i < canBoxes.length; i++) {
      const block = canBoxes[i]
      if (
        x >= block.x &&
        x <= block.x + block.w &&
        y >= block.y &&
        y <= block.y + block.h
      ) {
        targetCanvas = block
        isCanvasTarget = true
        break
      }
    }
    return isCanvasTarget
  }

  const onMouseDown = (e) => {
    startX = parseInt(e.nativeEvent.offsetX - canvas.current.clientLeft)
    startY = parseInt(e.nativeEvent.offsetY - canvas.current.clientTop)
    isMoveDown = moveableItem(startX, startY)
  }

  const onMouseMove = (e) => {
    if (!isMoveDown) return
    const mouseX = parseInt(e.nativeEvent.offsetX - canvas.current.clientLeft)
    const mouseY = parseInt(e.nativeEvent.offsetY - canvas.current.clientTop)
    const mouseStartX = mouseX - startX
    const mouseStartY = mouseY - startY
    startX = mouseX
    startY = mouseY
    targetCanvas.x += mouseStartX
    targetCanvas.y += mouseStartY
    canvasDraw()
  }

  const onMouseUp = (e) => {
    targetCanvas = null
    isMoveDown = false
  }

  const onMouseOut = (e) => {
    onMouseUp(e)
  }

  return (
    <div>
      <h2>Puzzle Pop</h2>
      <canvas
        onMouseDown={onMouseDown}
        onMouseMove={onMouseMove}
        onMouseUp={onMouseUp}
        onMouseOut={onMouseOut}
        ref={canvas}
        width={screen.width * 0.8}
        height={screen.height}
        style={{backgroundColor: "lightgray"}}
      ></canvas>
    </div>
  )
}