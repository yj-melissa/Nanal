import { Link } from 'react-router-dom';
import emo_joy from '../../src_assets/img/emotion/emo_joy.png';

function DiaryItem({
  isToggle,
  groupIdx,
  nickname,
  diaryIdx,
  diaryDate,
  content,
}) {
  const diarydate = diaryDate.split('-');

  return (
    <Link
      to={`/Diary/Detail`}
      state={{
        diaryIdx: diaryIdx,
        isToggle: isToggle,
        groupIdx: groupIdx,
        diaryDate: diaryDate,
      }}
    >
      <div className='flex items-center p-2 m-1 mb-3'>
        {isToggle !== 0 ? (
          <img
            src={emo_joy}
            alt='DALL:E2'
            className='w-32 h-32 p-1 rounded-lg'
          />
        ) : (
          <img
            src={emo_joy}
            alt='DALL:E2'
            className='w-20 h-20 p-1 rounded-lg hover:translate-y-2'
          />
        )}
        <div className='w-3/4 px-1 m-1 text-lg text-right'>
          <p>
            <span className='mr-2'>감정</span>
            {isToggle === 1 ? (
              <span>
                {diarydate[0]}년 {diarydate[1]}월 {diarydate[2]}일
              </span>
            ) : (
              <span>
                {diarydate[0]}년 {diarydate[1]}월 {diarydate[2]}일
              </span>
            )}
          </p>
          <p>{isToggle === 2 ? <span>{nickname}</span> : <></>}</p>
          <p className='block font-bold truncate'>{content}</p>
        </div>
      </div>
    </Link>
  );
}

export default DiaryItem;
