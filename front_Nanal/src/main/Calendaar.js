import React, { useState } from "react";
import Calendar from "react-calendar";
import "../Calendar.css";
import { Link } from "react-router-dom";
import DiaryList from "../diary/DiaryList";

function Calendaar() {
  const [value, onChange] = useState(new Date());

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
      <DiaryList />
      <div className="flex justify-center">
        <Link to="/New" className="m-3">
          일기 쓰러 가기~! 🖊
        </Link>
      </div>
    </div>
  );
}

export default Calendaar;
