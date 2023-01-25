import { useContext } from "react";
import { DiaryStateContext } from "./DiaryCreate";
import DiaryItem from "./DiaryItem";

function DiaryList() {
  // context에서 데이터 가져와 사용
  const diaryList = useContext(DiaryStateContext);

  return (
    <div className="DiaryList">
      <h2>일기 리스트</h2>
      <div>
        {diaryList.map((it) => (
          // 작성되는 일기를 prop으로 보내기
          <DiaryItem key={it.id} {...it} />
        ))}
      </div>
    </div>
  );
}

// undefined로 내려올 수도 있는 props의 기본값 설정
DiaryList.defaultProps = {
  diaryList: [],
};

export default DiaryList;
