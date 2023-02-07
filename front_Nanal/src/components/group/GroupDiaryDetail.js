import { useLocation } from 'react-router-dom';
import CommentList from './CommentList';

// 댓글 불러오기
function GroupDiaryDetail() {
  const location = useLocation();
  console.log(location);

  return (
    <div>
      <span>작성자 : {location.state.nickname}</span>
      <div>{location.state.creationDate}</div>
      <div>{location.state.content}</div>
      <div>
        <CommentList
          diaryIdx={location.state.diaryIdx}
          groupIdx={location.state.groupIdx}
        />
      </div>
    </div>
  );
}

export default GroupDiaryDetail;
