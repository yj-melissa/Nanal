import { Link } from 'react-router-dom';
import emo_joy from '../../src_assets/img/emo_joy.png';

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
        <div className='p-2 my-2'>
          <div className='flex items-center p-2 m-1 mb-3'>
            <img
              src={emo_joy}
              alt='DALL:E2'
              className='w-16 h-16 p-1 rounded-lg'
            />
            <div className='px-1 m-1 text-sm justify-evenly max-w-160'>
              {/* 작성자 추가 필요 */}
              <span></span>
              <p className='truncate ...'>{item.content}</p>
            </div>
          </div>
        </div>
      </Link>
    </div>
  );
}

export default GroupDiaryItem;
