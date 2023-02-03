import { Link } from "react-router-dom";

function DiaryItem({ userIdx, diaryIdx, creationDate, content }) {
  const strDate = new Date(creationDate).toLocaleString();

  return (
    <Link
      to={`/Diary/${diaryIdx}`}
      state={{
        diaryIdx: diaryIdx,
      }}
    >
      <div className="my-2 p-2">
        {/* <div>Diary No. {diaryIdx}</div> */}
        <span className="box-content h-256 w-256">그림</span>
        <div>
          <span className="text-sm">{strDate}</span>
          <p className="truncate ...">{content}</p>
        </div>
      </div>
    </Link>
  );
}

export default DiaryItem;
