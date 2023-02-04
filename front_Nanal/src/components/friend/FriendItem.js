import { Link } from 'react-router-dom';

function FriendItem({ item }) {
  return (
    <div>
      <Link to={`/Friend/${item.userIdx}`} state={{ friendIdx: item.userIdx }}>
        <div>
          {item.nickname}
          <br />
          {item.img}
          <br />
          {item.introduction}
          <br />
          {item.shortContent}
          <br />
          <br />
        </div>
      </Link>
    </div>
  );
}

export default FriendItem;
