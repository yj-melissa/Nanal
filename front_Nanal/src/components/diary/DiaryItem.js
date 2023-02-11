import { Link } from 'react-router-dom';

function DiaryItem({
  isToggle,
  groupIdx,
  diaryIdx,
  diaryDate,
  nickname,
  emo,
  picture,
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
      }}
    >
      <div className='flex items-center p-2 m-1 mb-3'>
        {isToggle !== 0 ? (
          <img src={emo} alt='DALL:E2' className='w-16 h-16 p-1 rounded-lg' />
        ) : (
          <img
            src={emo}
            alt='DALL:E2'
            className='w-16 h-16 p-1 rounded-lg hover:translate-y-2'
          />
        )}
        <div className='px-1 m-1 text-sm text-right'>
          <div>
            <p>
              <span className='mr-2'>감정</span>
              {isToggle === 1 ? (
                <span className='text-sm'>
                  {diarydate[0]}년 {diarydate[1]}월 {diarydate[2]}일
                </span>
              ) : (
                <span className='text-sm'>
                  {diarydate[1]}-{diarydate[2]}
                </span>
              )}
            </p>
            <p>{isToggle === 2 ? <span>{nickname}</span> : <></>}</p>
          </div>
          <p className='truncate block w-[156px] font-bold'>{content}</p>
        </div>
      </div>
    </Link>
  );
}

export default DiaryItem;
