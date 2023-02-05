import { Link } from 'react-router-dom';

function FriendItem({ item }) {
  return (
    <div>
      <Link to={`/Friend/${item.userIdx}`} state={{ friendIdx: item.userIdx }}>
        <div className='bg-[#EBEBEB] border-2 border-solid border-slate-400 rounded-lg m-1 mb-3 p-2'>
          {item.nickname}
          <br />
          <img
            src={item.img}
            className='w-[80px] h-[80px] inline-block float-left m-1 mr-2'
          ></img>
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
