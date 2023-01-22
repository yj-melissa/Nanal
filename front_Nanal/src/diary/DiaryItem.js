function DiaryItem({ id, content, group, created_at }) {
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
    </div>
  );
}

export default DiaryItem;
