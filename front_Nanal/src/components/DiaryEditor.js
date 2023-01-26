// 일기 작성, 수정 부분에서 사용할 컴포넌트
import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios_api from "../config/Axios";

// 오늘 날짜를 YYYY-MM-DDTHH:mm:ss 형태로 변환해주는 함수
const getStringDate = (date) => {
  return date.toISOString().slice(0, 10);
};

function DiaryEditor({ onCreate }) {
  const [date, setDate] = useState(getStringDate(new Date()));
  const [content, setContent] = useState("");
  const [group, setGroup] = useState("private");

  // 한 글자도 적지 않았을 때 작성완료를 누른 경우 작성 창 포커스 해주기
  const contentRef = useRef();

  // 일기작성 완료 버튼 클릭 시 실행되는 함수
  const onSubmit = (e) => {
    e.preventDefault();

    // 일기 길이 검사 후 통과 못하면 포커싱
    if (content.length < 2) {
      contentRef.current.focus();
      return;
    }
    // 비동기 통신 - 일기 생성하기
    axios_api
      .post("diary", {
        content: content,
        creationDate: date,
        groupIdxList: [1],
        userIdx: 1,
      })
      .then((response) => {
        console.log(response.data.data.diary.diaryIdx);
        onCreate(date, content, group);
        alert("저장 성공");
        navigate("/", { replace: true });
      })
      .catch(console.log("no"));

    // 저장 후 일기 데이터 초기화
    setContent("");
    setGroup("private");
  };

  // 뒤로가기
  const navigate = useNavigate();

  return (
    <div className="text-center text-base border-solid border-2 border-black p-20 cursor-pointer">
      <div>
        {/* 날짜 선택란 */}
        <section>
          <div className="max-w-xs">
            <input
              value={date}
              onChange={(e) => setDate(e.target.value)}
              type="date"
            />
          </div>
        </section>
        <br />
        {/* 일기 작성란 */}
        <section>
          <div className="text-wrapper">
            <textarea
              name="content"
              className="min-h-200 resize-y"
              placeholder="일기를 작성해주세요."
              ref={contentRef}
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </div>
        </section>
        <br />
        {/* 그룹 설정 */}
        <section>
          <div className="option-box">
            <h4>그룹 설정</h4>
            <select
              name="group"
              value={group}
              onChange={(e) => setGroup(e.target.value)}
            >
              <option key="private" value="private">
                개인
              </option>
              <option key="group" value="group">
                그룹
              </option>
            </select>
          </div>
        </section>
        <br />
        {/* 작성완료 버튼 */}
        <section>
          <div className="flex justify-between items-center">
            <button onClick={() => navigate(-1)}>취소 하기</button>
            <button onClick={onSubmit}>작성 완료</button>
          </div>
        </section>
      </div>
    </div>
  );
}

export default React.memo(DiaryEditor);
