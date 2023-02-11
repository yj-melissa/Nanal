import { Link } from 'react-router-dom';

function FriendItem({ item }) {
  return (
    <div>
      <Link to={`/Friend`} state={{ friendIdx: item.userIdx }}>
        <div className='flex bg-[#EBEBEB] border-1 border-slate-200/75 rounded-md m-1 mb-3 p-2'>
          <img
            src={item.img}
            className='inline-block w-1/4 p-1 mr-3 rounded-full h-1/4'
          ></img>
          <div className='my-2'>
            <p className='mb-1 font-bold'>{item.nickname}</p>
            <p className='break-all'>{item.introduction}</p>
          </div>
        </div>
      </Link>
    </div>
  );
}

export default FriendItem;
