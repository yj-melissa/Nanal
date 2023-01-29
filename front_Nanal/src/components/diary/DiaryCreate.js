import React, { useState, useRef } from "react";
import DiaryList from "./DiaryList";

function DiaryCreate() {
  // 일기 데이터는 부모 컴포넌트에서 관리 (데이터는 단방향으로 흐르기 떄문)
  // const [data, setData] = useState([]);
  const [data, setData] = useState([]);
  // id는 useRef로 생성
  const dataId = useRef(0);

  const onCreate = (content, group) => {
    const created_at = new Date().getTime();
    const newItem = {
      id: dataId.current,
      content,
      group,
      created_at,
    };
    dataId.current += 1;
    // 새로운 일기를 가장 위로 전달
    setData([newItem, ...data]);
  };

  // 일기 삭제 함수 - id 다른 것들은 삭제에서 제외하여 새로운 배열로 표현
  const onRemove = (targetId) => {
    const newDiaryList = data.filter((it) => it.id !== targetId);
    setData(newDiaryList);
  };

  // 일기 수정 함수 - id 같은 것을 수정
  const onEdit = (targetId, newContent) => {
    setData(
      data.map((it) =>
        it.id === targetId ? { ...it, content: newContent } : it
      )
    );
  };

  return (
    <div>
      <h2>오늘의 일기</h2>
      <DiaryList diaryList={data} onRemove={onRemove} onEdit={onEdit} />
    </div>
  );
}

export default DiaryCreate;
