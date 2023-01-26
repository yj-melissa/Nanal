import { useContext, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { DiaryStateContext } from "../App";

function DiaryUpdate() {
  const navigate = useNavigate();
  const { id } = useParams();

  const diaryList = useContext(DiaryStateContext);
  console.log(id);
  console.log(diaryList);

  useEffect(() => {
    if (diaryList.length >= 1) {
      const targetDiary = diaryList.find(
        (it) => parseInt(it.id) === parseInt(id)
      );
      console.log(targetDiary);

      // 없는 번호로 조회할 경우 홈으로 강제 이동
      if (targetDiary) {
      } else {
        navigate("/", { replace: true });
      }
    }
  }, [id, diaryList]);
  return (
    <div>
      <h2>수정페이지</h2>
    </div>
  );
}

export default DiaryUpdate;
