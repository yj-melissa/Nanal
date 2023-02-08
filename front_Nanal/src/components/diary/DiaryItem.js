import { Link } from 'react-router-dom';
import emo_joy from '../../src_assets/img/emo_joy.png';

function DiaryItem({ nickname, diaryIdx, diaryDate, content }) {
  return (
    <Link
      to={`/Diary/Detail`}
      state={{
        diaryIdx: diaryIdx,
      }}
    >
      <div className='flex items-center p-2 m-1 mb-3'>
        <img
          src={emo_joy}
          alt='DALL:E2'
          className='w-16 h-16 p-1 rounded-lg hover:translate-y-2'
        />
        <div className='px-1 m-1 text-sm text-right'>
          <p>
            <span className='mr-2'>감정</span>
            <span className='text-sm'>{diaryDate}</span>
          </p>
          <p className='truncate block w-[156px] font-bold'>{content}</p>
        </div>
      </div>
    </Link>
  );
}

export default DiaryItem;
