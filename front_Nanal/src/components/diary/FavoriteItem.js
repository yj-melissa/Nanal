import { Link } from 'react-router-dom';

function FavoriteItem({ diaryIdx, content }) {
  // const strDate = new Date(creationDate).toLocaleString();

  return (
    <Link to={`/Diary/${diaryIdx}`} state={{ diaryIdx: diaryIdx }}>
      <div>
        <span>그림 들어갈 자리</span>
        <div>{content}</div>
      </div>
    </Link>
  );
}

export default FavoriteItem;
