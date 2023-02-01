import { Link } from "react-router-dom";

function FavoriteItem({ diaryIdx }) {
  // 나중에 그림 들어와야 함
  return (
    <Link to={`/Diary/${diaryIdx}`} state={{ diaryIdx: diaryIdx }}>
      <div>
        <span>그림 들어갈 자리</span>
      </div>
    </Link>
  );
}

export default FavoriteItem;
