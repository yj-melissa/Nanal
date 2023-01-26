import DiaryItem from "./DiaryItem";

function DiaryList({ diaryList }) {
  return (
    <div className="DiaryList">
      <h4>{diaryList.length}개의 일기가 있습니다.</h4>
      <div>
        {diaryList.map((it) => (
          <DiaryItem key={it.id} {...it} />
        ))}
      </div>
    </div>
  );
}

// undefined로 내려올 수도 있기에 props의 기본값 설정
DiaryList.defaultProps = {
  diaryList: [],
};

export default DiaryList;
