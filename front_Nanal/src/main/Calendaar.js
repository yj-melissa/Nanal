import React, { useState, useContext, useEffect } from "react";
import Calendar from "react-calendar";
import { DiaryStateContext } from "../App";
import "../Calendar.css";
import { Link, useNavigate } from "react-router-dom";
import DiaryList from "../diary/DiaryList";

function Calendaar() {
  const navigate = useNavigate();
  const [value, onChange] = useState(new Date());
  // const year = value.getFullYear();
  // const month = value.getMonth() + 1;
  // const date = value.getDate();
  // const curDate = [year, month, date].join("-");
  // console.log(curDate);

  const diaryList = useContext(DiaryStateContext);
  const [data, setData] = useState([]);

  useEffect(() => {
    if (diaryList.length >= 1) {
      const first = new Date(
        value.getFullYear(),
        value.getMonth(),
        1
      ).getTime();
      const last = new Date(
        value.getFullYear(),
        value.getMonth() + 1,
        0
      ).getTime();

      setData(diaryList.filter((it) => first <= it.date && it.date <= last));
    }
  }, [diaryList, value]);

  return (
    <div>
      캘린더 페이지 입니다. ||
      <Link to="/SignIn"> 로그인 </Link>
      {/* 일기쓰기 테스트용 */}
      <div className="border-none">
        <Calendar
          onChange={onChange}
          value={value}
          calendarType="ISO 8601"
          formatDay={(locale, date) =>
            date.toLocaleString("en", { day: "numeric" })
          }
        />
      </div>
      <br />
      <hr className="border-black" />
      <br />
      <DiaryList diaryList={data} />
      <div className="flex justify-center m-3">
        <button onClick={() => navigate("/New")}> 일기 쓰러 가기~! 🖊 </button>
      </div>
    </div>
  );
}

export default Calendaar;
