import { useNavigate } from "react-router-dom";

function DiaryItem({ id, date, content, group }) {
  const strDate = new Date(parseInt(date)).toLocaleString();
  const navigate = useNavigate();
  const goDetail = () => {
    navigate(`/diary/${id}`);
  };

  return (
    <div className="DiaryItem">
      <div onClick={goDetail} className="picture">
        그림 넣기
      </div>
      <div onClick={goDetail} className="info-wrapper">
        <div className="diary-date">{strDate}</div>
        <div className="diary-content-preview">{content.slice(0, 25)}</div>
      </div>
    </div>
  );
}

export default DiaryItem;
