import { Link } from 'react-router-dom';

function GroupDiaryItem({ item, groupIdx }) {
  return (
    <div>
      <Link
        to={`/Group/Diary/${item.diaryIdx}`}
        state={{
          userIdx: item.userIdx,
          diaryIdx: item.diaryIdx,
          groupIdx: groupIdx,
          content: item.content,
          creationDate: item.creationDate,
          nickname: item.nickname,
        }}
      >
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
