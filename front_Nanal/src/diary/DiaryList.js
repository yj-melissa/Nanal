import DiaryItem from "./DiaryItem";

function DiaryList({ diaryList, onRemove, onEdit }) {
  return (
    <div className="DiaryList">
      <h2>일기 리스트</h2>
      <h4>{diaryList.length}개의 일기가 있습니다.</h4>
      <div>
        {diaryList.map((it) => (
          // 작성되는 일기를 prop으로 보내기
          <DiaryItem key={it.id} {...it} onRemove={onRemove} onEdit={onEdit} />
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
