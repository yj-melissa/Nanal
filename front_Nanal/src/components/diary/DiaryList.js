import { useState, useEffect } from "react";
import DiaryItem from "./DiaryItem";
import axios_api from "../../config/Axios";
// https://jsonplaceholder.typicode.com/comments

function DiaryList() {
  // 백엔드에서 데이터 받기
  // useEffect(() => {
  //   axios_api
  //     .get("diary/list/date/{date}")
  //     .then((res) => console.log(res))
  //     .catch((err) => console.log(err));
  // });
  // 데이터 받아서 사용하기
  const [diary, setDiary] = useState([{}]);

  return (
    <div className="DiaryList">
      <h4>날짜별 N개의 일기가 있습니다.</h4>
    </div>
  );
}

export default DiaryList;
