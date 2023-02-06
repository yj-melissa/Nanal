import { Link } from 'react-router-dom';

function GroupDiaryItem({ item }) {
  return (
    <div>
      <Link to={`/Diary/${item.diaryIdx}`} state={{ diaryIdx: item.diaryIdx }}>
        <div className='my-2 p-2'>
          {/* <div>Diary No. {diaryIdx}</div> */}
          <span className='box-content h-256 w-256'>그림</span>
          <div>
            <p className='truncate ...'>{item.content}</p>
          </div>
        </div>
      </Link>
    </div>
  );
}

export default GroupDiaryItem;
