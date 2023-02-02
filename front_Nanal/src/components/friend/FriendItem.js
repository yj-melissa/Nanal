import { Link } from 'react-router-dom';

function FriendItem({ item }) {
  return (
    <div>
      <Link>
        <div>
          {item.nickname}
          <br />
          {item.img}
          <br />
          {item.introduction}
          <br />
          {item.shortContent}
          <br />
        </div>
      </Link>
    </div>
  );
}

export default FriendItem;
