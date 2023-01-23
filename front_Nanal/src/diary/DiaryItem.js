function DiaryItem({ id, content, group, created_at, onDelete }) {
  return (
    <div className="DiaryItem">
      <div className="info">
        <span>일기 번호 : {id}</span>
        <br />
        <span className="date">
          작성 시간 : {new Date(created_at).toLocaleDateString()}
        </span>
        <br />
        <span className="group">공개 여부 : {group}</span>
      </div>
      <div>일기 내용 : {content}</div>
      <button
        onClick={() => {
          if (window.confirm(`${id}번째 일기를 정말 삭제하시겠습니까?`)) {
            onDelete(id);
          }
        }}
      >
        삭제하기
      </button>
    </div>
  );
}

export default DiaryItem;
