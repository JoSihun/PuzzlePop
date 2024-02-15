import { useCallback, useEffect, useMemo, useRef, useState } from "react";

const defaultValue = {
  afterStartCallback: () => {},
  defaultTime: 3,
};

export const useGameStart = ({ afterStartCallback, defaultTime } = defaultValue) => {
  const [time, setTime] = useState(defaultTime);
  const intervalRef = useRef(null);

  const isShow = useMemo(() => time < defaultTime, [time, defaultTime]);

  const onStart = useCallback(() => {
    intervalRef.current = setInterval(() => {
      setTime((prev) => prev - 1);
    }, 1000);
  }, []);

  useEffect(() => {
    if (time === 0) {
      afterStartCallback();
    }

    // 컴포넌트가 언마운트될 때 clearInterval 호출하여 메모리 누수 방지
    return () => {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
      }
    };
  }, [afterStartCallback, time]); // useEffect가 처음 한 번만 실행되도록 빈 배열을 전달

  return { time, isShow, onStart };
};
