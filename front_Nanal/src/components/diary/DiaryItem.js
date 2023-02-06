import { Link } from "react-router-dom";

function DiaryItem({ nickname, diaryIdx, diaryDate, content }) {
  // 월, 일만 추출
  const strMonth = diaryDate.slice(5, 7);
  const strDay = diaryDate.slice(8, 10);

  return (
    <Link
      to={`/Diary/${diaryIdx}`}
      state={{
        diaryIdx: diaryIdx,
        diaryDate: diaryDate,
      }}
    >
      <div className="my-2 p-2">
        {/* <div>Diary No. {diaryIdx}</div> */}
        <span className="box-content h-256 w-256">그림</span>
        <div className="flex justify-between">
          <span>{nickname}</span>
          <span>감정</span>
          <span className="text-sm">
            {strMonth}월 {strDay}일
          </span>
        </div>
        <p className="truncate ...">{content}</p>
      </div>
    </Link>
  );
}

export default DiaryItem;
