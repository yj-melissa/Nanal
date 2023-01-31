import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios_api from "../../config/Axios";

function DiaryCreate() {
  // 일기, 그룹여부 데이터 받기
  const [content, setContent] = useState("");
  const [group, setGroup] = useState("private");
  // 포커싱 기능
  const contentRef = useRef();

  // 작성완료 버튼 누르면 실행되는 함수 - axios 사용해서 백엔드와 통신
  const handleSubmit = (e) => {
    e.preventDefault();
    // 유효성 검사 후 포커싱
    if (content.length < 2) {
      contentRef.current.focus();
      return;
    }
    axios_api
      .post("diary", {
        userIdx: 1,
        // 선택한 그룹은 배열 형태로 전달해야 함
        groupIdxList: [1],
        content: content,
      })
      .then((response) => {
        alert("저장 성공");
        // 일기 생성 후 홈으로 보내기
        navigate("/", { replace: true });
      })
      .catch((err) => {
        console.log(err);
      });

    // 저장 후 일기 데이터 초기화
    setContent("");
    setGroup("private");
  };

  // 뒤로가기 기능
  const navigate = useNavigate();

  return (
    <div className="border-solid border-1 border-black text-center p-10">
      <h2>오늘의 일기</h2>
      {/* 일기 내용 작성란 */}
      <div>
        <textarea
          className="mb-10 w-full h-min"
          name="content"
          ref={contentRef}
          value={content}
          onChange={(e) => {
            setContent(e.target.value);
          }}
        />
      </div>
      {/* 그룹 여부 선택란 */}
      <div>
        <select
          className="mb-10"
          name="group"
          value={group}
          onChange={(e) => {
            setGroup(e.target.value);
          }}
        >
          <option value="private">개인</option>
          {/* 추후에 그룹 선택 시, 그룹 목록을 보여줘야 함 */}
          <option value="group">그룹</option>
        </select>
      </div>
      {/* 작성 취소 및 완료 버튼 */}
      <div className="flex justify-between">
        <button onClick={() => navigate(-1)}>작성 취소</button>
        <button onClick={handleSubmit}>작성 완료</button>
      </div>
    </div>
  );
}

export default DiaryCreate;
