import { Link } from 'react-router-dom';
import emo_joy from '../../src_assets/img/emo_joy.png';

function DiaryItem({ nickname, diaryIdx, diaryDate, content }) {
  // 월, 일만 추출
  const strMonth = diaryDate.slice(5, 7);
  const strDay = diaryDate.slice(8, 10);

  return (
    <Link
      to={`/Diary/Detail`}
      state={{
        diaryIdx: diaryIdx,
      }}
    >
      <div className='w-full'>
        <div>Diary No. {diaryIdx}</div>
        <img
          src={emo_joy}
          alt='DALL:E2'
          className='inline-block w-16 h-16 p-1 rounded-lg'
        ></img>
        <div className='inline-block px-1 m-1 break-words'>
          <span>{nickname}</span>
          <span>감정</span>
          <span className='text-sm'>
            {strMonth}월 {strDay}일
          </span>
          <div className='inline-block truncate ...'>{content}</div>
        </div>
      </div>
    </Link>
  );
}

export default DiaryItem;
