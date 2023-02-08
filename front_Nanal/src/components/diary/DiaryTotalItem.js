import { Link } from 'react-router-dom';
import emo_joy from '../../src_assets/img/emo_joy.png';

function DiaryTotalItem({ diaryIdx, diaryDate, content, emo }) {
  // 년, 월, 일만 추출
  const strYear = diaryDate.slice(0, 4);
  const strMonth = diaryDate.slice(5, 7);
  const strDay = diaryDate.slice(8, 10);

  return (
    <Link to={'/Diary/Detail'} state={{ diaryIdx: diaryIdx }}>
      <div className='flex items-center p-2 m-1 mb-3'>
        <img src={emo_joy} alt='DALL:E2' className='w-16 h-16 p-1 rounded-lg' />
        <div className='px-1 m-1 text-sm'>
          <p className='text-right'>
            <span className='mr-2'>{emo}</span>
            <span className='text-sm'>
              {strYear}년 {strMonth}월 {strDay}일
            </span>
          </p>
          <p className='text-right truncate block w-[156px] font-bold'>
            {content}
          </p>
        </div>
      </div>
    </Link>
  );
}

export default DiaryTotalItem;
