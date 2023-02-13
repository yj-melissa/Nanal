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
      <div className='flex items-center p-2 m-0.5 w-[328px]'>
        {isToggle !== 0 ? (
          <img
            src={picture}
            alt='DALL:E2'
            className='w-20 h-20 p-1 rounded-2xl'
          />
        ) : (
          <img
            src={picture}
            alt='DALL:E2'
            className='w-20 h-20 p-1 rounded-2xl'
          />
        )}
        <div className='w-full px-1 m-1 text-sm text-right truncate'>
          <div>
            <p>
              <img
                src={emo}
                alt='emotion'
                className='inline-block w-6 h-6 mb-1 mr-2'
              />
              {isToggle === 1 ? (
                <span className='text-sm'>
                  {diarydate[0]}년 {diarydate[1]}월 {diarydate[2]}일
                </span>
              ) : (
                <span className='text-sm'>
                  {diarydate[1]}월 {diarydate[2]}일
                </span>
              )}
            </p>
          </div>
          <p>{isToggle === 2 ? <span>{nickname}</span> : <></>}</p>
          <p className='block font-bold truncate'>{content}</p>
        </div>
      </div>
    </Link>
  );
}

export default DiaryItem;
